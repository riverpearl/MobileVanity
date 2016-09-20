package com.vanity.mobilevanity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.beautytip.BeautyTipFragment;
import com.vanity.mobilevanity.cosmetic.CosmeticReceiver;
import com.vanity.mobilevanity.cosmetic.HomeFragment;
import com.vanity.mobilevanity.manager.PropertyManager;
import com.vanity.mobilevanity.mypage.MyPageFragment;
import com.vanity.mobilevanity.sale.SaleFragment;
import com.vanity.mobilevanity.util.DateCalculator;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tabhost)
    FragmentTabHost tabHost;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public final static String TAG_TAB_HOME = "home";
    public final static String TAG_TAB_BEAUTYTIP = "beautytip";
    public final static String TAG_TAB_SALE = "sale";
    public final static String TAG_TAB_MYPAGE = "mypage";

    AlarmManager alarmManager;
    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView homeTabIndicator = new ImageView(this);
        homeTabIndicator.setImageResource(R.drawable.selector_main_tab_home);
        ImageView beautytipTabIndicator = new ImageView(this);
        beautytipTabIndicator.setImageResource(R.drawable.selector_main_tab_beautytip);
        ImageView saleTabIndicator = new ImageView(this);
        saleTabIndicator.setImageResource(R.drawable.selector_main_tab_sale);
        ImageView mypageTabIndicator = new ImageView(this);
        mypageTabIndicator.setImageResource(R.drawable.selector_main_tab_mypage);

        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec(TAG_TAB_HOME).setIndicator(homeTabIndicator), HomeFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TAG_TAB_BEAUTYTIP).setIndicator(beautytipTabIndicator), BeautyTipFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TAG_TAB_SALE).setIndicator(saleTabIndicator), SaleFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(TAG_TAB_MYPAGE).setIndicator(mypageTabIndicator), MyPageFragment.class, null);

        Intent intent = getIntent();
        int tab = intent.getIntExtra("saletab", 1);

        if (tab == 3) {
            tabHost.setCurrentTab(2);
        } else {
            tabHost.setCurrentTab(0);
        }

        if (!PropertyManager.getInstance().getIsAlarmCreated()) {
            setAlarmManager();
            PropertyManager.getInstance().setIsAlarmCreated(true);
            Calendar nowCal = Calendar.getInstance();
            DateCalculator calculator = new DateCalculator();
            String nowStr = calculator.CalToStr(nowCal);
            PropertyManager.getInstance().setLastNotifyDate(nowStr);
        }

    }

    public void setAlarmManager() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, CosmeticReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return false;
        lastClickTime = SystemClock.elapsedRealtime();

        if (item.getItemId() == R.id.menu_alert) {
            Intent intent = new Intent(MainActivity.this, AlertActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.logo)
    public void onLogoClick(View view) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
        lastClickTime = SystemClock.elapsedRealtime();

        tabHost.setCurrentTabByTag(TAG_TAB_HOME);
    }
}
