package com.niluogege.example.fastcodeframe.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niluogege.example.fastcodeframe.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * 类名称：DialogUtil
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/8/14
 * 描述：弹框相关工具类
 */
public class DialogUtil {

    public static DialogPlus createCustomDialog(Context context, int layoutId, int contentBgColor,
                                                int overlayBgColor, OnClickListener onClickListener,boolean cancelable) {
        View customView = LayoutInflater.from(context.getApplicationContext()).inflate(layoutId, null);
        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(customView))
                .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentBackgroundResource(contentBgColor)
                .setOverlayBackgroundResource(overlayBgColor)
                .setGravity(Gravity.CENTER)
                .setOnClickListener(onClickListener)
                .setCancelable(cancelable)
                .create();
        return dialogPlus;
    }

    public static DialogPlus createCommonDialog(Context context, int layoutId,  OnClickListener onClickListener) {
        return createCustomDialog(context,layoutId, android.R.color.white, R.color.mask_fg_color,onClickListener,true);
    }

    public static DialogPlus createCommonDialog(Context context, int layoutId,  OnClickListener onClickListener,boolean cancelable) {
        return createCustomDialog(context,layoutId, android.R.color.white, R.color.mask_fg_color,onClickListener,cancelable);
    }
}
