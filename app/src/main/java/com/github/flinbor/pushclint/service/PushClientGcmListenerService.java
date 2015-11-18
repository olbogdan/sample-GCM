package com.github.flinbor.pushclint.service;
/**
 * Created by Bogdanov Oleksandr on 18,October,2015
 */
import android.os.Bundle;
import android.text.TextUtils;

import com.github.flinbor.pushclint.eventbus.HubEvent;
import com.github.flinbor.pushclint.util.Logger;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;

import de.greenrobot.event.EventBus;


public class PushClientGcmListenerService extends GcmListenerService {
    private final Gson gson = new Gson();

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Logger.logD(this.getClass().getName(), "From: " + from);
        Logger.logD(this.getClass().getName(), "Message: " + message);

        if (!TextUtils.isEmpty(message)) {
            try {
                EventBus.getDefault().post(new HubEvent(message));
            } catch (Throwable th) {
                if (Logger.ENABLED) {
                    th.printStackTrace();
                }
            }
        }
    }
}