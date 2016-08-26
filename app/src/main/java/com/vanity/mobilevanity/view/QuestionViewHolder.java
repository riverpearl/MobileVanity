package com.vanity.mobilevanity.view;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.FAQ;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class QuestionViewHolder extends ParentViewHolder {

    @BindView(R.id.text_question)
    TextView questionView;

    FAQ data;

    public QuestionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(FAQ data) {
        this.data = data;

        questionView.setText(data.getTitle());
    }
}
