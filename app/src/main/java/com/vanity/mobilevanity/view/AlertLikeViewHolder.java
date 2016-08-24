package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.LikeItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class AlertLikeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_user)
    TextView userView;


    public interface OnAlertLikeItemClickListener {
        public void onAlertLikeItemClick(View view, LikeItem item, int position);
    }

    OnAlertLikeItemClickListener listener;

    public void setOnAlertLikeItemClickListener(OnAlertLikeItemClickListener listener) {
        this.listener = listener;
    }

    public AlertLikeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onAlertLikeItemClick(view, item, getAdapterPosition());
                }
            }
        });
    }

    LikeItem item;

    public void setLike(LikeItem item) {
        this.item = item;
        userView.setText(item.getUser());
    }
}
