
package com.tamic.statInterface.statsdk.core;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;


import java.util.Set;


/**
 * Intent Manager
 * Created by Tamic on 2016-04-15.
 * @author  Tamic
 */
public final class TcIntentManager {
	/** is normal start*/
	private boolean mNotMainIntent;
	/** instance */
	private static TcIntentManager sInstance;

	/**
	 * @return instance
	 */
	public static synchronized TcIntentManager getInstance() {
		if (sInstance == null) {
			sInstance = new TcIntentManager();
		}
		return sInstance;
	}

	/**
	 * private constructor
	 */
	private TcIntentManager() {
		
	}

	/**
	 * @return true表示不是通过程序icon进入程序
	 */
	public boolean isNotMainIntent() {
		return mNotMainIntent;
	}

	/**
	 * 设置是否是通过程序icon进入程序
	 * @param aFlag true表示不是通过程序icon进入程序
	 */
	public void setNotMainIntent(boolean aFlag) {
		mNotMainIntent = aFlag;
	}
	
	/**
	 * isActionValidate
	 * @param aIntent intent
	 * @return action  Validate
	 */
	public static boolean isActionValidate(final Intent aIntent) {
		return ((aIntent != null) && (!TextUtils.isEmpty(aIntent.getAction())));
	}
	
	/**
	 * @param aIntent intent
	 * @return intent类型是否是ACTION_MAIN
	 */
	public boolean isMainIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_MAIN);
	}

	/**
	 * @param aIntent intent
	 * @return intent类型是否是view
	 */
	public boolean isViewIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_VIEW);
	}
	
	/**
	 * @param aIntent intent
	 * @return intent类型是否是search
	 */
	public boolean isSearchIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_SEARCH);
	}

	/**
	 * @param aIntent intent
	 * @return intent类型是否是web search
	 */
	public boolean isWebSearchIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_WEB_SEARCH);
	}
	
	/**
	 * @param aIntent intent
	 * @return intent类型是否是ACTION_DATE_CHANGED
	 */
	public boolean isDateChangedIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_DATE_CHANGED);
	}

	/**
	 * @param aIntent intent
	 * @return intent类型是否是ACTION_USER_PRESENT
	 */
	public boolean isUserPresentIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_USER_PRESENT);
	}

	/**
	 * @param aIntent intent
	 * @return intent类型是否是ACTION_SCREEN_ON
	 */
	public boolean isScreenOnIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_SCREEN_ON);
	}

	/**
	 * @param aIntent intent
	 * @return intent类型是否是ACTION_SCREEN_OFF
	 */
	public boolean isScreenOffIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_SCREEN_OFF);
	}

	/**
	 * @param aIntent intent
	 * @return intent类型是否是ACTION_DEVICE_STORAGE_LOW
	 */
	public boolean isDeviceStorageLowIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_DEVICE_STORAGE_LOW);
	}

	/**
	 * @param aIntent intent
	 * @return intent类型是否是ACTION_CLOSE_SYSTEM_DIALOGS
	 */
	public boolean isCloseSystemDialogsIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
	}

	/**
	 * @param aIntent intent
	 * @return intent类型是否是ACTION_BATTERY_CHANGED
	 */
	public boolean isBatteryChangedIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_BATTERY_CHANGED);
	}
	
	/**
	 * @param aIntent intent
	 * @return intent类型是否是ACTION_HEADSET_PLUG
	 */
	public boolean isHeadsetPlugIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_HEADSET_PLUG);
	}

	/**
	 * @param aIntent intent
	 * @return intent类型是否是ACTION_BOOT_COMPLETED
	 */
	public boolean isBootCompletedIntent(final Intent aIntent) {
		return isActionAs(aIntent, Intent.ACTION_BOOT_COMPLETED);
	}
	
	/**
	 * @param aIntent intent
	 * @return intent类型是否是CONNECTIVITY_ACTION
	 */
	public boolean isConnectivityIntent(final Intent aIntent) {
		return isActionAs(aIntent, ConnectivityManager.CONNECTIVITY_ACTION);
	}
	
	/**
	 * @param aIntent intent 
	 * @param aAction action
	 * @return intent的action类型是否是给定的action
	 */
	private boolean isActionAs(final Intent aIntent, final String aAction) {
		final String action = getAction(aIntent);
		return ((action != null) && (aAction != null) && (action.equals(aAction)));
	}
	
	/**
	 * 从intent获取action
	 * @param aIntent intent
	 * @return action
	 */
	private static String getAction(final Intent aIntent) {
		if (aIntent == null) {
			return null;
		}
		return aIntent.getAction();
	}
	
	/**
	 * @param aIntent intent
	 * @param aCategory category
	 * @return 是否包含指定category类型
	 */
	private static boolean isCategoryAs(final Intent aIntent, final String aCategory) {
		final Set<String> categories = getCategories(aIntent);
		return ((categories != null) && (aCategory != null) && (categories.contains(aCategory)));
	}

	/**
	 * @param aIntent intent
	 * @return categories
	 */
	private static Set<String> getCategories(final Intent aIntent) {
		if (aIntent == null) {
			return null;
		}
		return aIntent.getCategories();
	}

}
