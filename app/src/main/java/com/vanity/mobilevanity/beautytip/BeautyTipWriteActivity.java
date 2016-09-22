package com.vanity.mobilevanity.beautytip;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.BaseActivity;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.BeautyTipInfoRequest;
import com.vanity.mobilevanity.request.InsertBeautyTipRequest;
import com.vanity.mobilevanity.request.UpdateBeautyTipRequest;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeautyTipWriteActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_toolbar_title)
    TextView toolbarTitleView;

    @BindView(R.id.text_title)
    EditText titleView;

    @BindView(R.id.text_content)
    EditText contentView;

    @BindView(R.id.image_content)
    ImageView imageView;

    @BindView(R.id.btn_camera)
    ImageButton camera;

    @BindView(R.id.btn_set)
    Button writeButton;

    long id;
    private long lastClickTime = 0;

    public final static String TAG_SEARCH_TYPE = "searchtype";
    public final static int INDEX_TYPE_NONE = 0;
    public final static int INDEX_TYPE_DETAIL = 1;
    public final static int INDEX_TYPE_FRAGMENT = 2;

    private static final int RC_GET_IMAGE = 100;
    private final static int RC_PERMISSION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_tip_write);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        toolbarTitleView.setText(getResources().getString(R.string.toolbar_title_beauty_tip_write));
        toolbarTitleView.setTypeface(Typeface.createFromAsset(getAssets(), "NanumGothicBold.ttf"));
        writeButton.setText(R.string.button_text);

        Intent intent = getIntent();
        int mode = intent.getIntExtra(TAG_SEARCH_TYPE, 0);

        if (mode == INDEX_TYPE_DETAIL) {
            long id = intent.getLongExtra(BeautyTipDetailActivity.TAG_BEAUTY_TIP_ID, 0);

            if (id != 0) {
                BeautyTipInfoRequest request = new BeautyTipInfoRequest(BeautyTipWriteActivity.this, "" + id);
                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                        if (result.getCode() == 1) {
                            BeautyTip tip = result.getResult();

                            titleView.setText(tip.getTitle());
                            contentView.setText(tip.getContent());
                            Glide.with(BeautyTipWriteActivity.this).load(tip.getPreviewImage()).into(imageView);
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
            }
        }

        checkPermission();
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Runtime Permission");
                builder.setMessage("사진을 업로드하려면 앨범 접근 승인이 필요합니다.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermission();
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        finishNoPermission();
                    }
                });

                builder.create().show();
                return;
            }
            requestPermission();
        }
    }

    private void finishNoPermission() {
        Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_PERMISSION) {
            if (grantResults != null && grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "grant permission", Toast.LENGTH_SHORT).show();
            } else {
                finishNoPermission();
            }
        }
    }

    @OnClick(R.id.btn_camera)
    public void onCameraClick(View view) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
        lastClickTime = SystemClock.elapsedRealtime();

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, RC_GET_IMAGE);
    }

    File uploadFile = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GET_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);

                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    uploadFile = new File(path);

                    Glide.with(this)
                            .load(uploadFile)
                            .into(imageView);
                }
            }
        }
    }

    @OnClick(R.id.btn_set)
    public void onSetClick(View view) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
        lastClickTime = SystemClock.elapsedRealtime();

        if (TextUtils.isEmpty(titleView.getText().toString())) {
            Toast.makeText(BeautyTipWriteActivity.this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(contentView.getText().toString())) {
            Toast.makeText(BeautyTipWriteActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (uploadFile == null) {
            Toast.makeText(BeautyTipWriteActivity.this, "등록된 사진이 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = getIntent();
        int code = intent.getIntExtra(TAG_SEARCH_TYPE, 0);

        switch (code) {
            case INDEX_TYPE_NONE:
            default:
                Toast.makeText(BeautyTipWriteActivity.this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
                finish();
                return;

            case INDEX_TYPE_DETAIL:
                id = intent.getLongExtra(BeautyTipDetailActivity.TAG_BEAUTY_TIP_ID, 0);

                UpdateBeautyTipRequest request = new UpdateBeautyTipRequest(getBaseContext(), "" + id, titleView.getText().toString(), contentView.getText().toString(), uploadFile);
                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                        if (result.getCode() == 1) {
                            BeautyTip beautyTip = result.getResult();
                            titleView.setText(beautyTip.getTitle());
                            contentView.setText(beautyTip.getContent());
                            Glide.with(imageView.getContext())
                                    .load(uploadFile)
                                    .into(imageView);
                            finish();
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
                return;

            case INDEX_TYPE_FRAGMENT:
                InsertBeautyTipRequest beautyTipRequest = new InsertBeautyTipRequest(getBaseContext(), titleView.getText().toString(), contentView.getText().toString(), uploadFile);
                NetworkManager.getInstance().getNetworkData(beautyTipRequest, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                        if (result.getCode() == 1) {
                            BeautyTip tip = result.getResult();
                            finish();
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });
                return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return false;
        lastClickTime = SystemClock.elapsedRealtime();

        if (item.getItemId() == R.id.menu_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

