package com.xyleme.bravais.utils.api.cds;

import com.fasterxml.jackson.annotation.*;

/**
 * 'Authenticate' endpoint data.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "username",
        "password"
})
public class AuthenticateEndpointData {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets data for authentication.
     * @param username - Specifies username
     * @param password - Specifies password
     * @return {@code AuthenticateEndpointData}
     */
    public AuthenticateEndpointData setDataForAuthenticationEndPoint(String username, String password) {
        setUsername(username);
        setPassword(password);
        return this;
    }
}