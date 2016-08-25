package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BeautyTip;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 은미 on 2016-08-24.
 */
public class BeautyTipItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_title)
    TextView titleView;
    @BindView(R.id.text_content)
    TextView contentView;
    @BindView(R.id.text_user)
    TextView userView;
    @BindView(R.id.text_like)
    TextView likeView;
    @BindView(R.id.text_comment)
    TextView commentView;
    @BindView(R.id.text_read)
    TextView readView;

    @BindView(R.id.image_profile)
    ImageView userProfileView;
    @BindView(R.id.image_beauty_tip_item)
    ImageView previewImageView;

    private BeautyTip item;

    public BeautyTipItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setBeautyTipItem(BeautyTip item) {
        this.item = item;
        titleView.setText(item.getTitle());
        contentView.setText(item.getContent());
        userView.setText(item.getUser().getUserNickname());
        likeView.setText("" + item.getLikeNum());
        commentView.setText("" + item.getCommentNum());
        readView.setText("" + item.getReadNum());

        Glide.with(userProfileView.getContext())
                .load(item.getUser().getUserProfile())
                .into(userProfileView);
        Glide.with(previewImageView.getContext())
                .load(item.getPreviewImage())
                .into(previewImageView);
    }
}
