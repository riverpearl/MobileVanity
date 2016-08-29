package com.vanity.mobilevanity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.LoginRequest;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_fb_login)
    public void onLoginClick(View view) {
        String email = "admin@mobilevanity.com";
        String password = "pwd";
        String regId = "1234";

        LoginRequest request = new LoginRequest(this, email, password, regId);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(SplashActivity.this, errorCode + " : " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.btn_fb_signup)
    public void onSignUpClick(View view) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
