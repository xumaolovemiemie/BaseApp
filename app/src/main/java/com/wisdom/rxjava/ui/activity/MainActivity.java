package com.wisdom.rxjava.ui.activity;

import android.support.v7.widget.LinearLayoutManager;

import com.wisdom.rxjava.R;
import com.wisdom.rxjava.adapter.ActivityAdapter;
import com.wisdom.rxjava.base.BaseActivity;
import com.wisdom.rxjava.databinding.ActivityMainBinding;
import com.wisdom.rxjava.entity.PortalBean;
import com.wisdom.rxjava.module.ActivityModule;
import com.wisdom.rxjava.service.CustomSubscriber;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private ActivityAdapter adapter;

    @Override
    protected void initViews() {
        b.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new ActivityAdapter(mActivity);
        b.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        addSubscription(ActivityModule.getInstance()
            .getPortalBean("categorys,posters")
            .subscribe(new CustomSubscriber<PortalBean>() {
                @Override
                public void onNext(PortalBean portalBean) {
                    if (portalBean != null && portalBean.getActivitys() != null) {
                        adapter.update(portalBean.getActivitys());
                    }
                    hideIndeterminateProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    hideIndeterminateProgressDialog();
                }
            }));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }
}
