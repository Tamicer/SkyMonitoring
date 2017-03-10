/*
 *    Copyright (C) 2016 Tamic
 *
 *    link :https://github.com/Tamicer
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.tamic.statInterface.statsdk.core;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Tamic on 2016-04-05.
 * StaticsManager
 */
public interface TcStaticsManager {

     boolean onInit(int appId, String channel, String fileName);

     void onSend();

     void onStore();

     void onRelease();

     void onRecordAppStart();

     void onRrecordPageEnd();

     void onRecordPageStart(Context context);

     void onRrecordAppEnd();

     void onInitPage(String... strings);

     void onPageParameter(String... strings);

     void onInitEvent(String eventName);

     void onEventParameter(String... strings);

     void onEvent(String var1, HashMap<String, String> var2);

}
