package ro.chris.schlechta.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.chris.schlechta.model.CustomUserDetails;
import ro.chris.schlechta.model.UserStock;
import ro.chris.schlechta.response.StandardResponse;
import ro.chris.schlechta.service.UserStockService;

import java.net.URI;

@RestController
public class UserStockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStockController.class);

    private final UserStockService service;

    @Autowired
    public UserStockController(UserStockService service) {
        this.service = service;
    }

    @GetMapping("/userstocks")
    @Secured("ROLE_USER")
    public ResponseEntity<StandardResponse> getStocksForUser(@AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("Handling GET request to /userstocks for user: {}.", user.getUsername());

        return ResponseEntity
                .ok(new StandardResponse("All stock subscribed to by the loggedin user.",
                        service.getAllStocksSubscribedTo()));
    }

    @PostMapping("/userstocks/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<StandardResponse> subscribeToStock(@PathVariable Long id) {
        LOGGER.info("Handling POST request to /userstocks...");

        UserStock userStock = service.save(id);

        if (userStock == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new StandardResponse("There is no stock with id: " + id, null));
        }

        return ResponseEntity
                .created(URI.create("http://localhost/userstocks/" + userStock.getId()))
                .body(null);
    }

}
