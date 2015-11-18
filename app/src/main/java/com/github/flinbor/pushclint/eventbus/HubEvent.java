package com.github.flinbor.pushclint.eventbus;

/**
 * Created by Bogdanov Oleksandr on 19,October,2015
 */
public class HubEvent {
    private String message;

    public HubEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
