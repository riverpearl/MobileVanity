package com.vanity.mobilevanity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.FAQ;
import com.vanity.mobilevanity.view.AnswerViewHolder;
import com.vanity.mobilevanity.view.QuestionViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class FAQAdapter extends ExpandableRecyclerAdapter<QuestionViewHolder, AnswerViewHolder> {
    private LayoutInflater mInflator;

    /**
     * Primary constructor. Sets up {@link #mParentItemList} and {@link #mItemList}.
     * <p>
     * Changes to {@link #mParentItemList} should be made through add/remove methods in
     * {@link ExpandableRecyclerAdapter}
     *
     * @param parentItemList List of all {@link ParentListItem} objects to be
     *                       displayed in the RecyclerView that this
     *                       adapter is linked to
     */
    public FAQAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public QuestionViewHolder onCreateParentViewHolder(ViewGroup parent) {
        View view = mInflator.inflate(R.layout.view_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public AnswerViewHolder onCreateChildViewHolder(ViewGroup parent) {
        View view = mInflator.inflate(R.layout.view_answer, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(QuestionViewHolder holder, int position, ParentListItem parentListItem) {
        FAQ faq = (FAQ)parentListItem;
        holder.setData(faq);
    }

    @Override
    public void onBindChildViewHolder(AnswerViewHolder holder, int position, Object childListItem) {
        String content = (String)childListItem;
        holder.setData(content);
    }
}
