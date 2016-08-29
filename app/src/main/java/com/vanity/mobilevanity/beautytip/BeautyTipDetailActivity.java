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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.SearchBeautyTipRequest;
import com.vanity.mobilevanity.view.LikeViewHolder;

import java.util.List;

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

    @BindView(R.id.btn_like)
    Button likeButton;

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

    @OnClick(R.id.btn_like)
    public void onLikeClick() {
        likeButton.setBackgroundColor(Color.YELLOW);
    }

    @OnClick(R.id.btn_comment)
    public void onCommentClick() {
        View view = getLayoutInflater().inflate(R.layout.view_beauty_tip_pop_up, null);
        view.setBackgroundColor(Color.BLUE);
        popup = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setOutsideTouchable(true);
        popup.showAtLocation(view, Gravity.CENTER, 0, 0);
        popup.showAtLocation(findViewById(R.id.btn_comment), Gravity.CENTER, 0, 0);

//        Intent intent = new Intent(BeautyTipDetailActivity.this, BeautyTipPopUpActivity.class);
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

    @Override
    protected void onStart() {
        super.onStart();
//        SearchBeautyTipRequest request = new SearchBeautyTipRequest(getBaseContext(), titleView.getText().toString());
//        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<BeautyTip>>>() {
//            @Override
//            public void onSuccess(NetworkRequest<NetworkResult<List<BeautyTip>>> request, NetworkResult<List<BeautyTip>> result) {
//                List<BeautyTip> tip = result.getResult();
//            }
//
//            @Override
//            public void onFail(NetworkRequest<NetworkResult<List<BeautyTip>>> request, int errorCode, String errorMessage, Throwable e) {
//                Toast.makeText(BeautyTipDetailActivity.this, "fail", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
