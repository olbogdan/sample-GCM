package com.github.flinbor.pushclint.core;
/**
 * Created by Bogdanov Oleksandr on 16,October,2015
 */

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.github.flinbor.pushclint.util.Logger;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import tk.flinbor.pushnotifications.R;
import com.github.flinbor.pushclint.util.Devices;

@ReportsCrashes(
        mailTo = "iceposhta@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.toast_crash_text)

public class PushApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initAcra();
        PushCore.getInstance().init(this);
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
}
