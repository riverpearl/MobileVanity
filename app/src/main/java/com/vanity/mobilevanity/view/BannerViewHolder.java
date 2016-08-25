package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BannerData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class BannerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_banner)
    ImageView bannerView;

    private BannerData data;

    public BannerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onBannerItemClick(view, data, getAdapterPosition());
            }
        });
    }

    public void setData(BannerData data) {
        this.data = data;
        //bannerView.setImageDrawable(data.getImage());
    }

    public interface OnBannerItemClickListener {
        public void onBannerItemClick(View view, BannerData data, int position);
    }

    OnBannerItemClickListener mListener;
    public void setOnBannerItemClickListener(OnBannerItemClickListener listener) {
        mListener = listener;
    }
}
