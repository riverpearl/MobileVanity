package com.vanity.mobilevanity.setting;

import android.content.Intent;
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

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.SettingAdapter;
import com.vanity.mobilevanity.manager.PropertyManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_toolbar_title)
    TextView toolbarTitleView;

    @BindView(R.id.list_setting)
    ListView settingView;

    Switch switchView;

    SettingAdapter sAdapter;

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
                Intent intent;
                switch (position) {
                    case 2:
                        intent = new Intent(SettingActivity.this, AccessTermActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(SettingActivity.this, FAQActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(SettingActivity.this, PartnershipActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        init();
    }

    private void init() {
        String accessTerm = "이용약관";
        String faq = "FAQ";
        String partnership = "제휴문의";

        sAdapter.add(accessTerm);
        sAdapter.add(faq);
        sAdapter.add(partnership);
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
