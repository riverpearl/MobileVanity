package com.vanity.mobilevanity.cosmetic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CosmeticDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_more)
    public void onMoreClick(View view) {
        Intent intent = new Intent(this, BeautyTipDetailActivity.class);
        startActivity(intent);

    }
}
