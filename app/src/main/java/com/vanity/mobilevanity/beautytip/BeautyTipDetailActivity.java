package com.vanity.mobilevanity.beautytip;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.BaseActivity;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.manager.PropertyManager;
import com.vanity.mobilevanity.request.BeautyTipInfoRequest;
import com.vanity.mobilevanity.request.DeleteBeautyTipRequest;
import com.vanity.mobilevanity.request.MyInfoRequest;
import com.vanity.mobilevanity.request.UpdateLikeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BeautyTipDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_toolbar_title)
    TextView toolbarTitleView;

    @BindView(R.id.text_title)
    TextView titleView;

    @BindView(R.id.text_content)
    TextView contentView;

    @BindView(R.id.image_beauty_tip)
    ImageView beautytipImage;

    @BindView(R.id.btn_like)
    ImageButton likeButton;

    @BindView(R.id.btn_comment)
    ImageButton commentButton;

    long beautyTipId = 0;
    boolean like = false;
    User user;

    private long lastClickTime = 0;

    public static final String TAG_BEAUTY_TIP_ID = "beautytipid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_tip_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitleView.setText(getResources().getString(R.string.toolbar_title_beauty_tip_detail));
        toolbarTitleView.setTypeface(Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf"));

        toolbar.setNavigationIcon(R.drawable.btn_before);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
                lastClickTime = SystemClock.elapsedRealtime();

                finish();
            }
        });

        Intent intent = getIntent();
        beautyTipId = intent.getLongExtra(TAG_BEAUTY_TIP_ID, 0);

        getBeautyTipInfo();
    }

    MenuItem updateMenuItem;
    MenuItem deleteMenuItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);

        updateMenuItem = menu.findItem(R.id.menu_update);
        deleteMenuItem = menu.findItem(R.id.menu_delete);

        updateMenuItem.setVisible(false);
        deleteMenuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return false;
        lastClickTime = SystemClock.elapsedRealtime();

        switch (item.getItemId()) {
            case R.id.menu_update:
                Intent intent = new Intent(BeautyTipDetailActivity.this, BeautyTipWriteActivity.class);
                intent.putExtra(BeautyTipWriteActivity.TAG_SEARCH_TYPE, BeautyTipWriteActivity.INDEX_TYPE_DETAIL);
                intent.putExtra(TAG_BEAUTY_TIP_ID, beautyTipId);
                startActivity(intent);
                return true;
            case R.id.menu_delete:
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

    @OnClick(R.id.btn_like)
    public void onLikeClick() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
        lastClickTime = SystemClock.elapsedRealtime();

        String isLike;
        if (like) isLike = "false";
        else isLike = "true";

        UpdateLikeRequest request = new UpdateLikeRequest(getBaseContext(), "" + beautyTipId, isLike);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                if (result.getCode()==1) {
                    BeautyTip tip = result.getResult();
                    like = tip.isLike();

                    if (like)
                        likeButton.setImageResource(R.drawable.btn_like_default);
                    else likeButton.setImageResource(R.drawable.btn_like_default_gray);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {
            }
        });
    }

    @OnClick(R.id.btn_comment)
    public void onCommentClick() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
        lastClickTime = SystemClock.elapsedRealtime();

        MyInfoRequest request = new MyInfoRequest(BeautyTipDetailActivity.this);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                if (result.getCode() == 1) {
                    user = result.getResult();

                    FragmentManager fm = getSupportFragmentManager();
                    BeautyTipCommentFragment dialog = new BeautyTipCommentFragment();

                    Bundle args = new Bundle();
                    args.putLong(TAG_BEAUTY_TIP_ID, beautyTipId);
                    args.putString(BeautyTipFragment.TAG_USER_PROFILE, user.getUserProfile());
                    args.putString(BeautyTipFragment.TAG_USER_NICKNAME, user.getUserNickName());

                    dialog.setArguments(args);
                    dialog.show(fm, BeautyTipFragment.TAG_COMMENT);
                }

            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }

    private void getBeautyTipInfo() {
        BeautyTipInfoRequest request = new BeautyTipInfoRequest(BeautyTipDetailActivity.this, "" + beautyTipId);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                if (result.getCode() == 1) {
                    BeautyTip beautyTip = result.getResult();

                    titleView.setText(beautyTip.getTitle());
                    titleView.setTypeface(Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf"));
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

                    if (like)
                        likeButton.setImageResource(R.drawable.btn_like_default);
                    else likeButton.setImageResource(R.drawable.btn_like_default_gray);

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
