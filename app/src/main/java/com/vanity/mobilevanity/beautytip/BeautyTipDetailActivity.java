package com.vanity.mobilevanity.beautytip;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.BeautyTipAdapter;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.BeautyTipInfoRequest;
import com.vanity.mobilevanity.request.CommentListRequest;
import com.vanity.mobilevanity.request.DeleteBeautyTipRequest;
import com.vanity.mobilevanity.request.LikeBeautyTipListRequest;
import com.vanity.mobilevanity.request.SearchBeautyTipRequest;
import com.vanity.mobilevanity.request.UpdateBeautyTipRequest;
import com.vanity.mobilevanity.request.UpdateLikeRequest;
import com.vanity.mobilevanity.view.LikeViewHolder;

import java.io.File;
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

    Intent intent;
    long id;

    public static final String TAG_DETAIL = "detail";
    public static final String DETAIL_ID = "beautytipid";

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
        String like;
        if (likeButton.isPressed()) {
            likeButton.setBackgroundColor(Color.YELLOW);
            like = "false";
        } else likeButton.setBackgroundColor(Color.BLUE);
        like = "true";

        UpdateLikeRequest request = new UpdateLikeRequest(getBaseContext(), "" + id, like);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                BeautyTip beautyTip = result.getResult();
                // 여기 셋(again)
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(BeautyTipDetailActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_comment)
    public void onCommentClick() {
        FragmentManager fm = getSupportFragmentManager();
        BeautyTipCommentFragment dialog = new BeautyTipCommentFragment();
        Bundle args = new Bundle();
        args.putLong("commentid", id);
        dialog.setArguments(args);
        dialog.show(fm, "dialog");
    }

    File updateImage = null;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_cancel) {
            finish();
            return true;
        }

        if (item.getItemId() == R.id.menu_update) {
            Intent intent = new Intent(BeautyTipDetailActivity.this, BeautyTipWriteActivity.class);
            intent.putExtra(BeautyTipWriteActivity.TAG_SEARCH_TYPE, BeautyTipWriteActivity.INDEX_TYPE_DETAIL);
            intent.putExtra(DETAIL_ID, id);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.menu_delete) {
            DeleteBeautyTipRequest request = new DeleteBeautyTipRequest(getBaseContext(), "" + id);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                    BeautyTip beautyTip = result.getResult();
                    finish();
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {
                    Toast.makeText(BeautyTipDetailActivity.this, "fail", Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        intent = getIntent();
       id = intent.getLongExtra("beautytipid", 0);

        BeautyTipInfoRequest request = new BeautyTipInfoRequest(getBaseContext(), "" + id);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                if (result.getCode() == 1) {
                    BeautyTip beautyTip = result.getResult();

                    titleView.setText(beautyTip.getTitle());
                    contentView.setText(beautyTip.getContent());
                    Glide.with(beautytipImage.getContext())
                            .load(beautyTip.getPreviewImage())
                            .into(beautytipImage);
                } else {
                    Toast.makeText(BeautyTipDetailActivity.this, "삭제된 게시물입니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(BeautyTipDetailActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
