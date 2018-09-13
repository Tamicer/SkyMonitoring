package com.tamic.statinterface.stats.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.tamic.statinterface.stats.core.TcUploadCoreReceiver;


/**
 * PollUtil
 * Created by Tamic.
 */
public class PollUtil {

    static TcUploadCoreReceiver receiver;


    /**startPollingService
     * @param context
     * @param seconds
     * @param cls
     * @param action
     */
    public static void startPollingService(Context context, int seconds, Class<?> cls, String action) {


        //获取AlarmManager系统服务
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);

        //包装需要执行Service的Intent
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
       /* PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);*/

        long triggerAtTime = SystemClock.elapsedRealtime();


        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime,
                seconds * 1000, pendingIntent);
    }

    /**
     * stopPollingService
     * @param context
     * @param cls
     * @param action
     */
    public static void stopPollingService(Context context, Class<?> cls, String action) {
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        manager.cancel(pendingIntent);

    }
}
