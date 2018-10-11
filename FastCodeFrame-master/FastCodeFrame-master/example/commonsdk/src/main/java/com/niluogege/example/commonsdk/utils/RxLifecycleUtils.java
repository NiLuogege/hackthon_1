/*
 * Copyright 2017 JessYan
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
 */
package com.niluogege.example.commonsdk.utils;

import com.niluogege.example.commonsdk.base.BaseActivity;
import com.niluogege.example.commonsdk.base.BaseLazyFragment;
import com.niluogege.example.commonsdk.base.mvp.IView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.annotations.NonNull;

/**

 */

public class RxLifecycleUtils {

    private RxLifecycleUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }


    /**
     * 绑定 Activity/Fragment 的生命周期
     *
     * @param view
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull IView view) {
        Preconditions.checkNotNull(view, "view == null");
        if (view instanceof LifecycleProvider) {
            return bindToLifecycle((LifecycleProvider) view);
        } else {
            throw new IllegalArgumentException("view isn't Lifecycleable");
        }
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull LifecycleProvider lifecycleable) {
        Preconditions.checkNotNull(lifecycleable, "lifecycleable == null");
        if (lifecycleable instanceof BaseActivity) {
            return lifecycleable.bindToLifecycle();
        } else if (lifecycleable instanceof BaseLazyFragment) {
            return lifecycleable.bindToLifecycle();
        } else {
            throw new IllegalArgumentException("Lifecycleable not match");
        }
    }
}
