package com.vanity.mobilevanity.mypage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.LikeAdapter;
import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.data.LikeData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LikeActivity extends AppCompatActivity {

    @BindView(R.id.rv_like)
    RecyclerView listView;

    LikeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        ButterKnife.bind(this);

        mAdapter = new LikeAdapter();
        mAdapter.setOnAdapterItemClickListener(new LikeAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, LikeData data, int position) {
                Intent intent = new Intent(LikeActivity.this, BeautyTipDetailActivity.class);
                startActivity(intent);
            }
        });

        listView.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        init();
    }

    private void init() {
        for (int i = 0; i < 10; i++) {
            LikeData data = new LikeData();
            data.setTitle("board " + i + " ");
            mAdapter.add(data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
