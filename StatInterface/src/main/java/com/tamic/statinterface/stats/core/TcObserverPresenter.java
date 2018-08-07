package com.tamic.statinterface.stats.core;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.tamic.statinterface.stats.constants.StaticsConfig;
import com.tamic.statinterface.stats.db.helper.DataConstruct;
import com.tamic.statinterface.stats.presenter.TcDeblockObserver;
import com.tamic.statinterface.stats.presenter.TcNetworkObserver;
import com.tamic.statinterface.stats.presenter.TcScreenObserver;
import com.tamic.statinterface.stats.util.StatLog;

import java.util.List;

/**
 * ObserverPresenter
 * Created by Tamic on 2016-04-14.
 */
public class TcObserverPresenter implements TcNetworkObserver.INetworkListener, TcScreenObserver.IScreenListener,
        TcDeblockObserver.IKeyguardListener {

    /** NetworkObserver  */
    private TcNetworkObserver mNetworkObserver;
    /** ScreenObserver */
    private TcScreenObserver mScreenObserver;
    /** DeblockObserver */
    private TcDeblockObserver mKeyguardObserver;
    /** isForeground */
    private boolean isForeground;
    /**  isInit */
    private boolean isInit;
    /** PackageName */
    private String mPackageName;
    /** isTopTask */
    private boolean isTopTask;
    /** isScreenOff */
    private boolean isScreenOff;
    /** isScreenLocked */
    private boolean isScreenLocked;
    /** APP_STATUS_FOREGROUND */
    public static final char APP_STATUS_FOREGROUND = '0';
    /** APP_STATUS_BACKGROUND */
    public static final char APP_STATUS_BACKGROUND = '1';
    /** TAG */
    private static final String LOG_TAG = "TamicStat::ObserverPresenter";

    private ScheduleListener scheduleListener;


    public TcObserverPresenter(ScheduleListener listener) {
        scheduleListener = listener;
    }

    public void init(Context context ) {
        if (!isInit) {
            mPackageName = context.getPackageName();
            isTopTask = true;
            isScreenOff = false;
            isScreenLocked = false;
            isForeground = true;
            registerObserver(context);
            isInit = true;
        }
    }

    /**
     *  app is Foreground
     *
     * @return 如果在前台则返回true，否则返回false
     */
    public boolean isForeground() {
        return isForeground;
    }

    /**
     * getApp Status
     *
     * @return 前后为“0”，后台为“1”
     */
    public char getAppStatus() {
        if (isForeground) {
            return APP_STATUS_FOREGROUND;
        } else {
            return APP_STATUS_BACKGROUND;
        }
    }


    /**
     * OnStart
     *
     * @param aContext
     *            Context
     */
    public void onStart(Context aContext) {
        if (!isTopTask) {
            Log.d(LOG_TAG, "onStart,false-->onForegroundChanged");
            isTopTask = true;
            onForegroundChanged(aContext, true);
        }
    }

    /**
     * OnPause
     *
     * @param aContext
     *            Context
     */
    public void onPause(Context aContext) {
        Log.d(LOG_TAG, "onPause");
        if (isTopTask) {
            ActivityManager.RunningTaskInfo taskInfo = getRunningTaskInfo(aContext);
            if (taskInfo != null && taskInfo.topActivity != null) {
                String packageName = taskInfo.topActivity.getPackageName();
                if (!TextUtils.isEmpty(packageName)) {
                    if (!packageName.equals(mPackageName)) {
                        isTopTask = false;
                        StatLog.d(LOG_TAG, "onPause --> onForegroundChanged(false)");
                        onForegroundChanged(aContext, false);
                    }
                }
            }
        }
    }

    /**
     * OnStop
     *
     * @param aContext
     *            Context
     */
    public void onStop(Context aContext) {
        StatLog.d(LOG_TAG, "onStop");
        if (isTopTask) {
            ActivityManager.RunningTaskInfo taskInfo = getRunningTaskInfo(aContext);
            if (taskInfo != null && taskInfo.topActivity != null) {
                String packageName = taskInfo.topActivity.getPackageName();
                if (!TextUtils.isEmpty(packageName)) {
                    if (!packageName.equals(mPackageName)) {
                        isTopTask = false;
                        StatLog.d(LOG_TAG, "onStop --> onForegroundChanged(false)");
                        onForegroundChanged(aContext, false);
                    }
                }
            }
        }
    }


    /**
     * registerObserver
     * @param aContext
     *            Context
     */
    private void registerObserver(Context aContext) {

        if (mScreenObserver == null) {
            mScreenObserver = new TcScreenObserver(aContext, this);
        }
        mScreenObserver.start();

        if (mNetworkObserver == null) {
            mNetworkObserver = new TcNetworkObserver(aContext, this);
        }
        mNetworkObserver.start();

        if (mKeyguardObserver == null) {
            mKeyguardObserver = new TcDeblockObserver(aContext, this);
        }
        mKeyguardObserver.start();
    }

    /**
     * unregisterObserver
     */
    private void unregisterObserver() {

        if (mScreenObserver != null) {
            mScreenObserver.stop();
        }

        if (mKeyguardObserver != null) {
            mKeyguardObserver.stop();
        }

        if (mNetworkObserver != null) {
            mNetworkObserver.stop();
        }

    }


    /**
     * Destroy
     */
    public void destroy() {
        unregisterObserver();
        mScreenObserver = null;
        mKeyguardObserver = null;
        mNetworkObserver = null;
        //mDateObserver = null;
        isInit = false;
    }

    /**
     * 获取正在运行的TaskInfo
     *
     * @param aContext
     *            Context
     * @return RunningTaskInfo
     */
    private ActivityManager.RunningTaskInfo getRunningTaskInfo(Context aContext) {
        ActivityManager manager = (ActivityManager) aContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = manager.getRunningTasks(1);
        if (taskList == null || taskList.isEmpty()) {
            return null;
        } else {
            return taskList.get(0);
        }
    }

    /**
     * 判断当前是否锁屏
     *
     * @param aContext
     *            Context
     * @return true locked, false otherwise
     */
    private boolean isScreenLocked(Context aContext) {
        android.app.KeyguardManager km = (android.app.KeyguardManager) aContext
                .getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }


    /**
     * 前后台转换
     *
     * @param aContext
     *            Context
     * @param aIsForeground
     *            如果在前台则为true，否则为false
     */
    private synchronized void onForegroundChanged(Context aContext, boolean aIsForeground) {
        // 更新前后台状态
        isForeground = aIsForeground;
        //前后台变换时访问report
        reportData(aContext);
        //记录前后台切换
        if (aIsForeground) {
            if (StaticsConfig.DEBUG) {
                Log.d(LOG_TAG, "onForeground true");
            }
            // app唤醒
            DataConstruct.storeAppAction("3");
            //切前台时开始计时
            scheduleStart();

        } else {
            //切后台
            if (StaticsConfig.DEBUG) {
                Log.d(LOG_TAG, "onForeground false");
            }
            //切后台时数据上传
            TcStatSdk.getInstance(aContext).send();
            // 切后台停止计时
            scheduleStop();

        }
    }

    private void reportData(Context context) {

         TcStatSdk.getInstance(context).send();
    }

    private void scheduleStart() {
        if (scheduleListener != null) {
            scheduleListener.onStart();
        }

    }

    private void scheduleStop() {
        if (scheduleListener != null) {
            scheduleListener.onStop();
        }

    }

    private void scheduleReStart() {
        if (scheduleListener != null) {
            scheduleListener.onReStart();
        }

    }

    @Override
    public void onNetworkConnected(Context aContext) {
        StatLog.d(LOG_TAG, "onNetworkConnected");
        // 同步网络信息
        TcHeadrHandle.getHeader(aContext).setNetworkinfo(TcHeadrHandle.getNetWorkInfo(aContext));
        if (isForeground) {
            StatLog.d(LOG_TAG, "onNetworkConnected send data");
            reportData(aContext);
            scheduleReStart();
        } else {
            scheduleStop();
        }

    }

    @Override
    public void onNetworkUnConnected(Context aContext) {
        StatLog.d(LOG_TAG, "onNetworkUnConnected");
        scheduleStop();

    }

    @Override
    public void onScreenOn(Context aContext) {

        StatLog.d(LOG_TAG, "onScreenOn");
        //Toast.makeText(aContext, "屏幕亮起", Toast.LENGTH_SHORT).show();
        if (isTopTask) {
            if (isScreenOff) {
                isScreenOff = false;

                if (isScreenLocked(aContext)) { //如果锁屏，则不作处理
                    isScreenLocked = true;
                } else {
                    StatLog.d(LOG_TAG, "onScreenOn-->onForegroundChanged(true)");
                    isScreenLocked = false;
                    onForegroundChanged(aContext, true);
                }
            }
        }

    }

    @Override
    public void onScreenOff(Context aContext) {

        //屏幕关闭时，如果还在前台，屏幕之前打开，则肯定切换至后台
        StatLog.d(LOG_TAG, "onScreenOff");
        if (isTopTask) {
            if (!isScreenOff) {
                isScreenOff = true;
                if (!isScreenLocked) {
                    StatLog.d(LOG_TAG, "onScreenOff-->onForegroundChanged(false)");
                    onForegroundChanged(aContext, false);
                }
            }
        }

    }

    @Override
    public void onKeyguardGone(Context aContext) {
        StatLog.d(LOG_TAG, "onKeyGuardGone");
        if (isTopTask) {
            StatLog.d(LOG_TAG, "onKeyGuardGone foreground");
            //如果在前台，屏幕锁屏，则屏幕已打开
            if (isScreenLocked) {
                isScreenLocked = false;
                onForegroundChanged(aContext, true);
            }
        }
    }

    /**
     * IKeyguardListener
     */
    public interface ScheduleListener {


        /** 开始
         *
         */
        void onStart();

        /**
         *
         */
        void onStop();


        /**
         * 结束
         */
        void onReStart();
    }


}
