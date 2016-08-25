package com.vanity.mobilevanity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class BeautyTipCommentPopUpViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_id)
    TextView idView;

    @BindView(R.id.text_user)
    TextView userView;

    @BindView(R.id.text_comment)
    TextView commentView;

    private Comment data;

    public BeautyTipCommentPopUpViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void setComment(Comment data) {
        this.data = data;
       // idView.setText(""+data.getBeautyTipId());
     //   userView.setText(""+ data.getWriter());
        commentView.setText(data.getContent());
    }
}
