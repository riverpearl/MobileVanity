package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.MyBeautyTipData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class MyBeautyTipViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_my_beauty_tip_title)
    TextView titleView;

    private MyBeautyTipData data;

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
    }

    public void setData(MyBeautyTipData data) {
        this.data = data;

        titleView.setText(data.getTitle());
    }

    public interface OnMyBeautyTipItemClickListener {
        public void onMyBeautyTipItemClick(View view, MyBeautyTipData data, int position);
    }

    OnMyBeautyTipItemClickListener mListener;
    public void setOnMyBeautyTipItemClickListener(OnMyBeautyTipItemClickListener listener) {
        mListener = listener;
    }
}
