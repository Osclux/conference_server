package com.example;

import java.util.List;

/**
 * TODO: Describe purpose
 */
public class AllActiveSessionsResponse {

    private final List<ActiveSession> list;

    public AllActiveSessionsResponse(List<ActiveSession> list) {
        this.list = list;
    }
}
