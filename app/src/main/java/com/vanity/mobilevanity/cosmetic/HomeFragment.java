package com.vanity.mobilevanity.cosmetic;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.vanity.mobilevanity.register.RegisterSearchActivity;
import com.vanity.mobilevanity.sale.SaleFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public final static int KEY_HOME = 1;

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
        Message msg = eyeHandler.obtainMessage(0);
        eyeHandler.removeMessages(0);
        eyeHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler eyeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(getContext(), CosmeticListActivity.class);
            intent.putExtra(CosmeticListActivity.TAG_CATEGORY, Constant.INDEX_CATEGORY_EYE);
            startActivity(intent);
        }
    };

    @OnClick(R.id.btn_lip)
    public void onLipClick(View view) {
        Message msg = lipHandler.obtainMessage(0);
        lipHandler.removeMessages(0);
        lipHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler lipHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(getContext(), CosmeticListActivity.class);
            intent.putExtra(CosmeticListActivity.TAG_CATEGORY, Constant.INDEX_CATEGORY_LIP);
            startActivity(intent);
        }
    };

    @OnClick(R.id.btn_skin)
    public void onSkinClick(View view) {
        Message msg = skinHandler.obtainMessage(0);
        skinHandler.removeMessages(0);
        skinHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler skinHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(getContext(), CosmeticListActivity.class);
            intent.putExtra(CosmeticListActivity.TAG_CATEGORY, Constant.INDEX_CATEGORY_SKIN);
            startActivity(intent);
        }
    };

    @OnClick(R.id.btn_face)
    public void onFaceClick(View view) {
        Message msg = faceHandler.obtainMessage(0);
        faceHandler.removeMessages(0);
        faceHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler faceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(getContext(), CosmeticListActivity.class);
            intent.putExtra(CosmeticListActivity.TAG_CATEGORY, Constant.INDEX_CATEGORY_FACE);
            startActivity(intent);
        }
    };

    @OnClick(R.id.btn_cleansing)
    public void onCleansingClick(View view) {
        Message msg = cleansingHandler.obtainMessage(0);
        cleansingHandler.removeMessages(0);
        cleansingHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler cleansingHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(getContext(), CosmeticListActivity.class);
            intent.putExtra(CosmeticListActivity.TAG_CATEGORY, Constant.INDEX_CATEGORY_CLEANSING);
            startActivity(intent);
        }
    };

    @OnClick(R.id.btn_tool)
    public void onToolClick(View view) {
        Message msg = toolHandler.obtainMessage(0);
        toolHandler.removeMessages(0);
        toolHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler toolHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(getContext(), CosmeticListActivity.class);
            intent.putExtra(CosmeticListActivity.TAG_CATEGORY, Constant.INDEX_CATEGORY_TOOL);
            startActivity(intent);
        }
    };

    @OnClick(R.id.fab_add_cosmetic)
    public void onAddClick(View view) {
        Message msg = cosmeticHandler.obtainMessage(0);
        cosmeticHandler.removeMessages(0);
        cosmeticHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler cosmeticHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(getContext(), RegisterBarcodeActivity.class);
            intent.putExtra(RegisterSearchActivity.TAG_REQUEST_CODE, KEY_HOME);
            startActivity(intent);
        }
    };
}
