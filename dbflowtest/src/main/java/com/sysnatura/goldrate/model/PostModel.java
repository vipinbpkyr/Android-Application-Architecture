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

import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.sysnatura.goldrate.App;
import com.sysnatura.goldrate.util.ValidationUtil;
import com.sysnatura.goldrate.vo.Post;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.sysnatura.goldrate.vo.Post_Table;

import org.apache.commons.lang3.StringUtils;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.List;

public class PostModel extends BaseModel {

    public PostModel(App app, DatabaseWrapper database) {
        super(app, database);
    }

    public List<Post> loadPostsSince(long since) {
       /* return new Select().from(Post.class).where(
                Condition.column(Post$Table.MCREATED).greaterThan(since)
        ).queryList();*/

        // single result, we apply a limit(1) automatically to get the result even faster.
        return SQLite.select()
                .from(Post.class)
                .where(Post_Table.mCreated.greaterThan(since))
                .queryList();
    }

    public List<Post> loadPostsOfUser(long userId, long since) {
        return new Select().from(Post.class).where(
                Post_Table.mCreated.greaterThan(since)
        ).and(Post_Table.mUserId.eq(userId)).queryList();
    }

    public Post load(long id) {
        return new Select().from(Post.class)
                .where(Post_Table.mId.eq(id)).querySingle();
    }

    public synchronized void save(Post post) {
        ValidationUtil.validate(post);
        saveValid(post);
    }

    public synchronized void saveAll(final List<Post> posts) {
        ValidationUtil.pruneInvalid(posts);
        if (posts.isEmpty()) {
            return;
        }
        for (Post post : posts) {
            saveValid(post);
        }
        /*TransactionManager.transact(mSQLiteDatabase, new Runnable() {
            @Override
            public void run() {
                for (Post post : posts) {
                    saveValid(post);
                }
            }
        });*/
    }

    private void saveValid(Post post) {
        Post existing = loadByClientIdAndUserId(post.getClientId(), post.getUserId());
        if (existing == null) {
            post.save();
        } else {
            post.setId(existing.getId());
            post.update();
        }
    }

    @Nullable
    public synchronized Post loadByClientIdAndUserId(String clientId, long userId) {
        if (StringUtils.isEmpty(clientId)) {
            return null;
        }
//        return new Select().from(Post.class)
//                .where(Condition.column(Post_Table.mClientId).eq(clientId))
//                .and(Condition.column(Post_Table.mUserId).eq(userId))
//                .querySingle();

        return SQLite.select()
                .from(Post.class)
                .where(Post_Table.mClientId.eq(clientId))
                .and(Post_Table.mUserId.eq(userId))
                .querySingle();
    }

    public void delete(Post post) {
        post.delete();
    }
}
