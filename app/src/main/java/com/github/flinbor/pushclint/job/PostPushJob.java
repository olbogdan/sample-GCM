package com.github.flinbor.pushclint.job;

import com.github.flinbor.pushclint.api.PushCareApiClient;
import com.github.flinbor.pushclint.constant.Priority;
import com.github.flinbor.pushclint.eventbus.PostPushEvent;
import com.github.flinbor.pushclint.model.RequestData;
import com.github.flinbor.pushclint.model.RequestPushBody;
import com.github.flinbor.pushclint.model.ResponcePostPush;
import com.github.flinbor.pushclint.util.PushHelper;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import de.greenrobot.event.EventBus;
import tk.flinbor.pushnotifications.R;


public class PostPushJob extends Job {
	public static final String TAG = PostPushJob.class.getName();
	private String message;


	public PostPushJob(String message) {
        super(new Params(Priority.LOW).requireNetwork().setGroupId(TAG));
		this.message = message;
	}

	@Override
	public void onAdded() {
	}

    @Override
    public void onRun() throws Throwable {
		PushCareApiClient.PushCareApi api = PushCareApiClient.getRestApi(getApplicationContext());

		String token = PushHelper.getRegistrationId(getApplicationContext());

		String api_key = getApplicationContext().getString(R.string.api_key);
		api_key = "key=" + api_key;

		RequestPushBody requestPushBody = new RequestPushBody();
		requestPushBody.setTo(token);

		RequestData requestData = new RequestData();
		requestData.setMessage(message);

		requestPushBody.setData(requestData);

		ResponcePostPush responcePostPush = api.postNotificationHistory(api_key, requestPushBody);

		int failure = responcePostPush.getFailure();
		int success = responcePostPush.getSuccess();
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("\n");
		sb.append("successfuly send to push server - ");
		sb.append("\n");
		sb.append("count failure messages: "+failure);
		sb.append("\n");
		sb.append("count success messages"+success);
		EventBus.getDefault().post(new PostPushEvent(sb.toString()));
	}

	@Override
	protected void onCancel() {
		EventBus.getDefault().post(new PostPushEvent("error send messages"));
	}
}
