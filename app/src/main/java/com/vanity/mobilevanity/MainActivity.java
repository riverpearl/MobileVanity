package com.vanity.mobilevanity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.beautytip.BeautyTipFragment;
import com.vanity.mobilevanity.cosmetic.HomeFragment;
import com.vanity.mobilevanity.mypage.MyPageFragment;
import com.vanity.mobilevanity.sale.SaleFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    Fragment f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Fragment f = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, f).commit();
    }

    @OnClick(R.id.btn_alert)
    public void onAlertClick(View view) {
        Intent intent = new Intent(MainActivity.this, AlertActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_home)
    public void onHomeClick(View view) {
        if (f instanceof HomeFragment)
            return;

        f = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, f).commit();
    }

    @OnClick(R.id.btn_beautytip)
    public void onBeautyTipClick(View view) {
        if (f instanceof BeautyTipFragment)
            return;

        f = new BeautyTipFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, f).commit();
    }

    @OnClick(R.id.btn_sale)
    public void onSaleClick(View view) {
        if (f instanceof SaleFragment)
            return;

        f = new SaleFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, f).commit();
    }

    @OnClick(R.id.btn_mypage)
    public void onMypageClick(View view) {
        if (f instanceof MyPageFragment)
            return;

        f = new MyPageFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, f).commit();
    }
}
