package ro.chris.schlechta.utils;

import ro.chris.schlechta.model.AuthorityType;

public interface Constants {

    String ALPHA_VANTAGE_GLOBAL_QUOTE_PATH = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE";

    String ALPHA_VANTAGE_SYMBOL_SEARCH_PATH = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH";

    int ASCII_ALPHABET_START_INDEX = 65;

    int ASCII_ALPHABET_END_INDEX = 90;

    String ROLE_USER = AuthorityType.ROLE_USER.getValue();

}
