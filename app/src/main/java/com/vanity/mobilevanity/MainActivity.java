package com.vanity.mobilevanity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.beautytip.BeautyTipFragment;
import com.vanity.mobilevanity.cosmetic.HomeFragment;
import com.vanity.mobilevanity.mypage.MyPageFragment;
import com.vanity.mobilevanity.sale.SaleFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabhost)
    FragmentTabHost tabHost;

    public final static String TAG_TAB_HOME = "home";
    public final static String TAG_TAB_BEAUTYTIP = "beautytip";
    public final static String TAG_TAB_SALE = "sale";
    public final static String TAG_TAB_MYPAGE = "mypage";

    public final static String LABEL_TAB_HOME = "홈";
    public final static String LABEL_TAB_BEAUTYTIP = "뷰티팁";
    public final static String LABEL_TAB_SALE = "세일";
    public final static String LABEL_TAB_MYPAGE = "마이페이지";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec(TAG_TAB_HOME).setIndicator(LABEL_TAB_HOME), HomeFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TAG_TAB_BEAUTYTIP).setIndicator(LABEL_TAB_BEAUTYTIP), BeautyTipFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TAG_TAB_SALE).setIndicator(LABEL_TAB_SALE), SaleFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TAG_TAB_MYPAGE).setIndicator(LABEL_TAB_MYPAGE), MyPageFragment.class, null);

        Intent intent = getIntent();
        int tab = intent.getIntExtra("saletab", 1);

        if (tab == 3) {
            tabHost.setCurrentTab(2);
        }
        else {
            tabHost.setCurrentTab(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_alert) {
            Intent intent = new Intent(MainActivity.this, AlertActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @OnClick(R.id.btn_home)
//    public void onHomeClick(View view) {
//        if (f instanceof HomeFragment)
//            return;
//
//        f = new HomeFragment();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.container, f).commit();
//    }
//
//    @OnClick(R.id.btn_beautytip)
//    public void onBeautyTipClick(View view) {
//        if (f instanceof BeautyTipFragment)
//            return;
//
//        f = new BeautyTipFragment();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.container, f).commit();
//    }
//
//    @OnClick(R.id.btn_sale)
//    public void onSaleClick(View view) {
//        if (f instanceof SaleFragment)
//            return;
//
//        f = new SaleFragment();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.container, f).commit();
//    }
//
//    @OnClick(R.id.btn_mypage)
//    public void onMypageClick(View view) {
//        if (f instanceof MyPageFragment)
//            return;
//
//        f = new MyPageFragment();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.container, f).commit();
//    }
}
