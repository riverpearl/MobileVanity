package com.vanity.mobilevanity.beautytip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.BeautyTipPopUpAdapter;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeautyTipPopUpActivity extends AppCompatActivity {
    @BindView(R.id.popup_list)
    RecyclerView listView;

    BeautyTipPopUpAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_tip_pop_up);
        ButterKnife.bind(this);
        mAdapter = new BeautyTipPopUpAdapter();
        listView.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        init();
    }

    public void init() {
            Comment comment = new Comment();
            User user = new User();

            comment.setId(3);
            user.setUserNickname("User");
            comment.setWriter(user);
            comment.setContent("Content");

            mAdapter.add(comment);

    }
}
