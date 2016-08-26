package com.vanity.mobilevanity.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.data.AlertItem;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.data.User;
import com.vanity.mobilevanity.view.AlertCommentViewHolder;
import com.vanity.mobilevanity.view.AlertLikeViewHolder;
import com.vanity.mobilevanity.view.AlertUseByViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class AlertAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AlertCommentViewHolder.OnAlertCommentClickListener, AlertLikeViewHolder.OnAlertLikeClickListener, AlertUseByViewHolder.OnAlertUseByItemClickListener {
    List<AlertItem> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(AlertItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<AlertItem> items) {
        items.addAll(items);
        notifyDataSetChanged();
    }

    private static final int VIEW_TYPE_COMMENT = 100;
    private static final int VIEW_TYPE_LIKE = 200;
    private static final int VIEW_TYPE_USEBY = 300;

    @Override
    public int getItemViewType(int position) {
        AlertItem item = items.get(position);

        if (item instanceof Comment) {
            return VIEW_TYPE_COMMENT;
        } else if (item instanceof User) {
            return VIEW_TYPE_LIKE;
        } else if (item instanceof CosmeticItem) {
            return VIEW_TYPE_USEBY;
        }

        return super.getItemViewType(position);
    }

    View commentView = null;
    View likeView = null;
    View usebyView = null;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_COMMENT: {
                if (commentView == null) {
                    commentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_alert_comment, parent, false);
                    AlertCommentViewHolder holder = new AlertCommentViewHolder(commentView);
                    holder.setOnAlertCommentClickListener(this);
                    return holder;
                }
            }
            case VIEW_TYPE_LIKE: {
                if (likeView == null) {
                    likeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_alert_like, parent, false);
                    AlertLikeViewHolder holder = new AlertLikeViewHolder(likeView);
                    holder.setOnAlertLikeClickListener(this);
                    return holder;
                }
            }
            case VIEW_TYPE_USEBY: {
                if (usebyView == null) {
                    usebyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_alert_useby, parent, false);
                    AlertUseByViewHolder holder = new AlertUseByViewHolder(usebyView);
                    // 고쳐야 함(UseBy부분)
                    holder.setOnAlertUseByItemClickListener(this);
                    return holder;
                }
            }
        }
        throw new IllegalArgumentException("invalid viewType");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AlertItem item = items.get(position);

        if (item instanceof Comment) {
            AlertCommentViewHolder cvh = (AlertCommentViewHolder) holder;
            ((AlertCommentViewHolder) holder).setComment((Comment) items.get(position));
            return;
        }
        if (item instanceof User) {
            AlertLikeViewHolder lvh = (AlertLikeViewHolder) holder;
            ((AlertLikeViewHolder) holder).setLike((User) items.get(position));
            return;
        }
        if (item instanceof CosmeticItem) {
            AlertUseByViewHolder uvh = (AlertUseByViewHolder) holder;
            ((AlertUseByViewHolder) holder).setUseBy((CosmeticItem) items.get(position));
            return;
        }

        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAlertCommentClick(View view, Comment item, int position) {
        if (listener != null) {
            listener.onAdapteItemClick(view, item, position);
        }
    }

    @Override
    public void onAlertLikeClick(View view, User user, int position) {
        if (listener != null) {
            listener.onAdapteItemClick(view, user, position);
        }
    }

    @Override
    public void onAlertUseByItemClick(View view, CosmeticItem item, int position) {
        if (listener != null) {
            listener.onAdapteItemClick(view, item, position);

        }
    }

    public interface OnAdapterItemClickListener {
        public void onAdapteItemClick(View view, AlertItem item, int position);
    }

    OnAdapterItemClickListener listener;

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        this.listener = listener;
    }

}
