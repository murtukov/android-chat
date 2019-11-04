package com.example.chatapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Request {
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    private String url;
    private String method = METHOD_GET;
    private Map<String, String> params = new HashMap<>();

    public Request(String method, String url) {
        this.method = method;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public String getEncodedParams() {
        StringBuilder sb = new StringBuilder();

        for (String key : params.keySet()) {
            String value = null;

            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (sb.length() > 0) {
                sb.append("&");
            }

            sb.append(key).append("=").append(value);
        }

        return sb.toString();
    }
}
