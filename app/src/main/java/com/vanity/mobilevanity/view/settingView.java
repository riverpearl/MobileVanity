package com.vanity.mobilevanity.view;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.vanity.mobilevanity.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-26.
 */
public class SettingView extends FrameLayout {

    public SettingView(Context context) {
        super(context);
        init();
    }

    TextView titleView;

    private void init() {
        inflate(getContext(), R.layout.view_setting, this);
        titleView = (TextView)findViewById(R.id.text_title);
    }

    public void setTitleView(String title) {
        titleView.setText(title);
    }
}
