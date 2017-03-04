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

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.sysnatura.goldrate.App;
import com.sysnatura.goldrate.util.ValidationUtil;
import com.sysnatura.goldrate.vo.User;
import com.raizlabs.android.dbflow.list.FlowCursorList;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.sysnatura.goldrate.vo.User_Table;

import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserModel extends BaseModel {

    public UserModel(App app, DatabaseWrapper database) {
        super(app, database);
    }

    public void save(User user) {
        user.validate();
        user.save();
    }

    public void saveAll(final List<User> users) {
        ValidationUtil.pruneInvalid(users);
        if (users.isEmpty()) {
            return;
        }

        for (User user : users) {
            user.save();
        }

       /* TransactionManager.transact(mSQLiteDatabase, new Runnable() {
            @Override
            public void run() {
                for (User user : users) {
                    user.save();
                }
            }
        });*/

        ArrayList<User> users = new ArrayList<>();

// fetch users from the network

// save rows
        FlowManager.getDatabase(DemoDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<User>() {
                            @Override
                            public void processModel(User user) {
                                // do work here -- i.e. user.delete() or user.update()
                                user.save();
                            }
                        }).addAll(users).build())  // add elements (can also handle multiple)
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {

                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {

                    }
                }).build().execute();

    }

    public User load(long id) {
        /*return new Select().from(User.class)
                .where(Condition.column(User$Table.MID).eq(id))
                .querySingle();*/

        return SQLite.select()
                .from(User.class)
                .where(User_Table.mId.eq(id))
                .querySingle();
    }

    public Map<Long, User> loadUsersAsMap(List<Long> userIds) {
        final HashMap<Long, User> result = new HashMap<>();
        if (userIds.isEmpty()) {
            return result;
        }
        long first = userIds.get(0);
        List<Long> rest = userIds.subList(1, userIds.size());// correct this
        /*FlowCursorList<User> userFlowCursorList = new Select().from(User.class).where(
                Condition.column(User_Table.mId).in(first, rest.toArray())
        ).queryCursorList();*/
        long aaa = 0;
        FlowCursorList<User> userFlowCursorList = new Select().from(User.class).where(
                User_Table.mId.in(first, aaa)
        ).cursorList();
        try {
            final int size = userFlowCursorList.getCount();
            for (int i = 0; i < size; i++) {
                User user = userFlowCursorList.getItem(i);
                result.put(user.getId(), user);
            }
        } finally {
            userFlowCursorList.close();
        }

        return result;
    }
}
