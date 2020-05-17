package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.config.security.CurrentAuthentication;
import ro.chris.schlechta.model.AuthorityType;
import ro.chris.schlechta.model.persisted.User;
import ro.chris.schlechta.repository.UserRepository;
import ro.chris.schlechta.request.RegisterRequestModel;

import java.util.Optional;

/**
 * It contains the logic needed to register a new {@link User}
 */
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final CurrentAuthentication authentication;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, CurrentAuthentication authentication, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.authentication = authentication;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * it registers a new user
     *
     * @param registerForm form submitted by the new user
     * @return true if the user data was persisted successfully, otherwise false
     */
    public boolean saveNewUser(RegisterRequestModel registerForm) {
        LOGGER.info("Verifying new user's passwords");

        if (!registerForm.getPassword().equals(registerForm.getPasswordCheck())) {
            LOGGER.error("The password and check password do not match!");
            return false;
        }

        if (repository.findByEmail(registerForm.getEmail()).isPresent()) {
            LOGGER.error("There already exists a user with email: {}!", registerForm.getEmail());
            return false;
        }

        LOGGER.info("Persisting new user with email: {}.", registerForm.getEmail());
        User user = new User()
                .setFirstName(registerForm.getFirstName())
                .setLastName(registerForm.getLastName())
                .setEmail(registerForm.getEmail())
                .setPassword(passwordEncoder.encode(registerForm.getPassword()))
                .setRole(AuthorityType.ROLE_USER);

        repository.save(user);
        LOGGER.info("Successfully persisted new user with email: {}.", registerForm.getEmail());

        return true;
    }

    public User getAuthenticatedUser() {
        String authenticatedUserEmail = authentication.getAuthentication().getName();
        Optional<User> authenticatedUser = repository.findByEmail(authenticatedUserEmail);

        return authenticatedUser.orElse(null);
    }

}