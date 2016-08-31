package com.vanity.mobilevanity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.cosmetic.CosmeticListActivity;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.InsertCosmeticItemRequest;
import com.vanity.mobilevanity.request.SearchCosmeticByBarcodeRequest;
import com.vanity.mobilevanity.request.SearchCosmeticListRequest;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterDetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.image_cosmetic)
    ImageView imageView;

    @BindView(R.id.text_brand_name)
    TextView brandView;

    @BindView(R.id.text_color_code)
    TextView colorCodeView;

    @BindView(R.id.text_color_name)
    TextView colorNameView;

    @BindView(R.id.text_cosmetic_name)
    TextView cosmeticView;

    @BindView(R.id.text_register_year)
    TextView registerYearView;

    @BindView(R.id.text_register_month)
    TextView registerMonthView;

    @BindView(R.id.text_register_day)
    TextView registerDayView;

    @BindView(R.id.text_useby_year)
    TextView usebyYearView;

    @BindView(R.id.text_useby_month)
    TextView usebyMonthView;

    @BindView(R.id.text_useby_day)
    TextView usebyDayView;

    Cosmetic cosmetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_detail);

        ButterKnife.bind(this);
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

    @OnClick(R.id.btn_edit_register)
    public void onEditClick(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                RegisterDetailActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DATE, dayOfMonth);

        registerYearView.setText(year + "");
        registerMonthView.setText((monthOfYear + 1) + "");
        registerDayView.setText(dayOfMonth + "");

        calendar.add(Calendar.DATE, cosmetic.getProduct().getUseBy());

        usebyYearView.setText(calendar.get(Calendar.YEAR) + "");
        usebyMonthView.setText((calendar.get(Calendar.MONTH) + 1) + "");
        usebyDayView.setText(calendar.get(Calendar.DATE) + "");
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick(View view) {
        if (cosmetic == null) {
            Toast.makeText(RegisterDetailActivity.this, "올바른 접근이 아닙니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(registerYearView.getText().toString()));
        calendar.set(Calendar.MONTH, Integer.parseInt(registerMonthView.getText().toString()) - 1);
        calendar.set(Calendar.DATE, Integer.parseInt(registerDayView.getText().toString()));
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSSZ");
        String parseDate = form.format(calendar.getTime());

        String id = cosmetic.getId() + "";
        String dateAdded = parseDate;
        String cosmeticTerm = cosmetic.getProduct().getUseBy() + "";

        InsertCosmeticItemRequest request = new InsertCosmeticItemRequest(this, id, dateAdded, cosmeticTerm);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<CosmeticItem>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<CosmeticItem>> request, NetworkResult<CosmeticItem> result) {
                if (result.getCode() == 1) {
                    Toast.makeText(RegisterDetailActivity.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<CosmeticItem>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(RegisterDetailActivity.this, errorCode + " : " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String barcode = intent.getStringExtra(RegisterBarcodeActivity.TAG_BARCODE);
        SearchCosmeticByBarcodeRequest request = new SearchCosmeticByBarcodeRequest(this, barcode);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<Cosmetic>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<Cosmetic>> request, NetworkResult<Cosmetic> result) {
                if (result == null)
                    return;

                if (result.getCode() == 1) {
                    cosmetic = result.getResult();
                    setViewByRequestResult(cosmetic);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<Cosmetic>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(RegisterDetailActivity.this, errorCode + " : " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setViewByRequestResult(Cosmetic cosmetic) {
        Glide.with(RegisterDetailActivity.this).load(cosmetic.getImage()).into(imageView);
        brandView.setText(cosmetic.getProduct().getBrand().getName());
        colorCodeView.setText(cosmetic.getColorName());
        //colorNameView.setText(cosmetic.getColorName());
        cosmeticView.setText(cosmetic.getProduct().getName());

        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        registerYearView.setText(calendar.get(Calendar.YEAR) + "");
        registerMonthView.setText((calendar.get(Calendar.MONTH) + 1) + "");
        registerDayView.setText(calendar.get(Calendar.DATE) + "");

        calendar.add(Calendar.DATE, cosmetic.getProduct().getUseBy());
        usebyYearView.setText(calendar.get(Calendar.YEAR) + "");
        usebyMonthView.setText((calendar.get(Calendar.MONTH) + 1) + "");
        usebyDayView.setText(calendar.get(Calendar.DATE) + "");
    }
}
