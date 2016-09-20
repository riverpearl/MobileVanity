package com.vanity.mobilevanity.register;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.vanity.mobilevanity.BaseActivity;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.cosmetic.CosmeticListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class RegisterBarcodeActivity extends BaseActivity implements ZXingScannerView.ResultHandler {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_toolbar_title)
    TextView toolbarTitleView;

    @BindView(R.id.view_scanner)
    ZXingScannerView scannerView;

    @BindView(R.id.btn_register_myself)
    Button registerButton;

    public final static String TAG_BARCODE = "barcode";
    private final static int RC_PERMISSION = 500;

    private int requestCode = 0;
    private int category = 0;
    private int tabpos = -1;

    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_barcode);

        ButterKnife.bind(this);
        checkPermission();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitleView.setText(getResources().getString(R.string.toolbar_title_register_barcode));
        registerButton.setText(R.string.activity_register_barcode_button_text);

        Intent intent = getIntent();
        requestCode = intent.getIntExtra(RegisterSearchActivity.TAG_REQUEST_CODE, 0);
        category = intent.getIntExtra(CosmeticListActivity.TAG_CATEGORY, 0);
        tabpos = intent.getIntExtra(CosmeticListActivity.TAG_TAB_POS, -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Runtime Permission");
                builder.setMessage("바코드를 읽으려면 카메라 접근 승인이 필요합니다.");
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
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, RC_PERMISSION);
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

    @OnClick(R.id.btn_register_myself)
    public void onRegisterMyselfClick(View view) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
        lastClickTime = SystemClock.elapsedRealtime();

        Intent intent = new Intent(RegisterBarcodeActivity.this, RegisterSearchActivity.class);
        intent.putExtra(RegisterSearchActivity.TAG_REQUEST_CODE, requestCode);

        if (category != 0 && tabpos != -1) {
            intent.putExtra(CosmeticListActivity.TAG_CATEGORY, category);
            intent.putExtra(CosmeticListActivity.TAG_TAB_POS, tabpos);
        }

        startActivity(intent);
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent(RegisterBarcodeActivity.this, RegisterDetailActivity.class);
        intent.putExtra(RegisterDetailActivity.TAG_REQUEST_CODE, requestCode);
        intent.putExtra(RegisterDetailActivity.TAG_SEARCH_TYPE, RegisterDetailActivity.INDEX_TYPE_BARCODE);
        intent.putExtra(TAG_BARCODE, "1234567890");
        startActivity(intent);
    }
}
