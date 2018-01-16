package com.example.arturmusayelyan.intentserviceforeground;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;

/**
 * Created by artur.musayelyan on 16/01/2018.
 */

public class MyService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyService() {
        super("IntentService");
    }

    public MyService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
// onHandleIntent-e avtomat vercnum e intentic datan

        return START_REDELIVER_INTENT;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int taskID = intent.getIntExtra(MainActivity.KEY_TASK_REQUEST_ID, 0);
        int time = intent.getIntExtra(MainActivity.KEY_SEND_TIME, 0);

        Intent intentSendResult = new Intent(MainActivity.BROADCAST_ACTION);
        intentSendResult.putExtra(MainActivity.KEY_TASK_REQUEST_ID, taskID);
        intentSendResult.putExtra(MainActivity.KEY_RESULT_STATUS, MainActivity.STATUS_START);
        sendBroadcast(intentSendResult);
        SystemClock.sleep(time);

        intentSendResult.putExtra(MainActivity.KEY_RESULT_STATUS, MainActivity.STATUS_WORKING);
        for (int i = 1; i <= 10; i++) {
            intentSendResult.putExtra(MainActivity.KEY_RESULT_WORKINGPROGRESS, i);
            sendBroadcast(intentSendResult);
            SystemClock.sleep(1000);
        }

        intentSendResult.putExtra(MainActivity.KEY_RESULT_STATUS, MainActivity.STATUS_FINISH);
        sendBroadcast(intentSendResult);
        SystemClock.sleep(2000);

        intentSendResult.putExtra(MainActivity.KEY_RESULT_STATUS, MainActivity.STATUS_FINISHED);
        sendBroadcast(intentSendResult);
        //stopSelf petq chi kanchel inqe avtomat avartum e
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
