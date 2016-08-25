package com.vanity.mobilevanity.beautytip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanity.mobilevanity.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeautyTipDetailActivity extends AppCompatActivity {

    @BindView(R.id.text_title)
    TextView titleView;

    @BindView(R.id.text_content)
    TextView contentView;

    @BindView(R.id.image_beauty_tip)
    ImageView beautytipImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_tip_detail);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_update, menu);
        getMenuInflater().inflate(R.menu.actionbar_delete, menu);
        getMenuInflater().inflate(R.menu.actionbar_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_cancel) {
            finish();
            return true;
        }

        if(item.getItemId() == R.id.menu_update) {
            Intent intent = new Intent(BeautyTipDetailActivity.this, BeautyTipWriteActivity.class);
            startActivity(intent);
            return true;
        }

        if(item.getItemId() == R.id.menu_delete) {
            finish();
            return true;
        }
//        switch (item.getItemId()) {
//            case R.id.menu_update:
//            case R.id.menu_delete:
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }

}
