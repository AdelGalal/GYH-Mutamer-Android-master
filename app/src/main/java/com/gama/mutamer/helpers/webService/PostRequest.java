package com.gama.mutamer.helpers.webService;

import org.json.JSONObject;


public class PostRequest {
    private JSONObject Data;
    private String Url;

    public PostRequest() {

    }

    public PostRequest(JSONObject data, String url) {
        this.setData(data);
        this.setUrl(url);
    }

    public JSONObject getData() {
        return Data;
    }

    public void setData(JSONObject data) {
        Data = data;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
