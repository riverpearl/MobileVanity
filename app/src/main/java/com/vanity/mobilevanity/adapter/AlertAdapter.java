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
import com.vanity.mobilevanity.data.CommentItem;
import com.vanity.mobilevanity.data.LikeItem;
import com.vanity.mobilevanity.data.UseByItem;
import com.vanity.mobilevanity.view.AlertCommentViewHolder;
import com.vanity.mobilevanity.view.AlertLikeViewHolder;
import com.vanity.mobilevanity.view.AlertUseByViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class AlertAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AlertCommentViewHolder.OnAlertCommentItemClickListener, AlertLikeViewHolder.OnAlertLikeItemClickListener, AlertUseByViewHolder.OnAlertUseByItemClickListener {
    List<AlertItem> items = new ArrayList<>();

    int checkedPosition = -1;

    public void add(AlertItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    private static final int VIEW_TYPE_COMMENT = 100;
    private static final int VIEW_TYPE_LIKE = 200;
    private static final int VIEW_TYPE_USEBY = 300;

    @Override
    public int getItemViewType(int position) {
        AlertItem item = items.get(position);

        if (item instanceof CommentItem) {
            return VIEW_TYPE_COMMENT;
        } else if (item instanceof LikeItem) {
            return VIEW_TYPE_LIKE;
        } else if (item instanceof UseByItem) {
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
                    holder.setOnAlertCommentItemClickListener(this);
                }
                return new AlertCommentViewHolder(commentView);
            }
            case VIEW_TYPE_LIKE: {
                if (likeView == null) {
                    likeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_alert_like, parent, false);
                }
                return new AlertLikeViewHolder(likeView);
            }
            case VIEW_TYPE_USEBY: {
                if (usebyView == null) {
                    usebyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_alert_useby, parent, false);
                }
                return new AlertUseByViewHolder(usebyView);
            }
        }
        throw new IllegalArgumentException("invalid viewType");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AlertItem item = items.get(position);

        if (item instanceof CommentItem) {
            AlertCommentViewHolder cvh = (AlertCommentViewHolder) holder;
            ((AlertCommentViewHolder) holder).setComment((CommentItem) items.get(position));
            return;
        }
        if (item instanceof LikeItem) {
            AlertLikeViewHolder lvh = (AlertLikeViewHolder) holder;
            ((AlertLikeViewHolder) holder).setLike((LikeItem) items.get(position));
            return;
        }
        if (item instanceof UseByItem) {
            AlertUseByViewHolder uvh = (AlertUseByViewHolder) holder;
            ((AlertUseByViewHolder) holder).setUseBy((UseByItem) items.get(position));
            return;
        }

        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public interface OnAdapterAlertCommentItemClickListener {
        public void onAdapterAlertCommentItemClick(View view, CommentItem item, int position);
    }

    AlertCommentViewHolder.OnAlertCommentItemClickListener comment_listener;

    public void setOnAdapterAlertCommentItemClickListener(AlertCommentViewHolder.OnAlertCommentItemClickListener listener) {
        this.comment_listener = listener;
    }

    @Override
    public void onAlertCommentItemClick(View view, CommentItem item, int position) {
        if (comment_listener != null) {
            comment_listener.onAlertCommentItemClick(view, item, position);
        }
    }

    public interface OnAdapterAlertLikeItemClickListener {
        public void onAdapterAlertLikeItemClick(View view, LikeItem item, int position);
    }

    AlertLikeViewHolder.OnAlertLikeItemClickListener like_listener;

    public void setOnAdapterAlertLikeItemClickListener(AlertLikeViewHolder.OnAlertLikeItemClickListener listener) {
        this.like_listener = listener;
    }

    @Override
    public void onAlertLikeItemClick(View view, LikeItem item, int position) {
        if (like_listener != null) {
            like_listener.onAlertLikeItemClick(view, item, position);
        }
    }

    public interface OnAdapterAlertUseByItemClickListener {
        public void onAdapterAlertUseByItemClick(View view, UseByItem item, int position);
    }

    AlertUseByViewHolder.OnAlertUseByItemClickListener useby_listener;

    public void setOnAdapterAlertUseByItemClickListener(AlertUseByViewHolder.OnAlertUseByItemClickListener listener) {
        this.useby_listener = listener;
    }

    @Override
    public void onAlertUseByItemClick(View view, UseByItem item, int position) {
        if (useby_listener != null) {
            useby_listener.onAlertUseByItemClick(view, item, position);
        }
    }
}
