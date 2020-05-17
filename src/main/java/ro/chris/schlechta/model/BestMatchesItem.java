package ro.chris.schlechta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BestMatchesItem {

    @JsonProperty(value = "1. symbol")
    private String symbol;

    @JsonProperty(value = "2. name")
    private String name;

    @JsonProperty(value = "3. type")
    private String type;

    @JsonProperty(value = "4. region")
    private String region;

    @JsonProperty(value = "5. marketOpen")
    private String marketOpen;

    @JsonProperty(value = "6. marketClose")
    private String marketClose;

    @JsonProperty(value = "7. timezone")
    private String timezone;

    @JsonProperty(value = "8. currency")
    private String currency;

    @JsonProperty(value = "9. matchScore")
    private String matchScore;

    public String getSymbol() {
        return symbol;
    }

    public BestMatchesItem setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getName() {
        return name;
    }

    public BestMatchesItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public BestMatchesItem setType(String type) {
        this.type = type;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public BestMatchesItem setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getMarketOpen() {
        return marketOpen;
    }

    public BestMatchesItem setMarketOpen(String marketOpen) {
        this.marketOpen = marketOpen;
        return this;
    }

    public String getMarketClose() {
        return marketClose;
    }

    public BestMatchesItem setMarketClose(String marketClose) {
        this.marketClose = marketClose;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public BestMatchesItem setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public BestMatchesItem setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getMatchScore() {
        return matchScore;
    }

    public BestMatchesItem setMatchScore(String matchScore) {
        this.matchScore = matchScore;
        return this;
    }
}