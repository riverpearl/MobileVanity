package com.vanity.mobilevanity.mypage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.alert.AlertActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_quit)
    public void onQuitClick(View view) {
        finish();
    }

    @OnClick(R.id.btn_set)
    public void onAlarmClick(View view) {
        finish();
    }

    @OnClick(R.id.btn_withdraw)
    public void onWithDrawClick(View view) {
       int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
    }

}
