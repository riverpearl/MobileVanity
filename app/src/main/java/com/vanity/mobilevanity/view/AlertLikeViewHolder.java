package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class AlertLikeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_user)
    TextView userView;

    private User item;

    public AlertLikeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onAlertLikeClick(view, item, getAdapterPosition());
                }
            }
        });
    }

    public void setLike(User item) {
        this.item = item;
        userView.setText(item.getUserNickName());
    }

    public interface OnAlertLikeClickListener {
        public void onAlertLikeClick(View view, User user, int position);
    }

    OnAlertLikeClickListener listener;

    public void setOnAlertLikeClickListener(OnAlertLikeClickListener listener) {
        this.listener = listener;
    }

}
