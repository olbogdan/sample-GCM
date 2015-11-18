package com.github.flinbor.pushclint.model;

/**
 * Created by Bogdanov Oleksandr on 19,October,2015
 */
public class RequestPushBody {
    private String to;
    private RequestData data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public RequestData getData() {
        return data;
    }

    public void setData(RequestData data) {
        this.data = data;
    }
}
