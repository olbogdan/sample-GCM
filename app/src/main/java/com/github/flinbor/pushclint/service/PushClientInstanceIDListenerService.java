package com.github.flinbor.pushclint.service;

/**
 * Created by Bogdanov Oleksandr on 18,October,2015
 */
import com.github.flinbor.pushclint.core.PushApplication;
import com.google.android.gms.iid.InstanceIDListenerService;
import com.path.android.jobqueue.JobManager;

import com.github.flinbor.pushclint.job.RegistrationJob;
import com.github.flinbor.pushclint.util.PushHelper;

/**
 * This class handles refreshing of push token
 */
public class PushClientInstanceIDListenerService extends InstanceIDListenerService {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    @Override
    public void onTokenRefresh() {
        PushHelper.clearRegistrationId(this);
        JobManager jobManager = PushApplication.getInstance().getJobManager();
        jobManager.addJobInBackground(new RegistrationJob());
    }

}
