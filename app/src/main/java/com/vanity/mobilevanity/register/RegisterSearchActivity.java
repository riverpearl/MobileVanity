package com.vanity.mobilevanity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vanity.mobilevanity.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_search);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cancel :
                finish();
                return true;
            case android.R.id.home :
                Intent intent = new Intent(RegisterSearchActivity.this, RegisterBarcodeActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(RegisterSearchActivity.this, RegisterBarcodeActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_result)
    public void onResultClick(View view) {
        Intent intent = new Intent(RegisterSearchActivity.this, RegisterDetailActivity.class);
        startActivity(intent);
        finish();
    }
}
