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
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class AlertCommentViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_user)
    TextView userView;

    private Comment item;

    public AlertCommentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onAlertCommentClick(view, item, getAdapterPosition());
                }
            }
        });
    }

    public void setComment(Comment item) {
        this.item = item;
        userView.setText(item.getWriter().getUserNickname());
    }

    public interface OnAlertCommentClickListener {
        public void onAlertCommentClick(View view, Comment item, int position);
    }

    OnAlertCommentClickListener listener;

    public void setOnAlertCommentClickListener(OnAlertCommentClickListener listener) {
        this.listener = listener;
    }


}
