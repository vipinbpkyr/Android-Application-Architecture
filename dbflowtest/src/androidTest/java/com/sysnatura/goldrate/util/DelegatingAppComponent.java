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

package com.sysnatura.goldrate.util;

import com.sysnatura.goldrate.api.ApiService;
import com.sysnatura.goldrate.config.DemoConfig;
import com.sysnatura.goldrate.controller.FeedController;
import com.sysnatura.goldrate.di.component.AppComponent;
import com.sysnatura.goldrate.job.feed.FetchFeedJob;
import com.sysnatura.goldrate.job.post.SaveNewPostJob;
import com.sysnatura.goldrate.model.FeedModel;
import com.sysnatura.goldrate.model.PostModel;
import com.sysnatura.goldrate.model.UserModel;
import com.path.android.jobqueue.JobManager;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

import org.greenrobot.eventbus.EventBus;


public class DelegatingAppComponent implements AppComponent {
    private final AppComponent mDelegate;
    public DelegatingAppComponent(AppComponent delegate) {
        mDelegate = delegate;
    }

    @Override
    public JobManager jobManager() {
        return mDelegate.jobManager();
    }

    @Override
    public UserModel userModel() {
        return mDelegate.userModel();
    }

    @Override
    public PostModel postModel() {
        return mDelegate.postModel();
    }

    @Override
    public EventBus eventBus() {
        return mDelegate.eventBus();
    }

    @Override
    public ApiService apiService() {
        return mDelegate.apiService();
    }

    @Override
    public FeedModel feedModel() {
        return mDelegate.feedModel();
    }

    @Override
    public FeedController feedController() {
        return mDelegate.feedController();
    }

    @Override
    public Context appContext() {
        return mDelegate.appContext();
    }

    @Override
    public DemoConfig demoConfig() {
        return mDelegate.demoConfig();
    }

    @Override
    public NotificationManagerCompat notificationManagerCompat() {
        return mDelegate.notificationManagerCompat();
    }

    @Override
    public void inject(FeedController feedController) {
        mDelegate.inject(feedController);
    }

    @Override
    public void inject(FeedModel feedModel) {
        mDelegate.inject(feedModel);
    }

    @Override
    public void inject(FetchFeedJob fetchFeedJob) {
        mDelegate.inject(fetchFeedJob);
    }

    @Override
    public void inject(SaveNewPostJob saveNewPostJob) {
        mDelegate.inject(saveNewPostJob);
    }
}
