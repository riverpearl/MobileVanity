package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.Notify;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-09-01.
 */
public class AlertListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_alert)
    ImageView alertView;

    @BindView(R.id.text_message)
    TextView messageView;

    Notify data;

    public AlertListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onAlertItemClick(view, data, getAdapterPosition());
            }
        });
    }

    public void setData(Notify data) {
        this.data = data;

        int iconResId = 0;

        if (data.getType().equals("like"))
            iconResId = R.drawable.icon_circel_like;
        else if (data.getType().equals("useby"))
            iconResId = R.drawable.icon_circel_dday;
        else iconResId = R.drawable.icon_circel_comment;

        Glide.with(alertView.getContext()).load(iconResId).into(alertView);
        messageView.setText(data.getMessage());
    }

    public interface OnAlertItemClickListener {
        public void onAlertItemClick(View view, Notify data, int position);
    }

    OnAlertItemClickListener mListener;
    public void setOnAlertItemClickListener(OnAlertItemClickListener listener) {
        mListener = listener;
    }
}
