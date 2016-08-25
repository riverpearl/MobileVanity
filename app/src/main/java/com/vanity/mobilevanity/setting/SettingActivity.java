package com.vanity.mobilevanity.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vanity.mobilevanity.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
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
