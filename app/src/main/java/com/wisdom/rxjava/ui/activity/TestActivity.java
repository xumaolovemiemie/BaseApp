package com.wisdom.rxjava.ui.activity;

import com.bumptech.glide.Glide;
import com.wisdom.rxjava.R;
import com.wisdom.rxjava.base.BaseActivity;
import com.wisdom.rxjava.databinding.ActivityTestBinding;
import com.wisdom.rxjava.entity.ProductBean;
import com.wisdom.rxjava.module.ActivityModule;
import com.wisdom.rxjava.service.CustomSubscriber;

/**
 * Created by wisdom on 17/4/1.
 */

public class TestActivity extends BaseActivity<ActivityTestBinding> {

    @Override
    protected void initData() {
        ActivityModule.getInstance()
            .getProduct(23111)
            .subscribe(new CustomSubscriber<ProductBean>(mActivity) {

                @Override
                public void onNext(ProductBean productBean) {
                    Glide.with(mActivity).load(productBean.getDetail_content().getHead_img()).crossFade().into(b.image);
                }
            });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_test;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
