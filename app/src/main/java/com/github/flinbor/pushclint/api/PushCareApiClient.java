package com.github.flinbor.pushclint.api;


import android.content.Context;

import com.github.flinbor.pushclint.core.PushCore;
import com.github.flinbor.pushclint.model.RequestPushBody;
import com.github.flinbor.pushclint.model.ResponcePostPush;
import com.github.flinbor.pushclint.util.Logger;
import com.squareup.okhttp.OkHttpClient;


import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import tk.flinbor.pushnotifications.R;

/**
 * Created by Bogdanov Oleksandr on 07,September,2015
 */

public class PushCareApiClient {
    private static PushCareApi restApi = null;
    private static OkHttpClient okHttpClient;
    private static final int CONNECTION_TIMEOUT = 20;// in seconds
    private static final int READ_TIMEOUT = 40;// in seconds

    /**
     * RestAdapter.Builder() class generates an implementation of the PushCareApi
     * interface
     */
    public static PushCareApi getRestApi(Context context) {

        if (restApi == null) {
            synchronized (PushCareApiClient.class) {

                if (restApi == null) {

                    RestAdapter.Builder restAdapterBuilder = new RestAdapter.Builder()
                            .setEndpoint(PushCore.getInstance().getContext().getString(R.string.server_url))
                            .setLogLevel(
                                    Logger.ENABLED ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                            .setClient(getRetrofitClient(context));

                    restApi = restAdapterBuilder.build().create(PushCareApi.class);
                }
            }
        }

        return restApi;
    }

    private static Client getRetrofitClient(Context context) {
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setFollowRedirects(true);
        okHttpClient.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);

        return new OkClient(okHttpClient);
    }

	public interface PushCareApi {


        @POST("/send")
        ResponcePostPush postNotificationHistory(@Header("Authorization") String apiKey, @Body RequestPushBody data);

    }
}
