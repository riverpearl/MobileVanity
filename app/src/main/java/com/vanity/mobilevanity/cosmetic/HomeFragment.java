package com.vanity.mobilevanity.cosmetic;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
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

    @OnClick(R.id.btn_makeup)
    public void onMakeupClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        startActivity(intent);
    }
}
