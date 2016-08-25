package com.vanity.mobilevanity.mypage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.alert.AlertActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateProfileActivity extends AppCompatActivity {

    @BindView(R.id.text_nickname)
    TextView nicknameView;

    @BindView(R.id.group_select_gender)
    RadioGroup groupGenderView;

    @BindView(R.id.group_select_skin_type)
    RadioGroup groupSkinTypeView;

    @BindView(R.id.group_select_skin_tone)
    RadioGroup groupSkinToneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        Intent intent = getIntent();
        int gender = intent.getIntExtra(MyPageFragment.TAG_GENDER, 1);
        int type = intent.getIntExtra(MyPageFragment.TAG_SKIN_TYPE, 1);
        int tone = intent.getIntExtra(MyPageFragment.TAG_SKIN_TONE, 1);

        nicknameView.setText(intent.getStringExtra(MyPageFragment.TAG_NICKNAME));

        switch (gender) {
            case 1 :
                groupGenderView.check(R.id.radio_male);
                break;
            case 2 :
                groupGenderView.check(R.id.radio_female);
                break;
        }

        switch (type) {
            case 1 :
                groupSkinTypeView.check(R.id.radio_dry);
                break;
            case 2 :
                groupSkinTypeView.check(R.id.radio_normal);
                break;
            case 3 :
                groupSkinTypeView.check(R.id.radio_oily);
                break;
            case 4 :
                groupSkinTypeView.check(R.id.radio_complex);
                break;
        }

        switch (tone) {
            case 1 :
                groupSkinToneView.check(R.id.radio_13);
                break;
            case 2 :
                groupSkinToneView.check(R.id.radio_21);
                break;
            case 3 :
                groupSkinToneView.check(R.id.radio_23);
                break;
        }
    }

    @OnClick(R.id.btn_set)
    public void onAlarmClick(View view) {
        finish();
    }

    @OnClick(R.id.btn_withdraw)
    public void onWithDrawClick(View view) {
        //앱 종료
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
