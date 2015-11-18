package com.github.flinbor.pushclint.core;
/**
 * Created by Bogdanov Oleksandr on 16,October,2015
 */

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.github.flinbor.pushclint.util.Devices;
import com.github.flinbor.pushclint.util.Logger;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import tk.flinbor.pushnotifications.R;

@ReportsCrashes(
        mailTo = "iceposhta@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.toast_crash_text)

public class PushApplication extends Application {

    private JobManager jobManager;
    private static PushApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        initAcra();
        configureJobManager();
        instance = this;
    }

    public static PushApplication getInstance() {
        return instance;
    }

    /**
     * init BugTracker
     */
    private void initAcra() {
        ACRA.init(this);
        String versionName = "";
        String versionCode = "";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_META_DATA);
            versionName = pInfo.versionName;
            versionCode = String.valueOf(pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException ex) {
            if (Logger.ENABLED) {
                ex.printStackTrace();
            }
        }
        String device = String.format(Locale.getDefault(),
                "Android version:%s || Device model:%s || Android locale:%s || Application version:%s || Application version code:%s || Timestamp:%s",
                Build.VERSION.SDK_INT, Devices.getDeviceName(), Locale.getDefault().toString(),
                versionName, versionCode, SimpleDateFormat.getDateTimeInstance().format(new Date()));
        ACRA.getErrorReporter().putCustomData("device info:", device);
    }

    public JobManager getJobManager() {
        return jobManager;
    }

    private void configureJobManager() {
        Configuration configuration = new Configuration.Builder(this)
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
        jobManager = new JobManager(this, configuration);
    }
}
