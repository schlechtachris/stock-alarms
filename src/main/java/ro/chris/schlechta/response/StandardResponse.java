package ro.chris.schlechta.response;

public class StandardResponse {

    private String infoMessage;

    private Object payload;

    /**
     * The Constructor
     *
     * @param infoMessage the info message
     * @param payload     the payload
     */
    public StandardResponse(String infoMessage, Object payload) {
        this.infoMessage = infoMessage;
        this.payload = payload;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public StandardResponse setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
        return this;
    }

    public Object getPayload() {
        return payload;
    }

    public StandardResponse setPayload(Object payload) {
        this.payload = payload;
        return this;
    }

}
