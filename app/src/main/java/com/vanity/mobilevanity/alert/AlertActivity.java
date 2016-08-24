package com.vanity.mobilevanity.alert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.AlertAdapter;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.cosmetic.CosmeticDetailActivity;
import com.vanity.mobilevanity.data.AlertItem;
import com.vanity.mobilevanity.data.CommentItem;
import com.vanity.mobilevanity.data.LikeItem;
import com.vanity.mobilevanity.data.UseByItem;
import com.vanity.mobilevanity.view.AlertCommentViewHolder;
import com.vanity.mobilevanity.view.AlertLikeViewHolder;
import com.vanity.mobilevanity.view.AlertUseByViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlertActivity extends AppCompatActivity {

    @BindView(R.id.alert_list)
    RecyclerView listView;

    AlertAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        ButterKnife.bind(this);

        mAdapter = new AlertAdapter();
        mAdapter.setOnAdapterItemClickListener(new AlertAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapteItemClick(View view, AlertItem item, int position) {
                if (item instanceof CommentItem) {
                    Intent intent = new Intent(AlertActivity.this, BeautyTipDetailActivity.class);
                    startActivity(intent);
                } else if (item instanceof LikeItem) {
                    Intent intent = new Intent(AlertActivity.this, BeautyTipDetailActivity.class);
                    startActivity(intent);
                } else if (item instanceof UseByItem) {
                    Intent intent = new Intent(AlertActivity.this, CosmeticDetailActivity.class);
                    startActivity(intent);

                }
            }
        });

        listView.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);


        initData();
    }

    public void initData() {
        CommentItem commentItem = new CommentItem("User");
        LikeItem likeItem = new LikeItem("Like");
        UseByItem useByItem = new UseByItem("User", 3);

        commentItem.setUser("user");
        likeItem.setUser("like");

        useByItem.setCosmetic("cosmetic");
        useByItem.setUseby(3);

        mAdapter.add(commentItem);
        mAdapter.add(likeItem);
        mAdapter.add(useByItem);
    }

    @OnClick(R.id.btn_quit)
    public void onQuitClick(View view) {
        finish();
    }

}
