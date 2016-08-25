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
import com.vanity.mobilevanity.data.Constant;
import com.vanity.mobilevanity.mypage.MyPageFragment;
import com.vanity.mobilevanity.register.RegisterBarcodeActivity;
import com.vanity.mobilevanity.sale.SaleFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public final static String TAG_CATEGORY = "category";

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

    @OnClick(R.id.btn_eye)
    public void onEyeClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_CATEGORY, Constant.INDEX_CATEGORY_EYE);
        startActivity(intent);
    }

    @OnClick(R.id.btn_lip)
    public void onLipClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_CATEGORY, Constant.INDEX_CATEGORY_LIP);
        startActivity(intent);
    }

    @OnClick(R.id.btn_skin)
    public void onSkinClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_CATEGORY, Constant.INDEX_CATEGORY_SKIN);
        startActivity(intent);
    }

    @OnClick(R.id.btn_face)
    public void onFaceClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_CATEGORY, Constant.INDEX_CATEGORY_FACE);
        startActivity(intent);
    }

    @OnClick(R.id.btn_cleansing)
    public void onCleansingClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_CATEGORY, Constant.INDEX_CATEGORY_CLEANSING);
        startActivity(intent);
    }

    @OnClick(R.id.btn_tool)
    public void onToolClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_CATEGORY, Constant.INDEX_CATEGORY_TOOL);
        startActivity(intent);
    }

    @OnClick(R.id.fab_add_cosmetic)
    public void onAddClick(View view) {
        Intent intent = new Intent(getContext(), RegisterBarcodeActivity.class);
        startActivity(intent);
    }
}
