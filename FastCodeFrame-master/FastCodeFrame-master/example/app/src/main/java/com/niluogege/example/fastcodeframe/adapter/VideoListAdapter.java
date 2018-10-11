package com.niluogege.example.fastcodeframe.adapter;

import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.niluogege.example.commonsdk.base.BaseApplication;
import com.niluogege.example.fastcodeframe.DemoActivirty;
import com.niluogege.example.fastcodeframe.R;
import com.niluogege.example.fastcodeframe.bean.VideoInfo;

import java.util.List;

/**
 * Created by niluogege on 2018/10/11.
 */

public class VideoListAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {
    private MediaPlayer mMediaPlayer;

    public VideoListAdapter(int layoutResId, @Nullable List<VideoInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoInfo item) {
        helper.setText(R.id.tv_people, item.getPeopleName());
        helper.setOnClickListener(R.id.iv_laba, new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                }
                mMediaPlayer = MediaPlayer.create(BaseApplication.getApplication(), item.getFileName());
                mMediaPlayer.start();
            }
        });
    }


}
