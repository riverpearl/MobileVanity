package com.vanity.mobilevanity.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.cosmetic.CosmeticDetailActivity;
import com.vanity.mobilevanity.data.CommentItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class AlertCommentViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_user)
    TextView userView;


    public interface OnAlertCommentItemClickListener{
        public void onAlertCommentItemClick(View view, CommentItem item, int position);
    }

    OnAlertCommentItemClickListener listener;
    public void setOnAlertCommentItemClickListener(OnAlertCommentItemClickListener listener) {
        this.listener = listener;
    }

    public AlertCommentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onAlertCommentItemClick(view, item, getAdapterPosition());
                }
            }
        });
    }

    CommentItem item;

    public void setComment(CommentItem item) {
        this.item = item;
        userView.setText(item.getUser());
    }


}
