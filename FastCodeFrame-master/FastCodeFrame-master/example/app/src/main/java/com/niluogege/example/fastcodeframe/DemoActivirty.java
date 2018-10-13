package com.niluogege.example.fastcodeframe;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.niluogege.example.commonsdk.network.DefaultObserver;
import com.niluogege.example.commonsdk.network.RetryWithDelay;
import com.niluogege.example.fastcodeframe.bean.VideoInfo;
import com.niluogege.example.fastcodeframe.net.RestfulApi;
import com.niluogege.example.fastcodeframe.utils.Constant;
import com.niluogege.example.fastcodeframe.utils.SPUtil;
import com.niluogege.example.fastcodeframe.utils.StatusBarUtil;
import com.niluogege.example.fastcodeframe.view.explosionfield.ExplosionField;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        StatusBarUtil.setTransparentForImageViewInFragment(this, null);

        screenSize = getScreenSize(this);
        statusBarHeight = getStatusBarHeight(this);

        View btn = findViewById(R.id.btn);
        riv = findViewById(R.id.riv);


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

//        Log.e("DemoActivirty", "imagewidth:" + imagewidth + " imageHeight:" + imageHeight + " screenwidth: " + screenSize[0] + " screenHeight: " + screenSize[1]);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float randomX = getRandomX();
                float randomY = getRandomY();

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
                explosionField.explode(v, 200);
                if (mMediaPlayer != null) {
                    mMediaPlayer.start();
                }

                update();
            }
        });

//        connectionWs();
        connectionWs1();
//        connectionWs2();

    }

    private void update() {
        Object name = SPUtil.get(SPUtil.PRODUCT_PROPERTY, Constant.NAME);
        RestfulApi.getApiService().update(name.toString())
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

//    private void connectionWs2() {
//        try {
//            uri = new URI(address);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        WebSocketClient webSocketClient = new WebSocketClient(uri, new Draft_75()) {
//            @Override
//            public void onOpen(ServerHandshake handshakedata) {
//                Log.e(TAG, "run() returned: " + "连接到服务器");
//            }
//
//            @Override
//            public void onMessage(String message) {
//                Log.e(TAG, "run() returned: " + message);
//            }
//
//            @Override
//            public void onClose(int code, String reason, boolean remote) {
//                Log.e(TAG, "onClose() returned: " + reason);
//            }
//
//            @Override
//            public void onError(Exception ex) {
//                Log.e(TAG, "onError() returned: " + ex);
//            }
//        };
//
//        webSocketClient.connect();
//    }


    private String address = "ws://118.31.223.114:8111";
    //    private String address = "ws://ws.t.xianghuanji.com:80/echo";
    private URI uri;
    private static final String TAG = "JavaWebSocket";
    private WebSocketClient mWebSocketClient;

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
                    Log.e(TAG, "onOpen: ");
                }

                @Override
                public void onMessage(String s) {
                    Log.e(TAG, "onMessage: " + s);
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

    private void connectionWs() {
        AsyncHttpClient.getDefaultInstance().websocket(
                "ws://192.168.1.135",// webSocket地址
                "8080/echo",// 端口
                new AsyncHttpClient.WebSocketConnectCallback() {
                    @Override
                    public void onCompleted(Exception ex, WebSocket webSocket) {
                        if (ex != null) {
                            ex.printStackTrace();
                            return;
                        }
                        webSocket.send("a string");// 发送消息的方法
                        webSocket.send(new byte[10]);
                        webSocket.setStringCallback(new WebSocket.StringCallback() {
                            public void onStringAvailable(String s) {
                                Log.e("connectionWs", "onStringAvailable: " + s);
                            }
                        });
                        webSocket.setDataCallback(new DataCallback() {
                            public void onDataAvailable(DataEmitter emitter, ByteBufferList byteBufferList) {
                                Log.e("connectionWs", "I got some bytes!");
                                // note that this data has been read
                                byteBufferList.recycle();
                            }
                        });
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
    }
}
