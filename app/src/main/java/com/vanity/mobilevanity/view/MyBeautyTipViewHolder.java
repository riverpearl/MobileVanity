package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BeautyTip;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class MyBeautyTipViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_my_beauty_tip_title)
    TextView titleView;

    private BeautyTip data;

    public MyBeautyTipViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onMyBeautyTipItemClick(view, data, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (longClickListener != null) {
                    longClickListener.onMyBeautyTipItemLongClick(view, data, getAdapterPosition());
                    return true;
                }
                return false;
            }
        });
    }

    public void setData(BeautyTip data) {
        this.data = data;

        titleView.setText(data.getTitle());
    }

    public interface OnMyBeautyTipItemClickListener {
        public void onMyBeautyTipItemClick(View view, BeautyTip data, int position);
    }

    OnMyBeautyTipItemClickListener mListener;
    public void setOnMyBeautyTipItemClickListener(OnMyBeautyTipItemClickListener listener) {
        mListener = listener;
    }

    public interface OnMyBeautyTipItemLongClickListener {
        public void onMyBeautyTipItemLongClick(View view, BeautyTip data, int position);
    }

    OnMyBeautyTipItemLongClickListener longClickListener;
    public void setOnMyBeautyTipItemLongClickListener(OnMyBeautyTipItemLongClickListener listener) {
        longClickListener = listener;
    }
}
