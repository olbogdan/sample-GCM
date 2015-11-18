package com.github.flinbor.pushclint.ui.activity;
/**
 * Created by Bogdanov Oleksandr on 17,October,2015
 */
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.flinbor.pushclint.eventbus.PostPushEvent;
import com.github.flinbor.pushclint.job.PostPushJob;
import com.path.android.jobqueue.JobManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import com.github.flinbor.pushclint.eventbus.HubEvent;
import tk.flinbor.pushnotifications.R;
import com.github.flinbor.pushclint.core.PushCore;
import com.github.flinbor.pushclint.eventbus.RegistrationEvent;
import com.github.flinbor.pushclint.eventbus.RegistrationEvent.State;
import com.github.flinbor.pushclint.job.RegistrationJob;

public class MainActivity extends BaseActivity {
    @Bind(R.id.progress) View progress;
    @Bind(R.id.imageview_push_status) ImageView imageViewPushStatus;
    @Bind(R.id.textview_log) TextView textViewLog;
    @Bind(R.id.edittext_push) EditText editTextPush;
    @Bind(R.id.scroll) ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initActionBar(getWindow());

        if (checkPlayServices()) {
            // Start Job to register this application with GCM.
            JobManager jobManager = PushCore.getInstance().getJobManager();
            jobManager.addJobInBackground(new RegistrationJob());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @SuppressWarnings({"UnusedDeclaration", "deprecation"})
    public void onEventMainThread(RegistrationEvent result) {
        String logText = null;
        if (result.getState() == State.SUCCESS) {
            imageViewPushStatus.setImageResource(R.drawable.ok);
            logText = getString(R.string.success_registration);
        } else if (result.getState() == State.ERROR) {
            imageViewPushStatus.setImageResource(R.drawable.cancel);
            logText = getString(R.string.failure_registration);
        }
        Spannable span = new SpannableString(logText);
        span.setSpan(new StyleSpan(Typeface.ITALIC), 0, logText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewLog.append(span);

        scroll.fullScroll(View.FOCUS_DOWN);
        progress.setVisibility(View.GONE);
    }

    @SuppressWarnings({"UnusedDeclaration", "deprecation"})
    public void onEventMainThread(HubEvent result) {
        textViewLog.append("\n");
        textViewLog.append("\n");
        textViewLog.append(getString(R.string.message_received));
        Spannable span = new SpannableString(result.getMessage());
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary_blue)), 0, result.getMessage().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewLog.append(span);
        scroll.fullScroll(View.FOCUS_DOWN);
    }

    @SuppressWarnings({"UnusedDeclaration", "deprecation"})
    public void onEventMainThread(PostPushEvent result) {
        textViewLog.append("\n");
        Spannable span = new SpannableString(result.getMessage());
        span.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.darker_gray)), 0, result.getMessage().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewLog.append(span);
        scroll.fullScroll(View.FOCUS_DOWN);
        progress.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Throwable t) {/*this may crash if registration did not go through. just be safe*/}
    }

    @OnClick(R.id.button_send_push)
    public void onClick() {
        JobManager jobManager = PushCore.getInstance().getJobManager();
        String message = editTextPush.getText().toString();
        if (TextUtils.isEmpty(message)) {
            message = getString(R.string.default_message);
        }

        jobManager.addJobInBackground(new PostPushJob(message));
        progress.setVisibility(View.VISIBLE);
    }

}
