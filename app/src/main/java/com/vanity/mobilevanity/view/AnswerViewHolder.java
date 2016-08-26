package com.vanity.mobilevanity.view;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.FAQ;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class AnswerViewHolder extends ChildViewHolder {

    @BindView(R.id.text_answer)
    TextView answerView;

    String data;

    public AnswerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(String data) {
        this.data = data;

        answerView.setText(data);
    }
}
