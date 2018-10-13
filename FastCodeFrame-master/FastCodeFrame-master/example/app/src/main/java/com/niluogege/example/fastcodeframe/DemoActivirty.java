package com.niluogege.example.fastcodeframe;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.niluogege.example.commonsdk.network.DefaultObserver;
import com.niluogege.example.commonsdk.network.RetryWithDelay;
import com.niluogege.example.fastcodeframe.bean.Data;
import com.niluogege.example.fastcodeframe.bean.NodeInfo;
import com.niluogege.example.fastcodeframe.bean.ResultInfo;
import com.niluogege.example.fastcodeframe.bean.VideoInfo;
import com.niluogege.example.fastcodeframe.net.RestfulApi;
import com.niluogege.example.fastcodeframe.utils.Constant;
import com.niluogege.example.fastcodeframe.utils.DialogUtil;
import com.niluogege.example.fastcodeframe.utils.SPUtil;
import com.niluogege.example.fastcodeframe.utils.StatusBarUtil;
import com.niluogege.example.fastcodeframe.view.explosionfield.ExplosionField;
import com.orhanobut.dialogplus.DialogPlus;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by niluogege on 2018/10/10.
 */

public class DemoActivirty extends RxAppCompatActivity {

    private int[] screenSize;
    private int imagewidth;
    private int imageHeight;
    private int statusBarHeight;
    private ExplosionField explosionField;
    private MediaPlayer mMediaPlayer;
    private View riv;
    private Object name;
    private int roomId;
    private TextView tv_countDown;
    private View rl_top;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        StatusBarUtil.setTransparentForImageViewInFragment(this, null);

        name = SPUtil.get(SPUtil.PRODUCT_PROPERTY, Constant.NAME);

        screenSize = getScreenSize(this);
        statusBarHeight = getStatusBarHeight(this);

        riv = findViewById(R.id.riv);
        tv_countDown = findViewById(R.id.tv_countDown);
        rl_top = findViewById(R.id.rl_top);


        imagewidth = dip2px(this, 50);
        imageHeight = dip2px(this, 50);

        explosionField = ExplosionField.attach2Window(this);

        setVideo();
        setImage();

        explosionField.setExplosionLisenner(new ExplosionField.onExplosionListener() {
            @Override
            public void onExplosionEnd() {
                if (mMediaPlayer != null) {
                    mMediaPlayer.pause();
                }
            }
        });

        riv.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                explosionField.explode(v, 200);
                if (mMediaPlayer != null) {
                    mMediaPlayer.start();
                }

                update();

                NodeInfo info = new NodeInfo();
                info.action = "hit_point";
                Data data = new Data();
                info.data = data;
                data.user_id = name.toString();
                String jsonString = JSON.toJSONString(info);
                Log.e("DemoActivirty", "jsonString=" + jsonString);
                mWebSocketClient.send(jsonString);
            }
        });

        connectionWs1();


        enter();
    }

    private void enter() {
        RestfulApi.getApiService().room(1, 0)
                .subscribeOn(Schedulers.io())
                .compose(DemoActivirty.this.bindToLifecycle())//compose方法需要在subscribeOn方法之后使用，因为在测试的过程中发现，将compose方法放在subscribeOn方法之前，如果在被观察者中执行了阻塞方法，比如Thread.sleep()，取消订阅后该阻塞方法不会被中断。
                .retryWhen(new RetryWithDelay())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Integer>() {

                    @Override
                    protected void onsuccess(Integer o) {
                        if (o != null) {
                            roomId = o;
                            Log.e(TAG, "roomId:" + roomId);
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) {
                        Log.e(TAG, throwable.getMessage());

                    }
                });
    }

    private void randomPoint() {
        float randomX = getRandomX();
        float randomY = getRandomY();

        riv.setVisibility(View.VISIBLE);
        riv.setAlpha(1);
        riv.setScaleX(1);
        riv.setScaleY(1);
        riv.setX(randomX);
        riv.setY(randomY);
    }

    private void update() {

        RestfulApi.getApiService().update(name.toString(), roomId)
                .subscribeOn(Schedulers.io())
                .compose(DemoActivirty.this.bindToLifecycle())//compose方法需要在subscribeOn方法之后使用，因为在测试的过程中发现，将compose方法放在subscribeOn方法之前，如果在被观察者中执行了阻塞方法，比如Thread.sleep()，取消订阅后该阻塞方法不会被中断。
                .retryWhen(new RetryWithDelay())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Object>() {
                    @Override
                    protected void onsuccess(Object o) {
                        Log.e(TAG, o.toString());
                    }

                    @Override
                    protected void onFail(Throwable throwable) {
                        Log.e(TAG, throwable.getMessage());

                    }
                });
    }


    //    private String address = "ws://118.31.223.114:8111";
    private String address = "ws://ws.t.xianghuanji.com:80/multi_online_game";
    private URI uri;
    private static final String TAG = "JavaWebSocket";
    private WebSocketClient mWebSocketClient;

    long count = 0l;

    private void connectionWs1() {
        try {
            uri = new URI(address);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (null == mWebSocketClient) {
            mWebSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.e(TAG, "onOpen: " + serverHandshake.toString());


                    NodeInfo info = new NodeInfo();
                    info.action = "game_prepare";
                    info.data = new Data();
                    String jsonString = JSON.toJSONString(info);
                    Log.e("DemoActivirty", "jsonString=" + jsonString);
                    mWebSocketClient.send(jsonString);
                }

                @Override
                public void onMessage(String s) {
                    Log.e(TAG, "onMessage: " + s);
                    if (s != null && !TextUtils.equals("", s)) {
                        NodeInfo parse = JSON.parseObject(s, new TypeReference<NodeInfo>() {
                        });
                        String action = parse.action;
                        if (TextUtils.equals("time_limited_point", action)) {
                            runOnUiThread(() -> randomPoint());
                        } else if (TextUtils.equals("game_over", action)) {
                            gameOver();
                        } else if (TextUtils.equals("game_wait_time", action)) {
                            waitTime(parse);
                        }
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Log.e(TAG, "onClose: " + " i=" + i + " s=" + i + " b=" + b);
                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, "onError: " + e);
                }
            };
            mWebSocketClient.connect();
        }

    }

    private void waitTime(NodeInfo parse) {
        Data data = parse.data;
        if (data != null) {
            count = (data.begin_time - data.client_time);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CountDownTimer timer = new CountDownTimer(count * 1000, 1000) {
                        @Override
                        public void onTick(long l) {
                            count--;
                            tv_countDown.setText(count + "s");
                            Log.e("DemoActivirty", "count= " + count);
                        }

                        @Override
                        public void onFinish() {
                            rl_top.setVisibility(View.GONE);
                            NodeInfo info = new NodeInfo();
                            info.action = "game_start";
                            info.data = new Data();
                            String jsonString = JSON.toJSONString(info);
                            Log.e("DemoActivirty", "game_start");
                            mWebSocketClient.send(jsonString);
                        }
                    };
                    timer.start();
                }
            });
        }
    }

    private void gameOver() {
        RestfulApi.getApiService().result(name.toString(), roomId)
                .subscribeOn(Schedulers.io())
                .compose(DemoActivirty.this.bindToLifecycle())//compose方法需要在subscribeOn方法之后使用，因为在测试的过程中发现，将compose方法放在subscribeOn方法之前，如果在被观察者中执行了阻塞方法，比如Thread.sleep()，取消订阅后该阻塞方法不会被中断。
                .retryWhen(new RetryWithDelay())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<ResultInfo>() {
                    @Override
                    protected void onsuccess(ResultInfo info) {
                        if (info != null) {
                            boolean the_one = info.is_the_one();
                            showEndDialog(the_one);
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) {
                        Log.e(TAG, throwable.getMessage());

                    }
                });
    }


    private void setImage() {
        Object o = SPUtil.get(SPUtil.PRODUCT_PROPERTY, Constant.IMAGE_SELECT);
        if (o != null) {
            Integer image = MAppAplication.getInstance().imageMap.get(o).getImage();
            ((ImageView) riv).setImageResource(image);
        } else {
            ((ImageView) riv).setImageResource(R.mipmap.tx_1);
        }
    }

    private void setVideo() {
        Object o = SPUtil.get(SPUtil.PRODUCT_PROPERTY, Constant.VIDEO_SELECT);
        if (o != null) {
            VideoInfo videoInfo = MAppAplication.getInstance().voideMap.get(o);
            mMediaPlayer = MediaPlayer.create(DemoActivirty.this, videoInfo.getFileName());
        } else {
            mMediaPlayer = MediaPlayer.create(DemoActivirty.this, R.raw.a);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }

        if (mWebSocketClient != null) mWebSocketClient.close();
    }

    private void showEndDialog(boolean isWinner) {
        DialogPlus dialogPlus = DialogUtil.createCommonDialog(this, R.layout.dialog_end, null);
        ImageView iv = (ImageView) dialogPlus.findViewById(R.id.iv);
        if (isWinner) {
            iv.setImageResource(R.mipmap.nb);
        } else {
            iv.setImageResource(R.mipmap.nb);
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
        dialogPlus.show();
    }

}
