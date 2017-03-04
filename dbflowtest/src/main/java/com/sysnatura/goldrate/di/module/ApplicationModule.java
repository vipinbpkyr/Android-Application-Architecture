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

package com.sysnatura.goldrate.di.module;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.di.DependencyInjector;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.sysnatura.goldrate.App;
import com.sysnatura.goldrate.config.DemoConfig;
import com.sysnatura.goldrate.controller.FeedController;
import com.sysnatura.goldrate.job.BaseJob;
import com.sysnatura.goldrate.model.DemoDatabase;
import com.sysnatura.goldrate.model.FeedModel;
import com.sysnatura.goldrate.model.PostModel;
import com.sysnatura.goldrate.model.UserModel;
import com.sysnatura.goldrate.util.L;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final App mApp;

    public ApplicationModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public UserModel userModel(DatabaseWrapper database) {
        return new UserModel(mApp, database);
    }

    @Provides
    @Singleton
    public PostModel postModel(DatabaseWrapper database) {
        return new PostModel(mApp, database);
    }

    @Provides
    @Singleton
    public FeedModel feedModel(DatabaseWrapper database) {
        return new FeedModel(mApp, database);
    }

    @Provides
    @Singleton
    public Context appContext() {
        return mApp;
    }

    @Provides
    @Singleton
    public FeedController feedController() {
        return new FeedController(mApp.getAppComponent());
    }

    @Provides
    @Singleton
    public EventBus eventBus() {
        return new EventBus();
    }

    /*@Provides
    @Singleton
    public SQLiteDatabase database() {
        return FlowManager.getDatabase(DemoDatabase.NAME).getWritableDatabase();
    }

    }*/

    @Provides
    @Singleton
    public DatabaseWrapper database() {
        return FlowManager.getDatabase(DemoDatabase.NAME).getWritableDatabase();
    }

    @Provides
    @Singleton
    public DemoConfig demoConfig() {
        return new DemoConfig(mApp);
    }

    @Provides
    @Singleton
    public JobManager jobManager() {
        Configuration config = new Configuration.Builder(mApp)
                .consumerKeepAlive(45)
                .maxConsumerCount(3)
                .minConsumerCount(1)
                .customLogger(L.getJobLogger())
                .injector(new DependencyInjector() {
                    @Override
                    public void inject(Job job) {
                        if (job instanceof BaseJob) {
                            ((BaseJob) job).inject(mApp.getAppComponent());
                        }
                    }
                })
                .build();
        return new JobManager(mApp, config);
    }

    @Provides
    @Singleton
    public NotificationManagerCompat notificationCompat() {
        return NotificationManagerCompat.from(mApp);
    }
}