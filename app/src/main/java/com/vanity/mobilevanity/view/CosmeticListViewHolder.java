package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.Cosmetic;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class CosmeticListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_cosmetic)
    ImageView cosmeticView;

    @BindView(R.id.image_new)
    ImageView newView;

    @BindView(R.id.text_color_code)
    TextView colorCodeView;

    @BindView(R.id.text_color_name)
    TextView colorNameView;

    @BindView(R.id.text_product_name)
    TextView productNameView;

    @BindView(R.id.text_brand_name)
    TextView brandNameView;

    @BindView(R.id.text_dday)
    TextView ddayView;

    private Cosmetic data;

    public CosmeticListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onCosmeticListItemClick(view, data, getAdapterPosition());
            }
        });
    }

    public void setCosmeticList(Cosmetic item) {
        this.data = item;

        colorCodeView.setText(item.getColorCode());
        colorNameView.setText(item.getColorName());
        productNameView.setText(item.getProduct().getName());
        brandNameView.setText(item.getProduct().getBrand().getName());
        ddayView.setText(""+item.getProduct().getUseBy());
    }

    public interface OnCosmeticListItemClickListener {
        public void onCosmeticListItemClick(View view, Cosmetic data, int position);
    }

    OnCosmeticListItemClickListener mListener;

    public void setOnCosmeticListItemClickListener(OnCosmeticListItemClickListener cosmeticlistListener) {
        mListener = cosmeticlistListener;
    }
}
