package com.vanity.mobilevanity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vanity.mobilevanity.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterBarcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_barcode);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_cancel)
    public void onCancelButton(View view) {
        finish();
    }

    @OnClick(R.id.btn_read_barcode)
    public void onReadBarcodeClick(View view) {
        Intent intent = new Intent(RegisterBarcodeActivity.this, RegisterDetailActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_register_myself)
    public void onRegisterMyselfClick(View view) {
        Intent intent = new Intent(RegisterBarcodeActivity.this, RegisterSearchActivity.class);
        startActivity(intent);
        finish();
    }
}
