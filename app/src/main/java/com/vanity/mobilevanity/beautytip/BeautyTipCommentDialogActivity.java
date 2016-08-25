package com.vanity.mobilevanity.beautytip;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.BeautyTipCommentPopUpAdapter;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-25.
 */
public class BeautyTipCommentDialogActivity extends AppCompatActivity {
    @BindView(R.id.popup_list)
    RecyclerView listView;

    BeautyTipCommentPopUpAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_beauty_tip_popup);
        ButterKnife.bind(this);
        mAdapter = new BeautyTipCommentPopUpAdapter();

        listView.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        init();
    }

    public void init() {
        Comment comment = new Comment();

        comment.setContent("Content");
        //comment.setBeautyTipId();
        //comment.setWriter();
        mAdapter.add(comment);

    }
}
