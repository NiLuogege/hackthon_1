package com.niluogege.example.fastcodeframe;

import com.niluogege.example.commonsdk.base.BaseApplication;
import com.niluogege.example.fastcodeframe.bean.VideoInfo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by niluogege on 2018/10/11.
 */

public class MAppAplication extends BaseApplication {
    private static MAppAplication instance =null;

    public Map<Integer, VideoInfo> voideMap;
    public Map<Integer, Integer> imageMap;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

        voideMap = new LinkedHashMap<>();
        voideMap.put(0, new VideoInfo("默认", R.raw.a));
        voideMap.put(1, new VideoInfo("NiLuogege", R.raw.lc));

        imageMap = new LinkedHashMap<>();
        imageMap.put(0, R.mipmap.tx_1);
        imageMap.put(1, R.mipmap.tx_2);
        imageMap.put(2, R.mipmap.tx_3);
    }

    public static MAppAplication getInstance() {
        return instance;
    }
}
