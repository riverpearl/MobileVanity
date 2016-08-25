package com.vanity.mobilevanity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.view.BeautyTipCommentPopUpViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class BeautyTipCommentPopUpAdapter extends RecyclerView.Adapter<BeautyTipCommentPopUpViewHolder> {
    List<Comment> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(Comment c) {
        items.add(c);
        notifyDataSetChanged();
    }

    public void addAll(List<Comment> items) {
        items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public BeautyTipCommentPopUpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_beauty_tip_comment_popup_view, parent, false);
        BeautyTipCommentPopUpViewHolder holder = new BeautyTipCommentPopUpViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BeautyTipCommentPopUpViewHolder holder, int position) {
        holder.setComment(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
