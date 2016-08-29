package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.Comment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class BeautyTipPopUpViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_user_id)
    TextView idView;

    @BindView(R.id.text_user_name)
    TextView nameView;

    @BindView(R.id.text_comment)
    TextView commentView;

    private Comment data;

    public BeautyTipPopUpViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setPopUp(Comment item) {
        this.data = item;
        idView.setText("" + item.getBeautyTipId());
        nameView.setText(item.getWriter().getUserNickName());
        commentView.setText(item.getContent());
    }
}
