package com.vanity.mobilevanity.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.vanity.mobilevanity.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartnershipActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_toolbar_title)
    TextView toolbarTitleView;

    @BindView(R.id.text_partnership)
    TextView partnershipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitleView.setText(getResources().getString(R.string.toolbar_title_partnership));

        partnershipView.setText("함께 만들어가는 베니띠.\n" +
            "제휴를 원하시는 경우 아래 주소로 연락주세요.\n\n\n\n\n" +
            "help@mobilevanity.com");
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
