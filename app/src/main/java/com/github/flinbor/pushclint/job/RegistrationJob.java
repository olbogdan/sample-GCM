package com.github.flinbor.pushclint.job;
/**
 * Created by Bogdanov Oleksandr on 17,October,2015
 */

import com.github.flinbor.pushclint.constant.Priority;
import com.github.flinbor.pushclint.eventbus.RegistrationEvent;
import com.github.flinbor.pushclint.util.PushHelper;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.concurrent.atomic.AtomicInteger;
import de.greenrobot.event.EventBus;
import tk.flinbor.pushnotifications.R;

/**
 * get token
 * and send it to web server for register on events
 */
public class RegistrationJob extends Job {
    private static final AtomicInteger jobCounter = new AtomicInteger(0);
    private static final String TAG = RegistrationJob.class.getName();
    private final int id;

    /**
     * job in group will be culled one by one
     */
    public RegistrationJob() {
        super(new Params(Priority.LOW).requireNetwork().setGroupId(TAG));
        id = jobCounter.incrementAndGet();
    }

    @Override
    public void onAdded() {}

    @Override
    public void onRun() throws Throwable {
        if (id != jobCounter.get()) {
            //looks like other fetch jobs has been added after me. no reason to keep many post
            //cancel me, let the for new one register
            return;
        }

        InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
        String token = instanceID.getToken(getApplicationContext().getString(R.string.project_number),GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

        //store after success send to server
        PushHelper.storeRegistrationId(getApplicationContext(), token);
        EventBus.getDefault().post(RegistrationEvent.getSuccess());


    }


    @Override
    protected void onCancel() {
        EventBus.getDefault().post(RegistrationEvent.getErrorInstance(null));
    }



}