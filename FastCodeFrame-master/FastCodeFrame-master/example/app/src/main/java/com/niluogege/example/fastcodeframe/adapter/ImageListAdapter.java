package com.niluogege.example.fastcodeframe.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.niluogege.example.fastcodeframe.R;
import com.niluogege.example.fastcodeframe.utils.Constant;
import com.niluogege.example.fastcodeframe.utils.SPUtil;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.List;

/**
 * Created by niluogege on 2018/10/11.
 */

public class ImageListAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private final DialogPlus dialogPlus;
    private int imageVideo = 0;


    public ImageListAdapter(DialogPlus dialogPlus, int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
        this.dialogPlus=dialogPlus;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        Object o = SPUtil.get(SPUtil.PRODUCT_PROPERTY, Constant.IMAGE_SELECT);
        if (o != null) {
            imageVideo = (int) o;
        }


        helper.setVisible(R.id.iv_duigou, imageVideo == helper.getAdapterPosition());

        helper.setImageResource(R.id.niv,item);

        helper.setOnClickListener(R.id.ll_root, v -> {
            SPUtil.save(SPUtil.PRODUCT_PROPERTY, Constant.IMAGE_SELECT, helper.getAdapterPosition());
            dialogPlus.dismiss();
        });
    }
}
