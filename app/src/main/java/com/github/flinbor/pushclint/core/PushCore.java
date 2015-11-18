package com.github.flinbor.pushclint.core;
/**
 * Created by Bogdanov Oleksandr on 16,October,2015
 */

import android.content.Context;
import android.util.Log;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

import com.github.flinbor.pushclint.util.Logger;


/**
 * Class to use for storing reference on application object - PushApplication
 * It will be created to fix issue with nullpointer exception during run Tests
 */
public class PushCore {
    private static PushCore instance;
    private JobManager jobManager;
    private Context context;

    /**
     * synchronized for work in different processes
     * @return
     */
    public static PushCore getInstance() {
        if (instance == null) {
            synchronized (PushCore.class) {
                if (instance == null) {
                    instance = new PushCore();
                }
            }
        }
        return instance;
    }

    public void init(final Context context) {
        this.context = context;
        configureJobManager();
    }

    public Context getContext() {
        return context;
    }

    public JobManager getJobManager() {
        return jobManager;
    }

    private void configureJobManager() {
        Configuration configuration = new Configuration.Builder(context)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "MMMCareJOBS";

                    @Override
                    public boolean isDebugEnabled() {
                        return Logger.ENABLED;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(2)//up to 2 consumers at a time
                .loadFactor(2)// jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .build();
        jobManager = new JobManager(context, configuration);
    }
}
