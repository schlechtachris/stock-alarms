package ro.chris.schlechta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The wrapping object of the {@link GlobalQuoteItem} object
 */
public class GlobalQuote {

    @JsonProperty("Global Quote")
    private GlobalQuoteItem globalQuoteItem;

    public GlobalQuoteItem getGlobalQuoteItem() {
        return globalQuoteItem;
    }

    public GlobalQuote setGlobalQuoteItem(GlobalQuoteItem globalQuoteItem) {
        this.globalQuoteItem = globalQuoteItem;
        return this;
    }
}
