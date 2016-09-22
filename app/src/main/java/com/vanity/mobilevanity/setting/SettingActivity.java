package com.vanity.mobilevanity.setting;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.vanity.mobilevanity.BaseActivity;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.SplashActivity;
import com.vanity.mobilevanity.adapter.SettingAdapter;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.manager.PropertyManager;
import com.vanity.mobilevanity.request.LogOutRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_toolbar_title)
    TextView toolbarTitleView;

    @BindView(R.id.list_setting)
    ListView settingView;

    Switch switchView;

    SettingAdapter sAdapter;
    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        View versionView = getLayoutInflater().inflate(R.layout.view_version, null);
        View alertView = getLayoutInflater().inflate(R.layout.view_alert, null);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitleView.setText(getResources().getString(R.string.toolbar_title_setting));
        toolbarTitleView.setTypeface(Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf"));

        settingView.addHeaderView(versionView, null, false);
        settingView.addHeaderView(alertView, null, false);

        switchView = (Switch)findViewById(R.id.switch_alert);
        switchView.setChecked(PropertyManager.getInstance().getIsAlertReceptible());
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                PropertyManager.getInstance().setIsAlertReceptible(isChecked);
            }
        });

        sAdapter = new SettingAdapter();
        settingView.setAdapter(sAdapter);

        settingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
                lastClickTime = SystemClock.elapsedRealtime();

                Intent intent;

                switch (position) {
                    case 2:
                        intent = new Intent(SettingActivity.this, AccessTermActivity.class);
                        break;
                    case 3:
                        intent = new Intent(SettingActivity.this, FAQActivity.class);
                        break;
                    case 4:
                        intent = new Intent(SettingActivity.this, PartnershipActivity.class);
                        break;
                    case 5:
                        logOut();
                        return;
                    default:
                        return;
                }

                startActivity(intent);
            }
        });

        init();
    }

    private void init() {
        String accessTerm = getResources().getString(R.string.toolbar_title_access_term);
        String faq = getResources().getString(R.string.toolbar_title_faq);
        String partnership = getResources().getString(R.string.toolbar_title_partnership);
        String logout = getResources().getString(R.string.activity_setting_title_logout);

        sAdapter.add(accessTerm);
        sAdapter.add(faq);
        sAdapter.add(partnership);
        sAdapter.add(logout);
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

    private void logOut() {
        LogOutRequest request = new LogOutRequest(this);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<String>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<String>> request, NetworkResult<String> result) {
                Intent intent = new Intent(SettingActivity.this, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                PropertyManager.getInstance().setFacebookId("");
                PropertyManager.getInstance().setUserId(0);
                PropertyManager.getInstance().setIsAlarmCreated(false);
                PropertyManager.getInstance().setRegistrationId("");
                PropertyManager.getInstance().setLastNotifyDate("");
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<String>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }
}
