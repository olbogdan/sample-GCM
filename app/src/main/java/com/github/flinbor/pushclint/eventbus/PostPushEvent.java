package com.github.flinbor.pushclint.eventbus;

/**
 * Created by Bogdanov Oleksandr on 19,October,2015
 */
public class PostPushEvent {
    private String message;

    public PostPushEvent(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }

}
