package com.niluogege.example.commonsdk.network;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.niluogege.example.commonsdk.R;
import com.niluogege.example.commonsdk.base.BaseActivity;
import com.niluogege.example.commonsdk.base.mvp.IView;
import com.niluogege.example.commonsdk.utils.DialogUtils;
import com.orhanobut.dialogplus.DialogPlus;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by niluogege on 2018/8/24.
 */

public class ProgressHelper {


    private static DialogPlus progress;

    public static DialogPlus createProgress(BaseActivity activity) {

        DialogPlus plus = DialogUtils.createCustomDialog(activity,
                R.layout.dialog_loading_layout,
                android.R.color.transparent,
                android.R.color.transparent,
                false, null, null);

        View image = plus.findViewById(R.id.img);
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.loading_animation);
        image.startAnimation(animation);

        return plus;
    }


    public static <T> ObservableTransformer<T, T> applyProgressBar(BaseActivity activity) {
        ObservableTransformer transformer = new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (progress == null)
                            progress = createProgress(activity);
                        if (!progress.isShowing())
                            progress.show();
                    }
                }).doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (progress != null && progress.isShowing())
                            progress.dismiss();
                    }
                });
            }
        };

        return transformer;
    }

    public static <T> ObservableTransformer<T, T> applyProgressBar(IView view) {
        if (view instanceof BaseActivity) {
            return applyProgressBar((BaseActivity) view);
        }
        return null;
    }
}
