package ro.chris.schlechta.model;

public enum AuthorityType {

    ROLE_USER("ROLE_USER");

    private String value;

    AuthorityType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}