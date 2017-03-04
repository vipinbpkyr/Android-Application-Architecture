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

package com.sysnatura.goldrate.di.component;

import com.sysnatura.goldrate.api.ApiModule;
import com.sysnatura.goldrate.api.ApiService;
import com.sysnatura.goldrate.config.DemoConfig;
import com.sysnatura.goldrate.controller.FeedController;
import com.sysnatura.goldrate.di.module.ApplicationModule;
import com.sysnatura.goldrate.job.feed.FetchFeedJob;
import com.sysnatura.goldrate.job.post.SaveNewPostJob;
import com.sysnatura.goldrate.model.FeedModel;
import com.sysnatura.goldrate.model.PostModel;
import com.sysnatura.goldrate.model.UserModel;
import com.path.android.jobqueue.JobManager;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

import javax.inject.Singleton;

import dagger.Component;
import org.greenrobot.eventbus.EventBus;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface AppComponent {

    JobManager jobManager();

    UserModel userModel();

    PostModel postModel();

    EventBus eventBus();

    ApiService apiService();

    FeedModel feedModel();

    FeedController feedController();

    Context appContext();

    DemoConfig demoConfig();

    NotificationManagerCompat notificationManagerCompat();

    void inject(FeedController feedController);

    void inject(FeedModel feedModel);

    void inject(FetchFeedJob fetchFeedJob);

    void inject(SaveNewPostJob saveNewPostJob);
}
