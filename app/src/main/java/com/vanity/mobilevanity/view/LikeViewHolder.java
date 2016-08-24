package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.LikeData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by riverpearl on 2016-08-24.
 */
public class LikeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_beauty_tip_title)
    TextView titleView;

    private LikeData data;

    public LikeViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onLikeItemClick(view, data, getAdapterPosition());
            }
        });
    }

    public void setData(LikeData data) {
        this.data = data;
        titleView.setText(data.getTitle());
    }

    public interface OnLikeItemClickListener {
        public void onLikeItemClick(View view, LikeData data, int position);
    }

    OnLikeItemClickListener mListener;
    public void setOnLikeItemClickListener(OnLikeItemClickListener listener) {
        mListener = listener;
    }
}
