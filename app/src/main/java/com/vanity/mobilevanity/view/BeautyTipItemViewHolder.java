package com.vanity.mobilevanity.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.Comment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.btn_like)
    ImageButton likeButton;

    @BindView(R.id.btn_comment)
    ImageButton commentButton;

    private BeautyTip item;
    private Comment commentItem;

    public BeautyTipItemViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onBeautyTipItemClick(view, item, getAdapterPosition());
                }
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialogListener != null) {
                    dialogListener.onCommentDialogClick(view,item, commentItem);
                }
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(likeListener != null) {
                    likeListener.onLikeClick(view, item, getAdapterPosition());
                }
            }
        });


    }

    public void setBeautyTipItem(BeautyTip item) {
        this.item = item;
        titleView.setText(item.getTitle());
        contentView.setText(item.getContent());
        userView.setText(item.getUser().getUserNickName());
        likeView.setText("" + item.getLikeCount());
        commentView.setText("" + item.getCommentNum());
        readView.setText("" + item.getReadNum());

        Glide.with(previewImageView.getContext())
                .load(item.getPreviewImage())
                .into(previewImageView);

        Glide.with(userProfileView.getContext())
                .load(item.getUser().getUserProfile())
                .into(userProfileView);

        if (item.isLike())
            likeButton.setImageResource(R.drawable.btn_like_selected);
        else likeButton.setImageResource(R.drawable.btn_like);
    }

    public interface OnBeautyTipItemClickListener {
        public void onBeautyTipItemClick(View view, BeautyTip beautyTip, int position);
    }

    OnBeautyTipItemClickListener listener;

    public void setOnBeautyTipItemClickListener(OnBeautyTipItemClickListener listener) {
        this.listener = listener;
    }


    public interface OnCommentDialogClickListener{
        public void onCommentDialogClick(View view, BeautyTip beautyTip, Comment comment);
    }

    OnCommentDialogClickListener dialogListener;

    public void setOnCommentDialogClickListner(OnCommentDialogClickListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public interface OnLikeClickListener{
        public void onLikeClick(View view, BeautyTip beautyTip, int position);
    }

    OnLikeClickListener likeListener;

    public void setOnLikeClickListener(OnLikeClickListener likeListener) {
        this.likeListener = likeListener;
    }
}
