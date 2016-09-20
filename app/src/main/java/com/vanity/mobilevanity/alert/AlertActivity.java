package com.vanity.mobilevanity.alert;

import android.content.Intent;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.AlertAdapter;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.cosmetic.CosmeticDetailActivity;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.data.DBContract;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.Notify;
import com.vanity.mobilevanity.data.Product;
import com.vanity.mobilevanity.data.User;
import com.vanity.mobilevanity.manager.DBManager;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.NotifyListRequest;
import com.vanity.mobilevanity.util.DateCalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertActivity extends AppCompatActivity {

    @BindView(R.id.alert_list)
    RecyclerView listView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_toolbar_title)
    TextView titleView;

    AlertAdapter mAdapter;
    List<Notify> notifyList = new ArrayList<>();
    DateCalculator calculator = new DateCalculator();

    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        titleView.setText(getResources().getString(R.string.toolbar_title_alert));

        mAdapter = new AlertAdapter();
        mAdapter.setOnAdapterItemClickListener(new AlertAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, Notify item, int position) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
                lastClickTime = SystemClock.elapsedRealtime();

                if (item.getType().equals("useby")) {
                    Intent intent = new Intent(AlertActivity.this, CosmeticDetailActivity.class);
                    intent.putExtra(CosmeticDetailActivity.TAG_COSMETIC_ITEM_ID, item.getContentId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AlertActivity.this, BeautyTipDetailActivity.class);
                    intent.putExtra(BeautyTipDetailActivity.TAG_BEAUTY_TIP_ID, item.getContentId());
                    startActivity(intent);
                }
            }
        });
        listView.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        //int result = DBManager.getInstance().insertNotify(5161363399770112L, "usebylimitiscoming", "2016-09-01T10:10:10.123+0900");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return false;
        lastClickTime = SystemClock.elapsedRealtime();

        if (item.getItemId() == R.id.menu_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Calendar aWeekAgo = Calendar.getInstance();
        aWeekAgo.add(Calendar.DATE, -7);

        Cursor c = DBManager.getInstance().selectNotifyList();
        notifyList.clear();

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Calendar cal = calculator.StrToCal(c.getString(c.getColumnIndex(DBContract.Notify.COLUMN_DATE)));

                if (cal.before(aWeekAgo))
                    continue;

                Notify notify = new Notify();
                notify.setType(c.getString(c.getColumnIndex(DBContract.Notify.COLUMN_TYPE)));
                notify.setContentId(c.getLong(c.getColumnIndex(DBContract.Notify.COLUMN_CONTENT_ID)));
                notify.setMessage(c.getString(c.getColumnIndex(DBContract.Notify.COLUMN_MESSAGE)));
                notify.setDate(c.getString(c.getColumnIndex(DBContract.Notify.COLUMN_DATE)));
                notifyList.add(notify);
            }

            Collections.sort(notifyList, new Comparator<Notify>() {
                @Override
                public int compare(Notify noti1, Notify noti2) {
                    Calendar noti1cal = calculator.StrToCal(noti1.getDate());
                    Calendar noti2cal = calculator.StrToCal(noti2.getDate());

                    return (noti1cal.before(noti2cal)) ? -1 : 1;
                }
            });

            mAdapter.clear();
            mAdapter.addAll(notifyList);
        }
    }
}
