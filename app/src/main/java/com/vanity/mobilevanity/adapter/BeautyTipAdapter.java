package com.vanity.mobilevanity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.view.BeautyTipItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class BeautyTipAdapter extends RecyclerView.Adapter<BeautyTipItemViewHolder>
        implements BeautyTipItemViewHolder.OnBeautyTipItemClickListener, BeautyTipItemViewHolder.OnCommentDialogClickListener {
    List<BeautyTip> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(BeautyTip b) {
        items.add(b);
        notifyDataSetChanged();
    }

    public void addAll(List<BeautyTip> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public BeautyTipItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_beauty_tip_item, parent, false);
        BeautyTipItemViewHolder holder = new BeautyTipItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BeautyTipItemViewHolder holder, int position) {
        holder.setBeautyTipItem(items.get(position));
        holder.setOnBeautyTipItemClickListener(this);
        holder.setOnCommentDialogClickListner(this);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnAdapterCommentClickListener {
        public void onAdapterCommentClick(View view, Comment comment);
    }

    OnAdapterCommentClickListener dialogListener;

    public void setOnAdapterCommentClickListener(OnAdapterCommentClickListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public void onCommentDialogClick(View view, Comment comment) {
        if(dialogListener != null) {
            dialogListener.onAdapterCommentClick(view, comment);
        }
    }

    public interface OnAdapterItemClickListener {
        public void onAdapterItemClick(View view, BeautyTip beautyTip, int position);
    }

    OnAdapterItemClickListener listener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBeautyTipItemClick(View view, BeautyTip beautyTip, int position) {
        if (listener != null) {
            listener.onAdapterItemClick(view, beautyTip, position);
        }
    }
}
