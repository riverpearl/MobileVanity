package com.vanity.mobilevanity.cosmetic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.sale.SaleFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CosmeticDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_saleinfo)
    public void onSaleInfoClick(View view) {
        Intent intent = new Intent(this, SaleFragment.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_beautytip)
    public void onBeautyTipClick(View view) {
        Intent intent = new Intent(this, BeautyTipDetailActivity.class);
        startActivity(intent);
    }
}
