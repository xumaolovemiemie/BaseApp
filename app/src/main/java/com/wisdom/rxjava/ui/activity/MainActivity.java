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
        setSwipeBackEnable(false);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new ActivityAdapter(mActivity);
        b.recyclerView.setAdapter(adapter);
        b.btn.setOnClickListener(v -> readyGo(TestActivity.class));
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        setDialogContent("主页数据加载中");
        ActivityModule.getInstance()
            .getPortalBean("categorys,posters")
            .subscribe(new CustomSubscriber<PortalBean>(mActivity) {
                @Override
                public void onNext(PortalBean portalBean) {
                    hideIndeterminateProgressDialog();
                    if (portalBean != null && portalBean.getActivitys() != null) {
                        adapter.update(portalBean.getActivitys());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    hideIndeterminateProgressDialog();
                    e.printStackTrace();
                }
            });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }
}
