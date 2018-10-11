package com.niluogege.example.fastcodeframe.adapter;

import android.media.MediaPlayer;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.niluogege.example.commonsdk.base.BaseApplication;
import com.niluogege.example.fastcodeframe.R;
import com.niluogege.example.fastcodeframe.bean.VideoInfo;
import com.niluogege.example.fastcodeframe.utils.Constant;
import com.niluogege.example.fastcodeframe.utils.SPUtil;

import java.util.List;

/**
 * Created by niluogege on 2018/10/11.
 */

public class VideoListAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {
    private MediaPlayer mMediaPlayer;
    private int selectVideo = 0;

    public VideoListAdapter(int layoutResId, @Nullable List<VideoInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoInfo item) {
        Object o = SPUtil.get(SPUtil.PRODUCT_PROPERTY, Constant.VIDEO_SELECT);
        if (o != null) {
            selectVideo = (int) o;
        }

        helper.setVisible(R.id.iv_duigou, selectVideo == helper.getAdapterPosition());
        helper.setText(R.id.tv_people, item.getPeopleName());
        helper.setOnClickListener(R.id.iv_laba, v -> {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
            }
            mMediaPlayer = MediaPlayer.create(BaseApplication.getApplication(), item.getFileName());
            mMediaPlayer.start();
        });

        helper.setOnClickListener(R.id.ll_root, v -> {
            SPUtil.save(SPUtil.PRODUCT_PROPERTY, Constant.VIDEO_SELECT, helper.getAdapterPosition());
            notifyDataSetChanged();
        });
    }


}
