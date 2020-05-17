package ro.chris.schlechta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BestMatches {

    @JsonProperty("bestMatches")
    private List<BestMatchesItem> symbols;

    public List<BestMatchesItem> getSymbols() {
        return symbols;
    }

    public BestMatches setSymbols(List<BestMatchesItem> symbols) {
        this.symbols = symbols;
        return this;
    }
}