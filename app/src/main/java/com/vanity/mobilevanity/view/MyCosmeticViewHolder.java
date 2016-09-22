package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.util.DateCalculator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class MyCosmeticViewHolder extends RecyclerView.ViewHolder {

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

    private CosmeticItem data;

    public MyCosmeticViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onCosmeticListItemClick(view, data, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (longClickListener != null) {
                    longClickListener.onCosmeticListItemLongClick(view, data, getAdapterPosition());
                    return true;
                }
                return false;
            }
        });
    }

    public void setCosmeticList(CosmeticItem item) {
        this.data = item;

        colorCodeView.setText(item.getCosmetic().getColorCode());
        colorNameView.setText(item.getCosmetic().getColorName());
        productNameView.setText(item.getCosmetic().getProduct().getName());
        brandNameView.setText(item.getCosmetic().getProduct().getBrand().getName());

        DateCalculator calculator = new DateCalculator();
        int dday = calculator.calculateDDay(item.getDateAdded(), item.getCosmetic().getProduct().getUseBy());

        if (dday > 0)
            ddayView.setText("D+" + dday);
        else ddayView.setText("D" + dday);

        Glide.with(cosmeticView.getContext())
                .load(item.getCosmetic().getImage())
                .into(cosmeticView);

        if (!calculator.isNew(item.getDateAdded()))
            newView.setVisibility(View.GONE);
    }

    public interface OnCosmeticListItemClickListener {
        public void onCosmeticListItemClick(View view, CosmeticItem data, int position);
    }

    OnCosmeticListItemClickListener mListener;

    public void setOnCosmeticListItemClickListener(OnCosmeticListItemClickListener cosmeticlistListener) {
        mListener = cosmeticlistListener;
    }

    public interface OnCosmeticListItemLongClickListener {
        public void onCosmeticListItemLongClick(View view, CosmeticItem data, int position);
    }

    OnCosmeticListItemLongClickListener longClickListener;
    public void setOnCosmeticListItemLongClickListener(OnCosmeticListItemLongClickListener listener) {
        longClickListener = listener;
    }
}
