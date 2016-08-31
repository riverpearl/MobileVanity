package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.Cosmetic;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class CosmeticViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_cosmetic)
    ImageView cosmeticView;

    @BindView(R.id.text_color_code)
    TextView colorCodeView;

    @BindView(R.id.text_color_name)
    TextView colorNameView;

    @BindView(R.id.text_product_name)
    TextView productNameView;

    @BindView(R.id.text_brand_name)
    TextView brandNameView;

    Cosmetic data;

    public CosmeticViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onCosmeticItemClick(view, data, getAdapterPosition());
            }
        });
    }

    public void setData(Cosmetic data) {
        this.data = data;

        Glide.with(cosmeticView.getContext()).load(data.getImage()).into(cosmeticView);
        colorCodeView.setText(data.getColorName());
        //colorNameView.setText(data.getColorName());
        productNameView.setText(data.getProduct().getName());
        brandNameView.setText(data.getProduct().getBrand().getName());
    }

    public interface OnCosmeticItemClickListener {
        public void onCosmeticItemClick(View view, Cosmetic data, int position);
    }

    OnCosmeticItemClickListener mListener;
    public void setOnLikeItemClickListener(OnCosmeticItemClickListener listener) {
        mListener = listener;
    }
}
