package ApiRest2.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Facundo on 12/06/2017.
 */
public class LoginResponseWrapper {

    @JsonProperty
    String sessionId ;


    public LoginResponseWrapper() {

    }

    public LoginResponseWrapper(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
