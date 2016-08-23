package com.vanity.mobilevanity.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vanity.mobilevanity.R;

import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @OnClick(R.id.btn_access_term)
    public void onAccessTermClick(View view) {
        Intent intent = new Intent(SettingActivity.this, AccessTermActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_faq)
    public void onFAQClick(View view) {
        Intent intent = new Intent(SettingActivity.this, FAQActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_partnership)
    public void onPartnershipClick(View view) {
        Intent intent = new Intent(SettingActivity.this, PartnershipActivity.class);
        startActivity(intent);
    }
}
