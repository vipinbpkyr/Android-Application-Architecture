/*
 * Copyright (C) 2015 The Android Open Source Project
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
 *
 */

package com.sysnatura.goldrate;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.sysnatura.goldrate.di.component.AppComponent;
import com.sysnatura.goldrate.di.component.DaggerAppComponent;
import com.sysnatura.goldrate.di.module.ApplicationModule;
import com.raizlabs.android.dbflow.config.FlowManager;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.support.multidex.MultiDex;

public class App extends Application {
    private AppComponent mAppComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        FlowManager.init(this);
        FlowManager.init(new FlowConfig.Builder(this).build());

        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @VisibleForTesting
    public void setAppComponent(AppComponent appComponent) {
        mAppComponent = appComponent;
    }
}