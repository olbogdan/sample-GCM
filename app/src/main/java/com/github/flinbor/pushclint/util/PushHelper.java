package com.github.flinbor.pushclint.util;

/**
 * Created by Bogdanov Oleksandr on 18,October,2015
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * This class contains static methods to manage push token id : get and save it, get application version code.
 */
public class PushHelper {
    private final static String TAG = "PushHelper";
    private static final String PROPERTY_PUSH_TOKEN = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    private PushHelper() {
    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId   registration ID
     */
    public static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Logger.logD(TAG, "Saving regId on app version " + appVersion + ",regId:" + regId);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_PUSH_TOKEN, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.apply();
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_PUSH_TOKEN, "");
        if (registrationId.isEmpty()) {
            Logger.logD(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Logger.logD(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    public static void clearRegistrationId(final Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(PROPERTY_PUSH_TOKEN);
        editor.apply();
    }

    /**
     * @param context
     * @return version code of application
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private static SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return context.getSharedPreferences(PushHelper.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }


}
