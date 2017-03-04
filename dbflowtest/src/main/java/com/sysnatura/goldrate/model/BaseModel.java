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

package com.sysnatura.goldrate.model;

import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.sysnatura.goldrate.App;
import com.sysnatura.goldrate.di.component.AppComponent;

import android.database.sqlite.SQLiteDatabase;

public class BaseModel {

    protected final DatabaseWrapper mSQLiteDatabase;
    protected final AppComponent mComponent;

    public BaseModel(App app, DatabaseWrapper database) {
        mComponent = app.getAppComponent();
        mSQLiteDatabase = database;
    }
}
