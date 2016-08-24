package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.UseByItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class AlertUseByViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_cosmetic)
    TextView cosmeticView;

    @BindView(R.id.text_useby)
    TextView usebyView;


    public AlertUseByViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onAlertUseByItemClick(view, item, getAdapterPosition());
                }
            }
        });
    }

    UseByItem item;

    public void setUseBy(UseByItem item) {
        this.item = item;
        cosmeticView.setText(item.getCosmetic());
        usebyView.setText("" + item.getUseby());
    }

    public interface OnAlertUseByItemClickListener {
        public void onAlertUseByItemClick(View view, UseByItem item, int position);
    }

    OnAlertUseByItemClickListener listener;

    public void setOnAlertUseByItemClickListener(OnAlertUseByItemClickListener listener) {
        this.listener = listener;
    }
}
