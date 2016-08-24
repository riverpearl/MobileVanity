package com.vanity.mobilevanity.mypage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.cosmetic.CosmeticListActivity;
import com.vanity.mobilevanity.setting.SettingActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {


    public MyPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_modification)
    public void onModificationClick(View view) {
        Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_setting)
    public void onSettingClick(View view) {
        Intent intent = new Intent(getContext(), SettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_my_cosmetic)
    public void onMyCosmeticClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_tip)
    public void onTipClick(View view) {
        Intent intent = new Intent(getContext(), MyBeautyTipActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_like)
    public void onLikeClick(View view) {
        Intent intent = new Intent(getContext(), LikeActivity.class);
        startActivity(intent);
    }


}
