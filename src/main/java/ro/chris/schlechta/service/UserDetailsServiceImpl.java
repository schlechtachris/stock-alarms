package ro.chris.schlechta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.model.CustomUserDetails;
import ro.chris.schlechta.model.persisted.User;
import ro.chris.schlechta.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // in our case the username is also the email, so we search for the email
        Optional<User> user = repository.findByEmail(username);

        if (user.isPresent()) {
            return new CustomUserDetails(user.get());
        }

        throw new UsernameNotFoundException(username);
    }
}
