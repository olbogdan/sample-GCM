package com.github.flinbor.pushclint.eventbus;
/**
 * Created by Bogdanov Oleksandr on 18,October,2015
 */

import android.support.annotation.NonNull;

/**
 * Event for notify UI
 */
public class RegistrationEvent {
    public enum State {SUCCESS, ERROR}

    private State state;
    private String error;
    private int targetProgress;
    private int currentProgress;
    private String targetFile;

    private RegistrationEvent() {
    }

    /**
     * @param error
     * @return error instance with optional message
     */
    public static RegistrationEvent getErrorInstance(@NonNull String error) {
        RegistrationEvent event = new RegistrationEvent();
        event.state = State.ERROR;
        event.error = error;
        return event;
    }

    /**
     * @return instance with selected state
     */
    public static RegistrationEvent getSuccess() {
        RegistrationEvent event = new RegistrationEvent();
        event.state = State.SUCCESS;
        return event;
    }

    public State getState() {
        return state;
    }
}
