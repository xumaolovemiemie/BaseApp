package com.wisdom.rxjava.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;

import com.wisdom.rxjava.R;
import com.wisdom.rxjava.utils.StatusBarUtil;
import com.wisdom.rxjava.widget.loading.WisdomLoading;
import com.wisdom.rxjava.widget.swipeback.SwipeBackActivityHelper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by wisdom on 17/3/31.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements BaseSubscriberContext {

    protected static String TAG_LOG = null;

    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    protected BaseActivity mActivity = null;
    protected WisdomLoading wisdomLoading;
    private SwipeBackActivityHelper mHelper;
    private CompositeDisposable mDisposable;

    protected T b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        Uri uri = getIntent().getData();
        if (extras != null) {
            getBundleExtras(extras);
        } else if (uri != null) {
            getIntentUrl(uri);
        }
        mActivity = this;
        TAG_LOG = this.getClass().getSimpleName();
        BaseAppManager.getInstance().addActivity(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        if (getContentViewLayoutID() != 0) {
            b = DataBindingUtil.setContentView(this, getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        initViews();
        initData();
        setListener();
    }

    @Override
    public void addDisposable(Disposable d) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(d);
    }

    public void getIntentUrl(Uri uri) {

    }

    protected void setListener() {

    }

    protected void initData() {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null) return mHelper.findViewById(id);
        return v;
    }

    public void setSwipeBackEnable(boolean enable) {
        mHelper.getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimaryDark), 0);
    }


    protected void getBundleExtras(Bundle extras) {

    }

    protected abstract int getContentViewLayoutID();

    protected void initViews() {

    }

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void showIndeterminateProgressDialog(boolean cancelable) {
        if (wisdomLoading == null) {
            wisdomLoading = WisdomLoading.createDialog(this)
                .setCanCancel(cancelable)
                .start();
        }
    }

    public void setDialogContent(String content) {
        if (wisdomLoading != null) {
            wisdomLoading.setContent(content);
        }
    }

    public void hideIndeterminateProgressDialog() {
        try {
            if (wisdomLoading != null) {
                wisdomLoading.dismiss();
                wisdomLoading = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        if (mDisposable != null) {
            mDisposable.clear();
            mDisposable = null;
        }
        super.onStop();
        hideIndeterminateProgressDialog();
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
