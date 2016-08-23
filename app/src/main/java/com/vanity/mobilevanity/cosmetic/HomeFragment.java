package com.vanity.mobilevanity.cosmetic;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.beautytip.BeautyTipFragment;
import com.vanity.mobilevanity.mypage.MyPageFragment;
import com.vanity.mobilevanity.register.RegisterBarcodeActivity;
import com.vanity.mobilevanity.sale.SaleFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_alarm)
    public void onAlarmClick(View view) {
        Intent intent = new Intent(getContext(), AlertActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_floating)
    public void onFloatingClick(View view) {
        Intent intent = new Intent(getContext(), RegisterBarcodeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_home)
    public void onHomeClick(View view) {
        Intent intent = new Intent(getContext(), HomeFragment.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_beautytip)
    public void onBeautyTipClick(View view) {
        Intent intent = new Intent(getContext(), BeautyTipFragment.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_sale)
    public void onSaleClick(View view) {
        Intent intent = new Intent(getContext(), SaleFragment.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_mypage)
    public void onMypageClick(View view) {
        Intent intent = new Intent(getContext(), MyPageFragment.class);
        startActivity(intent);
    }


}
