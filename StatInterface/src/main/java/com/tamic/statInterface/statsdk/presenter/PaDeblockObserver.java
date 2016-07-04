
package com.tamic.statInterface.statsdk.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.tamic.statInterface.statsdk.constants.StaticsConfig;
import com.tamic.statInterface.statsdk.core.TcIntentManager;
import com.tamic.statInterface.statsdk.util.StatLog;

/**
 * DeblockObserver
 * Created by Tamic on 2016-04-15.
 */
public class PaDeblockObserver extends BroadcastReceiver {

	/** DEBUG mode */
	private static final boolean DEBUG = StaticsConfig.DEBUG;
	/** StatLog TAG */
	private static final String StatLog_TAG = PaDeblockObserver.class.getSimpleName();
	/** Context */
	private Context mContext;
	/** IKeyguardListener */
	private IKeyguardListener mListener;

	/**
	 * Constructor
	 *
	 * @param aContext
	 *            Context
	 * @param aListener
	 *            IScreenListener
	 */
	public PaDeblockObserver(Context aContext, IKeyguardListener aListener) {
		mContext = aContext;
		mListener = aListener;
	}

	/**
	 * start Listener
	 */
	public void start() {
		try {
			IntentFilter filter = new IntentFilter();
			filter.addAction(Intent.ACTION_USER_PRESENT);
            mContext.registerReceiver(this, filter);

			if (!isScreenLocked(mContext)) {
				if (mListener != null) {
					mListener.onKeyguardGone(mContext);
				}
			}
		} catch (Exception e) {
			if (DEBUG) {
				StatLog.w(StatLog_TAG, "start Exception", e);
			}
		}
	}

	/**
	 * stop Listener
	 */
	public void stop() {
		try {
			mContext.unregisterReceiver(this);
		} catch (Exception e) {
			if (DEBUG) {
				StatLog.w(StatLog_TAG, "stop Exception", e);
			}
		}
	}

    /**
     * is Screen Locked
     *
     * @param aContext Context
     * @return true if screen locked, false otherwise
     */
	public boolean isScreenLocked(Context aContext) {
		android.app.KeyguardManager km = (android.app.KeyguardManager) aContext
				.getSystemService(Context.KEYGUARD_SERVICE);
		return km.inKeyguardRestrictedInputMode();
	}

	@Override
	public void onReceive(Context aContext, Intent aIntent) {
		if (TcIntentManager.getInstance().isUserPresentIntent(aIntent)) {
			if (mListener != null) {
				mListener.onKeyguardGone(aContext);
			}
		}
	}

	/**
	 * IKeyguardListener
	 */
	public interface IKeyguardListener {

		/**
		 * unlock
		 * 
		 * @param aContext
		 *            Context
		 */
		void onKeyguardGone(Context aContext);
	}
}
