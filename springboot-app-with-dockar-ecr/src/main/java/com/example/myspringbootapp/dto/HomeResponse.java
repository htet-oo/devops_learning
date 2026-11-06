
package com.example.myspringbootapp.dto;

public class HomeResponse {

    private String message;
    private String app;
    private String hostname;

    public HomeResponse(String message, String app, String hostname) {
        this.message = message;
        this.app = app;
        this.hostname = hostname;
    }

    public String getMessage() {
        return message;
    }

    public String getApp() {
        return app;
    }

    public String getHostname() {
        return hostname;
    }
}
