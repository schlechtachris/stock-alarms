package ro.chris.schlechta.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.chris.schlechta.request.RegisterRequestModel;
import ro.chris.schlechta.response.StandardResponse;
import ro.chris.schlechta.service.RegisterService;

import javax.validation.Valid;

@RestController
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    private final RegisterService service;

    @Autowired
    public RegisterController(RegisterService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<StandardResponse> register(@RequestBody @Valid RegisterRequestModel registerForm) {
        LOGGER.info("Register new user...");

        if (service.registerUser(registerForm)) {
            return ResponseEntity
                    .ok(new StandardResponse("Successfully registered!", null));
        }

        LOGGER.info("Something went wrong! User already exists!");
        return ResponseEntity
                .badRequest()
                .body(new StandardResponse("Something went wrong! User already exists.", null));
    }

}