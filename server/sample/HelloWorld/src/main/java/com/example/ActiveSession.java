package com.example;

/**
 * TODO: Describe purpose
 */
public class ActiveSession {

    private final String name;
    private final String sessionId;
    private final String token;
    private final String apiKey;

    public ActiveSession(String name, String sessionId, String token, String apiKey) {
        this.name = name;
        this.sessionId = sessionId;
        this.token = token;
        this.apiKey = apiKey;
    }

    public ActiveSession withToken(String token) {
        return new ActiveSession(this.name, this.sessionId, token, apiKey);
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
