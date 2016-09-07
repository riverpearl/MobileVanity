package com.vanity.mobilevanity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    AlarmManager alarmManager;

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
        } else {
            tabHost.setCurrentTab(0);
        }

        if (!PropertyManager.getInstance().getIsAlarmCreated()) {
            onAlarm();
            PropertyManager.getInstance().setIsAlarmCreated(true);
            Calendar nowCal = Calendar.getInstance();
            DateCalculator calculator = new DateCalculator();
            String nowStr = calculator.CalToStr(nowCal);
            PropertyManager.getInstance().setLastNotifyDate(nowStr);
        }

    }

    public void onAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, CosmeticReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, 8);
        calendar.set(Calendar.DATE, 7);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 14);
        calendar.set(Calendar.SECOND, 30);

        Log.d("c", ""+calendar.getTime());

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
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
}
