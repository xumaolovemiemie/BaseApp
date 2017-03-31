package com.wisdom.rxjava.widget.loading;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wisdom.rxjava.R;

/**
 * Created by wisdom on 17/3/31.
 */

public class WisdomLoading extends Dialog {


    private View contentView;

    public static WisdomLoading createDialog(Context context) {
        return new WisdomLoading(context, R.style.wisdomDialog);
    }

    private WisdomLoading(Context context, int themeResId) {
        super(context, themeResId);
        setOwnerActivity((Activity) context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = getLayoutInflater().inflate(R.layout.layout_wisdom, null);
        setContentView(contentView);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.1f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public WisdomLoading setCanCancel(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    public WisdomLoading start() {
        show();
        return this;
    }

    public void setContent(String content) {
        ((TextView) contentView.findViewById(R.id.text)).setText(content);
    }

    @Override
    public void show() {
        super.show();
        ((WisdomView) findViewById(R.id.wisdom_view)).start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        ((WisdomView) findViewById(R.id.wisdom_view)).cancel();
    }
}

