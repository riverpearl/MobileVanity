package com.vanity.mobilevanity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.manager.PropertyManager;
import com.vanity.mobilevanity.request.FacebookSignupRequest;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FacebookSignUpActivity extends AppCompatActivity {

    @BindView(R.id.image_profile)
    ImageView profileView;

    @BindView(R.id.edit_nickname)
    EditText nicknameView;

    @BindView(R.id.group_select_gender)
    RadioGroup groupGenderView;

    @BindView(R.id.group_select_skin_type)
    RadioGroup groupSkinTypeView;

    @BindView(R.id.group_select_skin_tone)
    RadioGroup groupSkinToneView;

    private File profile;
    private String facebookId;
    private String email;

    public final static String TAG_EMAIL = "email";
    public final static String TAG_FACEBOOK_ID = "facebookId";
    private final static int RC_GET_IMAGE = 100;
    private final static int RC_PERMISSION = 500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_sign_up);

        ButterKnife.bind(this);
        checkPermission();

        Intent intent = getIntent();
        facebookId = intent.getStringExtra(TAG_FACEBOOK_ID);
        email = intent.getStringExtra(TAG_EMAIL);
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

    @OnClick(R.id.image_profile)
    public void onProfileClick(View view) {
        Message msg = imageProfileHandler.obtainMessage(0);
        imageProfileHandler.removeMessages(0);
        imageProfileHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler imageProfileHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, RC_GET_IMAGE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GET_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                c.moveToFirst();

                String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                profile = new File(path);
                Glide.with(this).load(profile).into(profileView);
            }
        }
    }

    @OnClick(R.id.btn_sign_up)
    public void onSignUpClick(View view) {
        Message msg = signUpHandler.obtainMessage(0);
        signUpHandler.removeMessages(0);
        signUpHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler signUpHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (profile == null)
                return; // default 프로필 이미지를 넣어준다

            String nickname = nicknameView.getText().toString();
            String gender = getGroupGenderView() + "";
            String skinType = getGroupSkinTypeView() + "";
            String skinTone = getGroupSkinToneView() + "";

            if (!TextUtils.isEmpty(nickname)) {
                FacebookSignupRequest request = new FacebookSignupRequest(FacebookSignUpActivity.this, nickname, email, skinType, skinTone, gender, profile);
                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                        if (result.getCode() == 1) {
                            PropertyManager.getInstance().setFacebookId(facebookId);
                            setResult(Activity.RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(FacebookSignUpActivity.this, result.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                        Toast.makeText(FacebookSignUpActivity.this, "sign up fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    private int getGroupGenderView() {
        switch (groupGenderView.getCheckedRadioButtonId()) {
            case R.id.radio_male:
            default:
                return 1;
            case R.id.radio_female:
                return 2;
        }
    }

    private int getGroupSkinTypeView() {
        switch (groupSkinTypeView.getCheckedRadioButtonId()) {
            case R.id.radio_dry:
            default:
                return 1;
            case R.id.radio_normal:
                return 2;
            case R.id.radio_oily:
                return 3;
            case R.id.radio_complex:
                return 4;
        }
    }

    private int getGroupSkinToneView() {
        switch (groupSkinToneView.getCheckedRadioButtonId()) {
            case R.id.radio_13:
            default:
                return 1;
            case R.id.radio_21:
                return 2;
            case R.id.radio_23:
                return 3;
        }
    }
}
