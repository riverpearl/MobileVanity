package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.Comment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class BeautyTipPopUpViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_userprofile)
    ImageView profileView;

    @BindView(R.id.text_nickname)
    TextView nicknameView;

    @BindView(R.id.text_comment)
    TextView commentView;

    private Comment data;

    public BeautyTipPopUpViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setPopUp(Comment item) {
        this.data = item;
        Glide.with(profileView.getContext())
                .load(item.getWriter().getUserProfile())
                .into(profileView);

        nicknameView.setText(item.getWriter().getUserNickName());
        commentView.setText(item.getContent());
    }
}
