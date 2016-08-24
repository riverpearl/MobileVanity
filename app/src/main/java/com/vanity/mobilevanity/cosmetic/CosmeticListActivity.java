package com.vanity.mobilevanity.cosmetic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.register.RegisterBarcodeActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CosmeticListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_list);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_floating)
    public void onFloatingClick(View view) {
        Intent intent = new Intent(this, RegisterBarcodeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_beauty_tip_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_update:
            case R.id.menu_delete:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
