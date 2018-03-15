/*
 * Copyright (C) 2013 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tamic.statinterface.stats.service;

import android.os.Build;
import android.util.Log;

import java.util.concurrent.Executor;

/**
 * Created by Tamic on 2016-09-21.
 * {@link # https://github.com/NeglectedByBoss}
 */
public class Platform {
    private static final Platform PLATFORM = findPlatform();

    public static Platform get() {
        Log.v("TcStatInterfacePlatform", PLATFORM.getClass().toString());
        return PLATFORM;
    }

    private static Platform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return new Android();
            }
        } catch (ClassNotFoundException ignored) {
        }
        return new Platform();
    }

    public Executor defaultCallbackExecutor() {
        return new TcHandleThreadPool().getExecutor();
    }

    public Object execute(Runnable runnable) {
        defaultCallbackExecutor().execute(runnable);
        return null;
    }

    static class Android extends Platform {
        @Override
        public Executor defaultCallbackExecutor() {
            return new TcHandleThreadPool().getExecutor();
        }

    }

}
