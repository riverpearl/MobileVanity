package com.vanity.mobilevanity.beautytip;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.BeautyTipCommentPopUpAdapter;
import com.vanity.mobilevanity.view.LikeViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeautyTipDetailActivity extends AppCompatActivity {

    @BindView(R.id.text_title)
    TextView titleView;

    @BindView(R.id.text_content)
    TextView contentView;

    @BindView(R.id.image_beauty_tip)
    ImageView beautytipImage;



    PopupWindow popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_tip_detail);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_update, menu);
        getMenuInflater().inflate(R.menu.actionbar_delete, menu);
        getMenuInflater().inflate(R.menu.actionbar_cancel, menu);
        return true;
    }

    @OnClick(R.id.btn_comment)
    public void onCommentClick() {
        View view = getLayoutInflater().inflate(R.layout.view_beauty_tip_popup, null);
        view.setBackgroundColor(Color.BLUE);
        popup = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setOutsideTouchable(true);
        popup.showAtLocation(view, Gravity.CENTER, 0,0);
        popup.showAtLocation(findViewById(R.id.btn_comment), Gravity.CENTER,0, 0);

//        Intent intent = new Intent(BeautyTipDetailActivity.this, BeautyTipCommentDialogActivity.class);
//        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_cancel) {
            finish();
            return true;
        }

        if (item.getItemId() == R.id.menu_update) {
            Intent intent = new Intent(BeautyTipDetailActivity.this, BeautyTipWriteActivity.class);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.menu_delete) {
            finish();
            return true;
        }
//        switch (item.getItemId()) {
//            case R.id.menu_update:
//            case R.id.menu_delete:
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }

}
