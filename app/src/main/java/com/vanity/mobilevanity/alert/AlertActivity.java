package com.vanity.mobilevanity.alert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.AlertAdapter;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.cosmetic.CosmeticDetailActivity;
import com.vanity.mobilevanity.data.AlertItem;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.data.Product;
import com.vanity.mobilevanity.data.User;
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
                if (item instanceof Comment) {
                    Intent intent = new Intent(AlertActivity.this, BeautyTipDetailActivity.class);
                    startActivity(intent);
                } else if (item instanceof User) {
                    Intent intent = new Intent(AlertActivity.this, BeautyTipDetailActivity.class);
                    startActivity(intent);
                } else if (item instanceof CosmeticItem) {
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
        Comment comment = new Comment();
        User user = new User();
        CosmeticItem cosmeticItem = new CosmeticItem();
        Cosmetic cosmetic = new Cosmetic();
        Product product = new Product();

        user.setUserNickName("NickName");
        comment.setWriter(user);

        product.setName("Product");
        cosmetic.setProduct(product);
        cosmeticItem.setCosmetic(cosmetic);

        cosmeticItem.setCosmeticTerm(3);

        mAdapter.add(comment);
        mAdapter.add(user);
        mAdapter.add(cosmeticItem);
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
