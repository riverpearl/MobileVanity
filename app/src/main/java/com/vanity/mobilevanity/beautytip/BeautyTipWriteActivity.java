package com.vanity.mobilevanity.beautytip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.vanity.mobilevanity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeautyTipWriteActivity extends AppCompatActivity {

    @BindView(R.id.text_title)
    EditText titleView;

    @BindView(R.id.text_content)
    EditText contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_tip_write);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_set)
    public void onSetClick(View view) {
        finish();
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

