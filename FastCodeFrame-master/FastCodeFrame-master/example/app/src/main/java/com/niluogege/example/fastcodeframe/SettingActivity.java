package com.niluogege.example.fastcodeframe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.niluogege.example.fastcodeframe.adapter.ImageListAdapter;
import com.niluogege.example.fastcodeframe.adapter.VideoListAdapter;
import com.niluogege.example.fastcodeframe.bean.ImageInfo;
import com.niluogege.example.fastcodeframe.bean.VideoInfo;
import com.niluogege.example.fastcodeframe.utils.Constant;
import com.niluogege.example.fastcodeframe.utils.DialogUtil;
import com.niluogege.example.fastcodeframe.utils.SPUtil;
import com.niluogege.example.fastcodeframe.utils.StatusBarUtil;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by niluogege on 2018/10/11.
 */

public class SettingActivity extends AppCompatActivity {

    private List<VideoInfo> videoInfos = new ArrayList<>();
    private List<ImageInfo> imageInfos = new ArrayList<>();
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        StatusBarUtil.setTransparentForImageViewInFragment(this, null);
        bind = ButterKnife.bind(this);


        videoInfos = new ArrayList<>(MAppAplication.getInstance().voideMap.values());
        imageInfos = new ArrayList<>(MAppAplication.getInstance().imageMap.values());

        SPUtil.save(SPUtil.PRODUCT_PROPERTY, Constant.NAME, "用户：" + UUID.randomUUID());
    }

    @OnClick(R.id.btn_video)
    public void videoSetting() {
        DialogPlus dialogPlus = DialogUtil.createCommonDialog(this, R.layout.dialog_video_list, null);


        RecyclerView rv = (RecyclerView) dialogPlus.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
        VideoListAdapter adapter = new VideoListAdapter(dialogPlus, R.layout.item_video_list, videoInfos);
        rv.setAdapter(adapter);
        dialogPlus.show();
    }

    @OnClick(R.id.btn_image)
    public void imageSetting() {
        DialogPlus dialogPlus = DialogUtil.createCommonDialog(this, R.layout.dialog_video_list, null);
        RecyclerView rv = (RecyclerView) dialogPlus.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
        ImageListAdapter adapter = new ImageListAdapter(dialogPlus, R.layout.item_image_list, imageInfos);
        rv.setAdapter(adapter);
        dialogPlus.show();
    }

    @OnClick(R.id.btn_name)
    public void name() {
        DialogPlus dialogPlus = DialogUtil.createCommonDialog(this, R.layout.dialog_name, null);
        TextView tv_name = (TextView) dialogPlus.findViewById(R.id.tv_name);
        EditText rv = (EditText) dialogPlus.findViewById(R.id.et);
        Button btn_ok = (Button) dialogPlus.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = rv.getText().toString();
                if (name == null || TextUtils.equals("", name)) {
                } else {
                    SPUtil.save(SPUtil.PRODUCT_PROPERTY, Constant.NAME, name);
                }
                dialogPlus.dismiss();
            }
        });

        Object name = SPUtil.get(SPUtil.PRODUCT_PROPERTY, Constant.NAME);
        if (name != null && name instanceof String) {
            tv_name.setText(name.toString());
        }

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
