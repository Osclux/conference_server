package com.example;

import static spark.Spark.*;

import com.google.gson.Gson;
import freemarker.template.Configuration;
import spark.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import com.opentok.OpenTok;
import com.opentok.exception.OpenTokException;
import spark.template.freemarker.FreeMarkerEngine;

public class HelloWorldServer {

    private static final String apiKey = System.getProperty("API_KEY");
    private static final String apiSecret = System.getProperty("API_SECRET");
    private static OpenTok opentok;
    private static String sessionId;

    public static void main(String[] args) throws OpenTokException, IOException {

        if (apiKey == null || apiKey.isEmpty() || apiSecret == null || apiSecret.isEmpty()) {
            System.out.println("You must define API_KEY and API_SECRET system properties in the build.gradle file.");
            System.exit(-1);
        }

        SessionService.setApiKey(apiKey);
        SessionService.setApiSecret(apiSecret);
        SessionService.createNewSession("Oscar");
//        opentok = new OpenTok(Integer.parseInt(apiKey), apiSecret);
//
//        sessionId = opentok.createSession().getSessionId();
        sessionId = SessionService.getSessionIdForName("Oscar");

        externalStaticFileLocation("./public");
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldServer.class, "freemarker");
        get("/", (request, response) -> {

//                String token = null;
//                try {
//                    token = opentok.generateToken(sessionId);
//                } catch (OpenTokException e) {
//                    e.printStackTrace();
//                }

            String token = SessionService.allActiveSessions().get(0).getToken();

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("apiKey", apiKey);
            attributes.put("sessionId", sessionId);
            attributes.put("token", token);

            return new ModelAndView(attributes, "index.ftl");

        }, new FreeMarkerEngine(configuration));

        Gson gson = new Gson();
        get("/activesessions", (request, response) -> {

            return new AllActiveSessionsResponse(SessionService.allActiveSessions());
        }, gson::toJson);

    }
}
