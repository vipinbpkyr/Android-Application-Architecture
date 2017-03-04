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

import com.sysnatura.goldrate.di.module.TestApiServiceModule;
import com.sysnatura.goldrate.job.feed.FetchFeedJobTest;
import com.sysnatura.goldrate.model.FeedModelTest;
import com.sysnatura.goldrate.model.PostModelTest;
import com.sysnatura.goldrate.di.module.ApplicationModule;
import com.sysnatura.goldrate.di.module.TestApplicationModule;
import com.sysnatura.goldrate.event.LoggingBus;
import com.sysnatura.goldrate.model.UserModelTest;

import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestApplicationModule.class, ApplicationModule.class,
        TestApiServiceModule.class})
public interface TestComponent extends AppComponent {

    void inject(PostModelTest postModelTest);

    LoggingBus loggingBus();

    SQLiteDatabase database();

    void inject(FeedModelTest feedModelTest);

    void inject(UserModelTest userModelTest);

    void inject(FetchFeedJobTest fetchFeedJobTest);
}
