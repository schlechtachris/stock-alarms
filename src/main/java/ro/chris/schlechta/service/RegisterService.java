package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.model.User;
import ro.chris.schlechta.repository.UserRepository;
import ro.chris.schlechta.request.RegisterRequestModel;

/**
 * It contains the logic needed to register a new {@link User}
 */
@Service
public class RegisterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterService.class);

    private final UserRepository repository;

    @Autowired
    public RegisterService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * it registers a new user
     *
     * @param registerForm form submitted by the new user
     * @return true if the user data was persisted successfully, otherwise false
     */
    public boolean registerUser(RegisterRequestModel registerForm) {
        LOGGER.info("Verifying new user's passwords");

        if (registerForm.getPass().equals(registerForm.getPassCheck())) {
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
                .setPass(registerForm.getPass());

        repository.save(user);
        LOGGER.info("Successfully persisted new user with email: {}.", registerForm.getEmail());

        return true;
    }

}