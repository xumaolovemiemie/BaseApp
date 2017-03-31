package com.wisdom.rxjava.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wisdom.rxjava.R;
import com.wisdom.rxjava.base.BaseActivity;
import com.wisdom.rxjava.databinding.ItemActivityBinding;
import com.wisdom.rxjava.entity.PortalBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisdom on 17/3/31.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private BaseActivity activity;

    private List<PortalBean.ActivitysBean> data;

    public ActivityAdapter(BaseActivity activity) {
        this.activity = activity;
        data = new ArrayList<>();
    }

    public void update(List<PortalBean.ActivitysBean> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityAdapter.ViewHolder holder, int position) {
        Glide.with(activity).load(data.get(position).getAct_img()).crossFade().into(holder.b.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemActivityBinding b;

        ViewHolder(View itemView) {
            super(itemView);
            b = DataBindingUtil.bind(itemView);
        }
    }
}
