package com.vanity.mobilevanity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.view.LikeViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by riverpearl on 2016-08-24.
 */
public class LikeAdapter extends RecyclerView.Adapter<LikeViewHolder>
    implements LikeViewHolder.OnLikeItemClickListener, LikeViewHolder.OnLikeItemLongClickListener {
    private List<BeautyTip> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(BeautyTip item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<BeautyTip> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public LikeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_like, parent, false);
        LikeViewHolder holder = new LikeViewHolder(view);
        holder.setOnLikeItemClickListener(this);
        holder.setOnLikeItemLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(LikeViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnAdapterItemClickListener {
        public void onAdapterItemClick(View view, BeautyTip data, int position);
    }

    OnAdapterItemClickListener mListener;
    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onLikeItemClick(View view, BeautyTip data, int position) {
        if (mListener != null)
            mListener.onAdapterItemClick(view, data, position);
    }

    public interface OnAdapterItemLongClickListener {
        public void onAdapterLongItemClick(View view, BeautyTip data, int position);
    }

    OnAdapterItemLongClickListener longClickListener;
    public void setOnAdapterItemLongClickListener(OnAdapterItemLongClickListener listener) {
        longClickListener = listener;
    }

    @Override
    public void onLikeItemLongClick(View view, BeautyTip data, int position) {
        if (longClickListener != null)
            longClickListener.onAdapterLongItemClick(view, data, position);
    }
}
