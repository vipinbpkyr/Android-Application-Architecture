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

import com.sysnatura.goldrate.App;
import com.sysnatura.goldrate.di.component.DaggerTestComponent;
import com.sysnatura.goldrate.di.component.TestComponent;
import com.sysnatura.goldrate.di.module.ApplicationModule;
import com.sysnatura.goldrate.di.module.TestApplicationModule;
import com.sysnatura.goldrate.event.LoggingBus;
import com.sysnatura.goldrate.model.DemoDatabase;
import com.sysnatura.goldrate.vo.FeedItem;
import com.sysnatura.goldrate.vo.Post;
import com.sysnatura.goldrate.vo.User;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;
import com.raizlabs.android.dbflow.config.FlowManager;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

import javax.inject.Singleton;

import dagger.Provides;
import org.greenrobot.eventbus.EventBus;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtil {

    private static long sPostIdCounter = 1;

    public static Post createDummyPost() {
        return createDummyPost(null);
    }
    
    public static TestComponent prepare(App app) {
        FlowManager.destroy();
        resetSingleton(FlowManager.class, "mDatabaseHolder");
        ApplicationModule appModule = new ApplicationModule(app) {
            @Override
            public EventBus eventBus() {
                return new LoggingBus();
            }
            @Provides
            @Singleton
            public JobManager jobManager() {
                JobManager mock = mock(JobManager.class);
                when(mock.addJob(any(Job.class))).thenReturn(1L);
                return mock;
            }
        };
        TestComponent testComponent = DaggerTestComponent.builder()
                .testApplicationModule(new TestApplicationModule())
                .applicationModule(appModule)
                .build();
        testComponent.appContext().deleteDatabase(DemoDatabase.NAME + ".db");
        FlowManager.init(app);
        testComponent.feedModel().clear();
        testComponent.loggingBus().clear();
        return testComponent;
    }

    public static Post createDummyPost(Long userId) {
        Post post = new Post();
        post.setId(sPostIdCounter++);
        post.setText(UUID.randomUUID().toString());
        post.setClientId(UUID.randomUUID().toString());
        if (userId == null) {
            post.setUserId(1);
        } else {
            post.setUserId(userId);
        }

        post.setCreated(System.currentTimeMillis());
        return post;
    }

    private static long sUserIdCounter = 1;

    public static User createDummyUser() {
        User user = new User();
        user.setId(sUserIdCounter++);
        user.setName(UUID.randomUUID().toString());
        return user;
    }

    public static FeedItem createDummyFeedItem() {
        User user = createDummyUser();
        Post post = createDummyPost(user.getId());
        return new FeedItem(post, user);
    }


    public static <T> Call<T> createCall(T response) {
        return new MockCall<>(response);
    }

    public static <T> Call<T> createCall(int responseCode, T response) {
        return new MockCall<>(response, responseCode);
    }

    private static class MockCall<T> implements Call<T> {

        final T mResponse;

        final int mCode;

        public MockCall(T response, int code) {
            mResponse = response;
            mCode = code;
        }

        public MockCall(T response) {
            this(response, 200);
        }

        @Override
        public Response<T> execute() throws IOException {
            return buildResponse();
        }

        @NonNull
        private Response<T> buildResponse() {
            if (mCode > 199 && mCode < 300) {
                return Response.success(mResponse);
            } else {
                return Response.error(mCode, null);
            }
        }

        @Override
        public void enqueue(Callback<T> callback) {
            callback.onResponse(null,buildResponse());
        }

        @Override
        public boolean isExecuted() {
            return false;
        }

        @Override
        public void cancel() {

        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @SuppressWarnings("CloneDoesntCallSuperClone")
        @Override
        public Call<T> clone() {
            return new MockCall<>(mResponse);
        }

        @Override
        public Request request() {
            return null;
        }
    }

    private static void resetSingleton(Class clazz, String fieldName) {
        Field instance;
        try {
            instance = clazz.getDeclaredField(fieldName);
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
