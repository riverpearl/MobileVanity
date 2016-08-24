package com.vanity.mobilevanity.cosmetic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.sale.SaleFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CosmeticDetailActivity extends AppCompatActivity {

    @BindView(R.id.text_brand)
    TextView brandView;

    @BindView(R.id.text_cosmetic_name)
    TextView cosmeticView;

    @BindView(R.id.image_cosmetic)
    ImageView cosmeticImage;

    @BindView(R.id.text_register_year)
    TextView registerYearView;

    @BindView(R.id.text_register_month)
    TextView registerMonthView;

    @BindView(R.id.text_register_date)
    TextView registerDateView;

    @BindView(R.id.text_usebydate_year)
    TextView useByDateYearView;

    @BindView(R.id.text_usebydate_month)
    TextView useByDateMonthView;

    @BindView(R.id.text_usebydate_date)
    TextView useByDateDateView;

    @BindView(R.id.text_brand_name)
    TextView brandNameView;

    @BindView(R.id.text_sale_information)
    TextView saleInformationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_quit)
    public void onQuitClick(View view) {
        finish();
    }

    @OnClick(R.id.btn_saleinfo)
    public void onSaleInfoClick(View view) {
        Intent intent = new Intent(this, SaleFragment.class);
        startActivity(intent);
    }

    @OnClick(R.id.image_register)
    public void onRegisterButtonClick(View view) {

    }

    @OnClick(R.id.image_usebydate)
    public void onUseByDateButton(View view) {

    }

}
