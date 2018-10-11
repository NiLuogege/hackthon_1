package com.niluogege.example.commonsdk.utils;

import android.app.Activity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/9/22.
 * 管理Activity
 * chenfeng
 */
public class ActivityManager {

    private static ActivityManager mInstance;

    private List<Activity> mActivityList = new ArrayList<>();

    private ActivityManager() {

    }

    /**
     * 实例化
     *
     * @return
     */
    public static ActivityManager getInstance() {
        if (mInstance == null) {
            synchronized (ActivityManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
        if (mActivityList == null || mActivityList.contains(activity)) {
            return;
        }
        mActivityList.add(activity);
    }

    /**
     * 获取集合中Activity的个数
     */
    public int getActivityListSize() {
        if (mActivityList == null) {
            return 0;
        }
        return mActivityList.size();
    }


    /**
     * 获取activity
     *
     * @param activityCanonicalName 页面类的CanonicalName，比如：MainActivity.class.getCanonicalName().
     * @return
     */
    public Activity getActivity(String activityCanonicalName) {
        if (TextUtils.isEmpty(activityCanonicalName) || mActivityList == null) {
            return null;
        }
        for (Activity activity : mActivityList) {
            if (activityCanonicalName.equals(activity.getClass().getCanonicalName())) {
                return activity;
            }
        }
        return null;
    }


    /**
     * 移除Activity
     */
    public void removeActivity(Activity activity) {
        if (mActivityList == null || !mActivityList.contains(activity)) {
            return;
        }
        mActivityList.remove(activity);
    }

    /**
     * finishActivity
     */
    public void finishActivity(String activityCanonicalName) {
        if (TextUtils.isEmpty(activityCanonicalName) || mActivityList == null) {
            return;
        }
        for (Activity activity : mActivityList) {
            if (activityCanonicalName.equals(activity.getClass().getCanonicalName())) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }


    /**
     * 判断某个页面是否存在
     *
     * @param activityCanonicalName 页面类的CanonicalName，比如：MainActivity.class.getCanonicalName().
     * @return
     */
    public boolean isActivityRunning(String activityCanonicalName) {
        if (TextUtils.isEmpty(activityCanonicalName) || mActivityList == null) {
            return false;
        }
        for (Activity activity : mActivityList) {
            if (activityCanonicalName.equals(activity.getClass().getCanonicalName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * finish所有的Activity除了指定需要保留的页面
     *
     * @param activityCanonicalName 页面类的CanonicalName，比如：MainActivity.class.getCanonicalName().
     */
    public void finishAllWithout(String activityCanonicalName) {
        if (TextUtils.isEmpty(activityCanonicalName) || mActivityList == null) {
            return;
        }
        for (Activity activity : mActivityList) {
            if (activityCanonicalName.equals(activity.getClass().getCanonicalName())) {
                continue;
            }

            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * finish所有的Activity
     */
    public void finishAllActivity() {
        if (mActivityList == null) {
            return;
        }
        for (Activity activity : mActivityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 退出app
     */
    public void appExit() {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
