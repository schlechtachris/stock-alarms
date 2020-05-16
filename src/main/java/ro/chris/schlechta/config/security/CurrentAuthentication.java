package ro.chris.schlechta.config.security;

import org.springframework.security.core.Authentication;

public interface CurrentAuthentication {

    Authentication getAuthentication();

}
