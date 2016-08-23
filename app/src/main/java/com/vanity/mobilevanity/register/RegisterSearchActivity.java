package com.vanity.mobilevanity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }

    @OnClick(R.id.btn_cancel)
    public void onCancelClcik(View view) {
        finish();
    }

    @OnClick(R.id.btn_result)
    public void onResultClick(View view) {
        Intent intent = new Intent(RegisterSearchActivity.this, RegisterDetailActivity.class);
        startActivity(intent);
        finish();
    }
}
