package com.vanity.mobilevanity.mypage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.cosmetic.CosmeticListActivity;
import com.vanity.mobilevanity.data.User;
import com.vanity.mobilevanity.setting.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {

    public MyPageFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.text_nickname)
    TextView nicknameView;

    @BindView(R.id.text_skin_type)
    TextView skinTypeView;

    @BindView(R.id.text_skin_tone)
    TextView skinToneView;

    @BindView(R.id.text_eye_count)
    TextView eyeCountView;

    @BindView(R.id.text_lip_count)
    TextView lipCountView;

    @BindView(R.id.text_base_count)
    TextView baseCountView;

    public final static String TAG_ITEM_TAB = "item";
    public final static int INDEX_ITEM_EYE = 1;
    public final static int INDEX_ITEM_LIP = 2;
    public final static int INDEX_ITEM_SKIN = 3;
    public final static int INDEX_ITEM_BASE = 4;
    public final static int INDEX_ITEM_CLEANSING = 5;
    public final static int INDEX_ITEM_TOOL = 6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    @OnClick(R.id.text_eye_count)
    public void onEyeCountClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_ITEM_TAB, INDEX_ITEM_EYE);
        startActivity(intent);
    }

    @OnClick(R.id.text_lip_count)
    public void onLipCountClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_ITEM_TAB, INDEX_ITEM_LIP);
        startActivity(intent);
    }

    @OnClick(R.id.text_skin_count)
    public void onSkinCountClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_ITEM_TAB, INDEX_ITEM_SKIN);
        startActivity(intent);
    }

    @OnClick(R.id.text_base_count)
    public void onBaseCountClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_ITEM_TAB, INDEX_ITEM_BASE);
        startActivity(intent);
    }

    @OnClick(R.id.text_cleansing_count)
    public void onCleansingCountClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_ITEM_TAB, INDEX_ITEM_CLEANSING);
        startActivity(intent);
    }

    @OnClick(R.id.text_tool_count)
    public void onToolCountClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_ITEM_TAB, INDEX_ITEM_TOOL);
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
