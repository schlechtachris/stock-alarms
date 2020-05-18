package ro.chris.schlechta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BestMatchesDto {

    @JsonProperty("bestMatches")
    private List<BestMatchesItemDto> symbols;

    public List<BestMatchesItemDto> getSymbols() {
        return symbols;
    }

    public BestMatchesDto setSymbols(List<BestMatchesItemDto> symbols) {
        this.symbols = symbols;
        return this;
    }
}