package com.vanity.mobilevanity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.cosmetic.CosmeticListActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_detail);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_cancel)
    public void onCancelClick(View view) {
        finish();
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick(View view) {
        finish();
    }
}
