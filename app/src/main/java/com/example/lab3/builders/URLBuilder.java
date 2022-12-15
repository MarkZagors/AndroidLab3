package com.example.lab3.builders;

public class URLBuilder {
    private final String REQUEST_IP = "10.21.18.157";
    private final String REQUEST_PORT = "8080";
    private final String REQUEST_ADDRESS = "http://" + REQUEST_IP + ":" + REQUEST_PORT + "/";

    private String requestString;

    public URLBuilder(String url, boolean hasParameters) {
        if (hasParameters) requestString = REQUEST_ADDRESS + url + "?";
        else requestString = REQUEST_ADDRESS + url;
    }

    public URLBuilder addParam(String key, String value) {
        requestString += key + "=" + value + "&";
        return this;
    }

    public String getURL() {
        return requestString;
    }
}
