package com.vanity.mobilevanity.mypage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_like)
    public void onLikeClick(View view) {
        Intent intent = new Intent(this, BeautyTipDetailActivity.class);
        startActivity(intent);
    }
}
