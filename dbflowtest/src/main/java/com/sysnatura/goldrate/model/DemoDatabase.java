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

import android.util.Log;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.migration.BaseMigration;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

@Database(name = DemoDatabase.NAME, version = 2)
public class DemoDatabase {

    public static final String NAME = "demo";

    @Migration(version = 2, database = DemoDatabase.class)
    public static class Migration2 extends BaseMigration {

        @Override
        public void migrate(DatabaseWrapper database) {
            // run some code here
            Log.d("DemoDatabase","migrate");
//            SQLite.update(Employee.class)
//                    .set(Employee_Table.status.eq("Invalid"))
//                    .where(Employee_Table.job.eq("Laid Off"))
//                    .execute(database); // required inside a migration to pass the wrapper
        }
    }
}
