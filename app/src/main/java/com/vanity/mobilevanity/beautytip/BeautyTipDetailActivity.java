package com.vanity.mobilevanity.beautytip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.vanity.mobilevanity.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeautyTipDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_tip_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_before)
    public void onBeforeClick(View view) {
        this.finish();
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
