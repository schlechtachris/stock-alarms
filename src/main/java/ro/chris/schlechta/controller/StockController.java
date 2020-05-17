package ro.chris.schlechta.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.chris.schlechta.response.StandardResponse;
import ro.chris.schlechta.service.StockService;

@RestController
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stocks")
    public ResponseEntity<StandardResponse> getStocks() {
        LOGGER.info("Handle GET request to /stocks...");

        return ResponseEntity
                .ok(new StandardResponse("All stocks available.", stockService.getStocks()));
    }

}
