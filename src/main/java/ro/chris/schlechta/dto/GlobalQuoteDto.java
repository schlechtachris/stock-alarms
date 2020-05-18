package ro.chris.schlechta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The wrapping object of the {@link GlobalQuoteItemDto} object
 */
public class GlobalQuoteDto {

    @JsonProperty("Global Quote")
    private GlobalQuoteItemDto globalQuoteItemDto;

    public GlobalQuoteItemDto getGlobalQuoteItemDto() {
        return globalQuoteItemDto;
    }

    public GlobalQuoteDto setGlobalQuoteItemDto(GlobalQuoteItemDto globalQuoteItemDto) {
        this.globalQuoteItemDto = globalQuoteItemDto;
        return this;
    }
}
