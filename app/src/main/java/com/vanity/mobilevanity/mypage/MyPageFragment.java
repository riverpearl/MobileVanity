package com.vanity.mobilevanity.mypage;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.SplashActivity;
import com.vanity.mobilevanity.cosmetic.CosmeticListActivity;
import com.vanity.mobilevanity.data.Constant;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.MyInfoRequest;
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

    @BindView(R.id.text_face_count)
    TextView faceCountView;

    @BindView(R.id.text_cleansing_count)
    TextView cleansingCountView;

    @BindView(R.id.text_tool_count)
    TextView toolCountView;

    User user;

    public final static int RC_MODIFY = 100;

    public final static String TAG_NICKNAME = "nickname";
    public final static String TAG_SKIN_TYPE = "skintype";
    public final static String TAG_SKIN_TONE = "skintone";
    public final static String TAG_GENDER = "gender";
    public final static String TAG_CATEGORY = "category";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, view);

        return view;
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
        intent.putExtra(TAG_NICKNAME, user.getUserNickName());
        intent.putExtra(TAG_GENDER, user.getGender());
        intent.putExtra(TAG_SKIN_TYPE, user.getSkinType());
        intent.putExtra(TAG_SKIN_TONE, user.getSkinTone());
        startActivityForResult(intent, RC_MODIFY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_MODIFY) {
            if (resultCode == Activity.RESULT_OK) {
                //네트워크에서 프로필정보 받아와서 다시 세팅해줌
            }
        }
    }

    @OnClick(R.id.btn_setting)
    public void onSettingClick(View view) {
        Intent intent = new Intent(getContext(), SettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.text_eye_count)
    public void onEyeCountClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_CATEGORY, Constant.INDEX_CATEGORY_EYE);
        startActivity(intent);
    }

    @OnClick(R.id.text_lip_count)
    public void onLipCountClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_CATEGORY, Constant.INDEX_CATEGORY_LIP);
        startActivity(intent);
    }

    @OnClick(R.id.text_skin_count)
    public void onSkinCountClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_CATEGORY, Constant.INDEX_CATEGORY_SKIN);
        startActivity(intent);
    }

    @OnClick(R.id.text_face_count)
    public void onBaseCountClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_CATEGORY, Constant.INDEX_CATEGORY_FACE);
        startActivity(intent);
    }

    @OnClick(R.id.text_cleansing_count)
    public void onCleansingCountClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_CATEGORY, Constant.INDEX_CATEGORY_CLEANSING);
        startActivity(intent);
    }

    @OnClick(R.id.text_tool_count)
    public void onToolCountClick(View view) {
        Intent intent = new Intent(getContext(), CosmeticListActivity.class);
        intent.putExtra(TAG_CATEGORY, Constant.INDEX_CATEGORY_TOOL);
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

    @Override
    public void onStart() {
        super.onStart();

        MyInfoRequest request = new MyInfoRequest(getContext());
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                User user = result.getResult();
                nicknameView.setText(user.getUserNickName());
                setGenderView(user.getGender());
                setSkinTypeView(user.getSkinType());
                setSkinToneView(user.getSkinTone());
                eyeCountView.setText(user.getEyeNum() + "");
                lipCountView.setText(user.getLipNum() + "");
                skinCountView.setText(user.getSkinNum() + "");
                faceCountView.setText(user.getFaceNum() + "");
                cleansingCountView.setText(user.getCleansingNum() + "");
                toolCountView.setText(user.getToolNum() + "");
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(), errorCode + " : " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setGenderView(int gender) {
        switch (gender) {
            case 1 : default :
                genderView.setText("남성");
                break;
            case 2 :
                genderView.setText("여성");
                break;
        }
    }

    private void setSkinTypeView(int type) {
        switch (type) {
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
    }

    private void setSkinToneView(int tone) {
        switch (tone) {
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
    }
}
