package com.niluogege.example.commonsdk.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.niluogege.example.commonsdk.BuildConfig;
import com.niluogege.example.commonsdk.utils.ILog;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class BaseApplication extends Application {
    private static Application app = null;


    /**
     * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        initARouter();
        initLog();

    }


    /**
     * 在模拟环境中程序终止会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public static Application getApplication() {
        return app;
    }

    /**
     * 初始化路由
     */
    private void initARouter() {
        if (BuildConfig.LOG_DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(getApplication()); // 尽可能早，推荐在Application中初始化
    }

    private void initLog() {
        initLogger();
        ILog.setLogSwitch(BuildConfig.LOG_DEBUG);
        ILog.setLogToFile(false);
    }

    private void initLogger() {

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 显示线程信息
                .methodCount(1)         // 方法栈打印的个数，默认是2
                .methodOffset(0)        // 设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .tag(getPackageName())  // 设置tag
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.LOG_DEBUG;
            }
        });

        //将日志缓存大本地 sd卡 的Logger文件夹中
//        Logger.addLogAdapter(new DiskLogAdapter());
    }


}
