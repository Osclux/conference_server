package com.example;

import com.opentok.OpenTok;
import com.opentok.exception.OpenTokException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

/**
 * TODO: Describe purpose
 */
public class SessionService {
    private static String apiSecret;
    private static String apiKey;
    private static List<ActiveSession> activeSessionList = new ArrayList<>();

    public static List<ActiveSession> allActiveSessions() {
        return activeSessionList.stream().map(
                activeSession -> activeSession.withToken(createTokenForSession(activeSession.getSessionId()))
        ).collect(Collectors.toList());
    }

    private static String createTokenForSession(String sessionId) {
        OpenTok opentok = new OpenTok(Integer.parseInt(apiKey), apiSecret);
        try {
            return opentok.generateToken(sessionId);
        } catch (OpenTokException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void createNewSession(String name) throws OpenTokException {
        OpenTok opentok = new OpenTok(Integer.parseInt(apiKey), apiSecret);
        String sessionId = opentok.createSession().getSessionId();
        activeSessionList.add(new ActiveSession(name, sessionId, "", apiKey));
    }

    public static void setApiSecret(String apiSecret) {
        SessionService.apiSecret = apiSecret;
    }

    public static String getApiSecret() {
        return apiSecret;
    }

    public static void setApiKey(String apiKey) {
        SessionService.apiKey = apiKey;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getSessionIdForName(String name) {
        Optional<ActiveSession> activeSession = activeSessionList.stream().filter(session -> session.getName().equals(name)).findFirst();
        if (activeSession.isPresent()) {
            return activeSession.get().getSessionId();
        }
        return "";
    }


}
