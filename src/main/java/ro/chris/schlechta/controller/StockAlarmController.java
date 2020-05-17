package ro.chris.schlechta.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ro.chris.schlechta.model.AlarmDto;
import ro.chris.schlechta.model.AuthorityType;
import ro.chris.schlechta.model.CustomUserDetails;
import ro.chris.schlechta.model.persisted.StockAlarm;
import ro.chris.schlechta.response.StandardResponse;
import ro.chris.schlechta.service.StockAlarmService;

import java.net.URI;
import java.util.Optional;

import static ro.chris.schlechta.model.AuthorityType.ROLE_USER;

@RestController
public class StockAlarmController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockAlarmController.class);

    private final StockAlarmService service;

    @Autowired
    public StockAlarmController(StockAlarmService service) {
        this.service = service;
    }

    @GetMapping("/alarms")
    public ResponseEntity<StandardResponse> getAllAlarms() {
        LOGGER.info("Handling GET request to /alarms.");

        return ResponseEntity
                .ok(new StandardResponse("Alarms set.", service.getAllAlarmsPerUser()));
    }

    @GetMapping("/alarm/{id}")
    public ResponseEntity<StandardResponse> getAllAlarms(@PathVariable("id") String id) {
        LOGGER.info("Handling GET request to /alarms/{}.", id);

        Optional<StockAlarm> alarmById = service.getAlarmById(Long.valueOf(id));

        if (alarmById.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new StandardResponse("There is no alarm with this id.", null));
        }

        return ResponseEntity
                .ok(new StandardResponse("Successfully retrieved alarm.", alarmById.get()));
    }

    @PostMapping("/alarm")
    @Secured("ROLE_USER")
    public ResponseEntity<StandardResponse> createNewAlarm(@RequestBody StockAlarm stockAlarm,
//    public ResponseEntity<StandardResponse> createNewAlarm(@RequestBody AlarmDto stockAlarm,
                                                           @AuthenticationPrincipal CustomUserDetails user) {

        LOGGER.info("Create new alarm for stock: {} by user: {}.", stockAlarm.getStockSymbol(), user.getUsername());
        StockAlarm createdAlarm = service.createNewAlarm(stockAlarm);

        if (createdAlarm == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new StandardResponse("An alarm for this stock is already defined.", null));
        }

        return ResponseEntity
                .created(URI.create("http://localhost:8081/alarm/" + createdAlarm.getId()))
                .body(new StandardResponse("The alarm was created successfully", createdAlarm));
    }

    @PutMapping("/alarm")
    public ResponseEntity<StandardResponse> updateAlarms(@RequestBody StockAlarm stockAlarm) {
        LOGGER.info("Handling PUT request to /alarm.");

        StockAlarm updatedAlarm = service.updateAlarm(stockAlarm);

        if (updatedAlarm == null) {
            return ResponseEntity.badRequest()
                    .body(new StandardResponse("There is no alarm for this stock!", null));
        }

        return ResponseEntity
                .ok(new StandardResponse("Updated successfully!", updatedAlarm));
    }

    @DeleteMapping("/alarms")
    public ResponseEntity<StandardResponse> deleteAlarms(@AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("Deleting all alarms for user: {}", user.getUsername());

        service.deleteAllAlarms();
        return ResponseEntity
                .ok(new StandardResponse("Alarms deleted successfully!", null));
    }

}
