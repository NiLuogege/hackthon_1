package com.niluogege.example.fastcodeframe;

import com.niluogege.example.commonsdk.base.BaseApplication;
import com.niluogege.example.fastcodeframe.bean.ImageInfo;
import com.niluogege.example.fastcodeframe.bean.VideoInfo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by niluogege on 2018/10/11.
 */

public class MAppAplication extends BaseApplication {
    private static MAppAplication instance = null;

    public Map<Integer, VideoInfo> voideMap;
    public Map<Integer, ImageInfo> imageMap;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        voideMap = new LinkedHashMap<>();
        voideMap.put(0, new VideoInfo("默认", R.raw.a));
        voideMap.put(1, new VideoInfo("leo大大", R.raw.leo_1));
        voideMap.put(2, new VideoInfo("林生-1", R.raw.ls_1));
        voideMap.put(3, new VideoInfo("林生-2", R.raw.ls_2));
        voideMap.put(3, new VideoInfo("NiLuogege", R.raw.lc));

        imageMap = new LinkedHashMap<>();
        imageMap.put(0, new ImageInfo("臭臭", R.mipmap.tx_1));
        imageMap.put(1, new ImageInfo("产品小狗狗", R.mipmap.tx_2));
        imageMap.put(2, new ImageInfo("乐园的龙猫", R.mipmap.tx_3));
        imageMap.put(3, new ImageInfo("LY_1", R.mipmap.tx_4));
        imageMap.put(4, new ImageInfo("LY_2", R.mipmap.tx_5));
    }

    public static MAppAplication getInstance() {
        return instance;
    }
}
