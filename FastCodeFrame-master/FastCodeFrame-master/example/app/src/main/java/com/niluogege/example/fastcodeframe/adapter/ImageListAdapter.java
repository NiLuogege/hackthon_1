package com.niluogege.example.fastcodeframe.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.niluogege.example.fastcodeframe.R;
import com.niluogege.example.fastcodeframe.utils.Constant;
import com.niluogege.example.fastcodeframe.utils.SPUtil;

import java.util.List;

/**
 * Created by niluogege on 2018/10/11.
 */

public class ImageListAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private int imageVideo = 0;


    public ImageListAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
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
            notifyDataSetChanged();
        });
    }
}
