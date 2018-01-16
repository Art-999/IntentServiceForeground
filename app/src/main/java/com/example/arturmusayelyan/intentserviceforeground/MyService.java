package com.example.arturmusayelyan.intentserviceforeground;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by artur.musayelyan on 16/01/2018.
 */

public class MyService extends IntentService {
    //    /**
//     * Creates an IntentService.  Invoked by your subclass's constructor.
//     *
//     * @param name Used to name the worker thread, important only for debugging.
//     */
    public MyService() {
        super("IntentService");
    }

    public MyService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service started...", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//// onHandleIntent-e avtomat vercnum e intentic datan
//        Toast.makeText(this, "Service started...", Toast.LENGTH_SHORT).show();
//Log.d("Art","onStartCommand worked");
//        return START_STICKY;
//    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        synchronized (this) {// inqe hencic skzmic hertov e anum
        int taskID = intent.getIntExtra(MainActivity.KEY_TASK_REQUEST_ID, 0);
        int time = intent.getIntExtra(MainActivity.KEY_SEND_TIME, 0);
        Log.d("Art", "onHandleIntent worked  "+taskID + " " + time);

        Intent localIntent = new Intent(MainActivity.BROADCAST_ACTION);
        localIntent.putExtra(MainActivity.KEY_TASK_REQUEST_ID, taskID);
        localIntent.putExtra(MainActivity.KEY_RESULT_STATUS, MainActivity.STATUS_START);
        //sendBroadcast(localIntent);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
//            MainActivity.textView1.post(new Runnable() {
//                @Override
//                public void run() {
//                    MainActivity.textView1.setText("Task 1 start...");
//                }
//            });

        SystemClock.sleep(time);

        localIntent.putExtra(MainActivity.KEY_RESULT_STATUS, MainActivity.STATUS_WORKING);
        for (int i = 1; i <= 10; i++) {
            localIntent.putExtra(MainActivity.KEY_RESULT_WORKINGPROGRESS, i);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
            SystemClock.sleep(1000);
        }

        localIntent.putExtra(MainActivity.KEY_RESULT_STATUS, MainActivity.STATUS_FINISH);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        SystemClock.sleep(2000);

        localIntent.putExtra(MainActivity.KEY_RESULT_STATUS, MainActivity.STATUS_FINISHED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        //stopSelf petq chi kanchel inqe avtomat avartum e
//         }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service finished...", Toast.LENGTH_SHORT).show();
    }
}
