package com.vanity.mobilevanity.beautytip;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.InsertBeautyTipRequest;
import com.vanity.mobilevanity.request.UpdateBeautyTipRequest;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeautyTipWriteActivity extends AppCompatActivity {

    @BindView(R.id.text_title)
    EditText titleView;

    @BindView(R.id.text_content)
    EditText contentView;

    @BindView(R.id.image_content)
    ImageView imageView;

    Intent intent;
    long id;

    public final static String TAG_SEARCH_TYPE = "searchtype";
    public final static int INDEX_TYPE_NONE = 0;
    public final static int INDEX_TYPE_DETAIL = 1;
    public final static int INDEX_TYPE_FRAGMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_tip_write);
        ButterKnife.bind(this);

        Button camera = (Button) findViewById(R.id.btn_camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, RC_GET_IMAGE);
            }
        });
    }

    private static final int RC_GET_IMAGE = 1;


    @OnClick(R.id.btn_set)
    public void onSetClick(View view) {

        Intent intent = getIntent();
        int code = intent.getIntExtra(TAG_SEARCH_TYPE, 0);

        switch (code) {
            case INDEX_TYPE_NONE:
            default:
                Toast.makeText(BeautyTipWriteActivity.this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
                finish();
                return;

            case INDEX_TYPE_DETAIL:
                id = intent.getLongExtra(BeautyTipDetailActivity.DETAIL_ID, 0);
                UpdateBeautyTipRequest request = new UpdateBeautyTipRequest(getBaseContext(), "" + id, titleView.getText().toString(), contentView.getText().toString(), uploadFile);
                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                        BeautyTip beautyTip = result.getResult();
                        titleView.setText(beautyTip.getTitle());
                        contentView.setText(beautyTip.getContent());
                        Glide.with(imageView.getContext())
                                .load(uploadFile)
                                .into(imageView);
                        finish();
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {
                        Toast.makeText(BeautyTipWriteActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                });
                return;

            case INDEX_TYPE_FRAGMENT:
                InsertBeautyTipRequest beautyTipRequest = new InsertBeautyTipRequest(getBaseContext(), titleView.getText().toString(), contentView.getText().toString(), uploadFile);
                NetworkManager.getInstance().getNetworkData(beautyTipRequest, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                        BeautyTip tip = result.getResult();
                        finish();
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {
                        Toast.makeText(BeautyTipWriteActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                });

        }

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

    @Override
    protected void onStart() {
        super.onStart();


    }
}

