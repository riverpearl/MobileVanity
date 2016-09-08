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
import android.os.Handler;
import android.os.Message;
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
import android.util.Log;
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
import com.vanity.mobilevanity.data.User;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.manager.PropertyManager;
import com.vanity.mobilevanity.request.BeautyTipInfoRequest;
import com.vanity.mobilevanity.request.CommentListRequest;
import com.vanity.mobilevanity.request.DeleteBeautyTipRequest;
import com.vanity.mobilevanity.request.LikeBeautyTipListRequest;
import com.vanity.mobilevanity.request.MyInfoRequest;
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

    long beautyTipId = 0;
    boolean like = false;

    public static final String TAG_BEAUTY_TIP_ID = "beautytipid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_tip_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        beautyTipId = intent.getLongExtra(TAG_BEAUTY_TIP_ID, 0);

        getBeautyTipInfo();
    }

    MenuItem updateMenuItem;
    MenuItem deleteMenuItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_update, menu);
        getMenuInflater().inflate(R.menu.actionbar_delete, menu);
        getMenuInflater().inflate(R.menu.actionbar_cancel, menu);

        updateMenuItem = menu.findItem(R.id.menu_update);
        deleteMenuItem = menu.findItem(R.id.menu_delete);

        updateMenuItem.setVisible(false);
        deleteMenuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.btn_like)
    public void onLikeClick() {
        Message msg = likeHandler.obtainMessage(0);
        likeHandler.removeMessages(0);
        likeHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler likeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String isLike;
            if (like) isLike = "false";
            else isLike = "true";

            UpdateLikeRequest request = new UpdateLikeRequest(getBaseContext(), "" + beautyTipId, isLike);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {
                }
            });
        }
    };


    @OnClick(R.id.btn_comment)
    public void onCommentClick() {
        Message msg = commentHandler.obtainMessage(0);
        commentHandler.removeMessages(0);
        commentHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler commentHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FragmentManager fm = getSupportFragmentManager();
            BeautyTipCommentFragment dialog = new BeautyTipCommentFragment();
            Bundle args = new Bundle();
            args.putLong("commentid", beautyTipId);
            dialog.setArguments(args);
            dialog.show(fm, "dialog");
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cancel :
                finish();
                return true;
            case R.id.menu_update :
                Intent intent = new Intent(BeautyTipDetailActivity.this, BeautyTipWriteActivity.class);
                intent.putExtra(BeautyTipWriteActivity.TAG_SEARCH_TYPE, BeautyTipWriteActivity.INDEX_TYPE_DETAIL);
                intent.putExtra(TAG_BEAUTY_TIP_ID, beautyTipId);
                startActivity(intent);
                return true;
            case R.id.menu_delete :
                DeleteBeautyTipRequest request = new DeleteBeautyTipRequest(getBaseContext(), "" + beautyTipId);
                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                        if (result.getCode() == 1) {
                            Toast.makeText(BeautyTipDetailActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {
                    }
                });
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void getBeautyTipInfo() {
        BeautyTipInfoRequest request = new BeautyTipInfoRequest(BeautyTipDetailActivity.this, "" + beautyTipId);
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
                    like = beautyTip.isLike();

                    if (PropertyManager.getInstance().getUserId() == beautyTip.getUser().getId()) {
                        updateMenuItem.setVisible(true);
                        deleteMenuItem.setVisible(true);
                    } else {
                        updateMenuItem.setVisible(false);
                        deleteMenuItem.setVisible(false);
                    }
                } else {
                    Toast.makeText(BeautyTipDetailActivity.this, "삭제된 게시물입니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {
            }
        });
    }
}
