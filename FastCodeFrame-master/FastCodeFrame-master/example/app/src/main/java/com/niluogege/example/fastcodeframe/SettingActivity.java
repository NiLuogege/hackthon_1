package com.niluogege.example.fastcodeframe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.niluogege.example.fastcodeframe.adapter.ImageListAdapter;
import com.niluogege.example.fastcodeframe.adapter.VideoListAdapter;
import com.niluogege.example.fastcodeframe.bean.VideoInfo;
import com.niluogege.example.fastcodeframe.utils.DialogUtil;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by niluogege on 2018/10/11.
 */

public class SettingActivity extends AppCompatActivity {

    private List<VideoInfo> videoInfos = new ArrayList<>();
    private List<Integer> imageInfos = new ArrayList<>();
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        bind = ButterKnife.bind(this);


        videoInfos = new ArrayList<>(MAppAplication.getInstance().voideMap.values());
        imageInfos = new ArrayList<>(MAppAplication.getInstance().imageMap.values());
    }

    @OnClick(R.id.btn_video)
    public void videoSetting() {
        DialogPlus dialogPlus = DialogUtil.createCommonDialog(this, R.layout.dialog_video_list, null);


        RecyclerView rv = (RecyclerView) dialogPlus.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
        VideoListAdapter adapter = new VideoListAdapter(R.layout.item_video_list, videoInfos);
        rv.setAdapter(adapter);
        dialogPlus.show();
    }

    @OnClick(R.id.btn_image)
    public void imageSetting() {
        DialogPlus dialogPlus = DialogUtil.createCommonDialog(this, R.layout.dialog_video_list, null);
        RecyclerView rv = (RecyclerView) dialogPlus.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
        ImageListAdapter adapter = new ImageListAdapter(R.layout.item_image_list, imageInfos);
        rv.setAdapter(adapter);
        dialogPlus.show();
    }

    @OnClick(R.id.btn_begin)
    public void begin() {
        startActivity(new Intent(this, DemoActivirty.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) bind.unbind();
    }
}
