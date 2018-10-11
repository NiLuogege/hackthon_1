package com.niluogege.example.fastcodeframe;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.niluogege.example.fastcodeframe.view.explosionfield.ExplosionField;

import java.util.Random;


/**
 * Created by niluogege on 2018/10/10.
 */

public class DemoActivirty extends AppCompatActivity {

    private int[] screenSize;
    private int imagewidth;
    private int imageHeight;
    private int statusBarHeight;
    private ExplosionField explosionField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        screenSize = getScreenSize(this);
        statusBarHeight = getStatusBarHeight(this);

        View btn = findViewById(R.id.btn);
        View riv = findViewById(R.id.riv);


        imagewidth = dip2px(this, 50);
        imageHeight = dip2px(this, 50);

        explosionField = ExplosionField.attach2Window(this);

//        Log.e("DemoActivirty", "imagewidth:" + imagewidth + " imageHeight:" + imageHeight + " screenwidth: " + screenSize[0] + " screenHeight: " + screenSize[1]);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float randomX = getRandomX();
                float randomY = getRandomY();
//                if (randomY > 1770) {
//                    Log.e("DemoActivirty", "越界了");
//                }
//                Log.e("DemoActivirty", "randomX:" + randomX + " randomY:" + randomY);
                riv.setAlpha(1);
                riv.setScaleX(1);
                riv.setScaleY(1);
                riv.setX(randomX);
                riv.setY(randomY);
            }
        });

        riv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explosionField.explode(v);
                MediaPlayer mMediaPlayer= MediaPlayer.create(DemoActivirty.this, R.raw.a);
                mMediaPlayer.start();
            }
        });

    }

    private float getRandomX() {
        int width = screenSize[0];
        Random random = new Random();
        return random.nextInt(width - imagewidth);
    }

    private float getRandomY() {
        int height = screenSize[1];
        Random random = new Random();
        int nextInt = random.nextInt(height - imageHeight - statusBarHeight);
        return nextInt > statusBarHeight ? nextInt : statusBarHeight;
    }

    /**
     * 获取屏幕尺寸
     *
     * @return
     */
    public static int[] getScreenSize(Context context) {
        int[] screenSize = new int[2];
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        screenSize[0] = width;
        screenSize[1] = height;
        return screenSize;
    }

    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
