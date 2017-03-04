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

import com.sysnatura.goldrate.di.component.TestComponent;
import com.sysnatura.goldrate.util.TestUtil;

import org.junit.Before;
import org.junit.Test;

import android.support.test.InstrumentationRegistry;
import android.test.AndroidTestCase;

public class BaseTest extends AndroidTestCase {
    private TestComponent mTestComponent;

    protected TestComponent getTestComponent() {
        return mTestComponent;
    }

    @Before
    public final void setupBaseTest() throws Exception {
        super.setUp();
        App app = (App) InstrumentationRegistry.getTargetContext().getApplicationContext();
        mTestComponent = TestUtil.prepare(app);
    }

    @Test
    public void setupCheck() throws Exception {
        assertNotNull(mTestComponent);
    }
}
