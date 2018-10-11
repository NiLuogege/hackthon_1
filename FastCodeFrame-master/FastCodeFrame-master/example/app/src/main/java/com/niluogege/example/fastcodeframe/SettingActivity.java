package com.niluogege.example.fastcodeframe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.niluogege.example.fastcodeframe.adapter.VideoListAdapter;
import com.niluogege.example.fastcodeframe.bean.VideoInfo;
import com.niluogege.example.fastcodeframe.utils.DialogUtil;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by niluogege on 2018/10/11.
 */

public class SettingActivity extends AppCompatActivity {

    private List<VideoInfo> videoInfos = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        videoInfos = new ArrayList<>();
        videoInfos.add(new VideoInfo("默认", R.raw.a));
        videoInfos.add(new VideoInfo("罗晨", R.raw.lc));
    }

    @OnClick(R.id.btn_video)
    public void videoSetting() {
        DialogPlus dialogPlus = DialogUtil.createCommonDialog(this, R.layout.dialog_video_list, new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {

            }
        });


        RecyclerView rv = (RecyclerView) dialogPlus.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
        VideoListAdapter adapter = new VideoListAdapter(R.layout.item_video_list, videoInfos);
        rv.setAdapter(adapter);
        dialogPlus.show();
    }

    @OnClick(R.id.btn_begin)
    public void begin() {
        startActivity(new Intent(this, DemoActivirty.class));
    }
}
