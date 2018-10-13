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
        voideMap.put(4, new VideoInfo("NiLuogege", R.raw.lc));
        voideMap.put(5, new VideoInfo("女性-1", R.raw.nv_1));
        voideMap.put(6, new VideoInfo("女性-2", R.raw.nv_2));
        voideMap.put(7, new VideoInfo("dog", R.raw.dog));
        voideMap.put(8, new VideoInfo("pag", R.raw.pag));
        voideMap.put(9, new VideoInfo("乐园", R.raw.ly));
        voideMap.put(10, new VideoInfo("雅妮", R.raw.yn));
        voideMap.put(11, new VideoInfo("聪美_1", R.raw.cm_1));
        voideMap.put(12, new VideoInfo("聪美_2", R.raw.cm_2));
        voideMap.put(13, new VideoInfo("杨磊", R.raw.ll_1));

        imageMap = new LinkedHashMap<>();
        imageMap.put(0, new ImageInfo("臭臭", R.mipmap.tx_1));
        imageMap.put(1, new ImageInfo("产品小狗狗", R.mipmap.tx_2));
        imageMap.put(2, new ImageInfo("乐园的龙猫", R.mipmap.tx_3));
        imageMap.put(3, new ImageInfo("LY_1", R.mipmap.tx_4));
        imageMap.put(4, new ImageInfo("LY_2", R.mipmap.tx_5));
        imageMap.put(5, new ImageInfo("XQ", R.mipmap.tx_6));
        imageMap.put(6, new ImageInfo("LL", R.mipmap.tx_7));
        imageMap.put(7, new ImageInfo("撅嘴的猫", R.mipmap.tx_8));
    }

    public static MAppAplication getInstance() {
        return instance;
    }
}
