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
import com.vanity.mobilevanity.SplashActivity;
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

    @BindView(R.id.text_gender)
    TextView genderView;

    @BindView(R.id.text_skin_type)
    TextView skinTypeView;

    @BindView(R.id.text_skin_tone)
    TextView skinToneView;

    @BindView(R.id.text_eye_count)
    TextView eyeCountView;

    @BindView(R.id.text_lip_count)
    TextView lipCountView;

    @BindView(R.id.text_skin_count)
    TextView skinCountView;

    @BindView(R.id.text_base_count)
    TextView baseCountView;

    @BindView(R.id.text_cleansing_count)
    TextView cleansingCountView;

    @BindView(R.id.text_tool_count)
    TextView toolCountView;

    User user;

    public final static String TAG_NICKNAME = "nickname";
    public final static String TAG_SKIN_TYPE = "skintype";
    public final static String TAG_SKIN_TONE = "skintone";
    public final static String TAG_GENDER = "gender";
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
        init();

        return view;
    }

    private void init() {
        user = new User();
        user.setUserNickname("Vanity");
        user.setGender(2);
        user.setSkinType(2);
        user.setSkinTone(2);
        user.setEyeNum(10);
        user.setLipNum(10);
        user.setSkinNum(10);
        user.setBaseNum(10);
        user.setCleansingNum(10);
        user.setToolNum(10);

        nicknameView.setText(user.getUserNickname());

        switch (user.getGender()) {
            case 1 : default :
                genderView.setText("남성");
                break;
            case 2 :
                genderView.setText("여성");
                break;
        }

        switch (user.getSkinType()) {
            case 1 : default :
                skinTypeView.setText("건성");
                break;
            case 2 :
                skinTypeView.setText("중성");
                break;
            case 3 :
                skinTypeView.setText("지성");
                break;
            case 4 :
                skinTypeView.setText("복합성");
                break;
        }

        switch (user.getSkinTone()) {
            case 1 : default :
                skinToneView.setText("13호");
                break;
            case 2 :
                skinToneView.setText("21호");
                break;
            case 3 :
                skinToneView.setText("23호");
                break;
        }

        eyeCountView.setText(user.getEyeNum() + "");
        lipCountView.setText(user.getLipNum() + "");
        skinCountView.setText(user.getSkinNum() + "");
        baseCountView.setText(user.getBaseNum() + "");
        cleansingCountView.setText(user.getCleansingNum() + "");
        toolCountView.setText(user.getToolNum() + "");
    }

    @OnClick(R.id.btn_logout)
    public void onLogoutClick(View view) {
        Intent intent = new Intent(getContext(), SplashActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.btn_modification)
    public void onModificationClick(View view) {
        Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
        intent.putExtra(TAG_NICKNAME, user.getUserNickname());
        intent.putExtra(TAG_GENDER, user.getGender());
        intent.putExtra(TAG_SKIN_TYPE, user.getSkinType());
        intent.putExtra(TAG_SKIN_TONE, user.getSkinTone());
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
