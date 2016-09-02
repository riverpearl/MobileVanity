package com.vanity.mobilevanity.cosmetic;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.MainActivity;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.Product;
import com.vanity.mobilevanity.data.Sale;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.CosmeticItemsRequest;
import com.vanity.mobilevanity.request.CosmeticListRequest;
import com.vanity.mobilevanity.request.ProductListRequest;
import com.vanity.mobilevanity.request.SaleInfoRequest;
import com.vanity.mobilevanity.request.UpdateCosmeticItemRequest;
import com.vanity.mobilevanity.sale.SaleFragment;
import com.vanity.mobilevanity.util.DateCalculator;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CosmeticDetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.text_brand_name)
    TextView brandView;

    @BindView(R.id.text_color_code)
    TextView colorCodeView;

    @BindView(R.id.text_color_name)
    TextView colorNameView;

    @BindView(R.id.text_cosmetic_name)
    TextView cosmeticView;

    @BindView(R.id.image_cosmetic)
    ImageView cosmeticImage;

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

    @BindView(R.id.text_sale_info)
    TextView saleView;

    @BindView(R.id.btn_update)
    Button updateView;

    @BindView(R.id.btn_cancel)
    Button cancelView;

    private Intent intent;

    public static final String TAG_COSMETIC_ITEM_ID = "cosmeticitemid";
    public static final String TAG_COSMETIC_ID = "cosmeticid";
    public static final String TAG_PRODUCT_ID = "productid";
    public static final String TAG_IMAGE = "image";
    public static final String TAG_BRAND = "brandname";
    public static final String TAG_COLOR_CODE = "colorcode";
    public static final String TAG_COLOR_NAME = "colorname";
    public static final String TAG_NAME = "name";
    public static final String TAG_REG_DATE = "regdate";
    public static final String TAG_USEBY = "useby";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_detail);
        ButterKnife.bind(this);

        intent = getIntent();
        setCosmeticView();
        setDateView(intent.getStringExtra(TAG_REG_DATE), intent.getIntExtra(TAG_USEBY, 0));
        getSaleInfoList(intent.getLongExtra(TAG_PRODUCT_ID , 0));

        updateView.setVisibility(View.GONE);
        cancelView.setVisibility(View.GONE);
    }

    public void getSaleInfoList(long productid) {
        if (productid == 0)
            return;

        String type = SaleInfoRequest.TAG_TYPE_PRODUCT;
        SaleInfoRequest saleRequest = new SaleInfoRequest(getBaseContext(), type, "" + productid);
        NetworkManager.getInstance().getNetworkData(saleRequest, new NetworkManager.OnResultListener<NetworkResult<List<Sale>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<Sale>>> request, NetworkResult<List<Sale>> result) {
                if (result.getCode() == 1) {
                    List<Sale> sales = result.getResult();
                    DateCalculator calculator = new DateCalculator();
                    StringBuffer buffer = new StringBuffer();

                    for (int i = 0; i < sales.size(); i++) {
                        Calendar startDay = calculator.StrToCal(sales.get(i).getStartDay());
                        Calendar endDay = calculator.StrToCal(sales.get(i).getEndDay());

                        String title = sales.get(i).getTitle();
                        String start = startDay.get(Calendar.YEAR) + "/" + startDay.get(Calendar.MONTH) + "/" + startDay.get(Calendar.DATE);
                        String end = endDay.get(Calendar.YEAR) + "/" + endDay.get(Calendar.MONTH) + "/" + endDay.get(Calendar.DATE);

                        buffer.append(title + " : " + start + " ~ " + end + "\n");
                    }

                    saleView.setText(buffer.toString());
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<Sale>>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(CosmeticDetailActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.text_sale_info)
    public void onSaleClick() {
        Intent intent = new Intent(CosmeticDetailActivity.this, MainActivity.class);
        intent.putExtra("saletab", 3);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.btn_edit_register)
    public void onEditRegisterClick(View view) {
        updateView.setVisibility(View.VISIBLE);
        cancelView.setVisibility(View.VISIBLE);

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                CosmeticDetailActivity.this,
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

        calendar.add(Calendar.DATE, intent.getIntExtra(TAG_USEBY, 0));
        usebyYearView.setText(calendar.get(Calendar.YEAR) + "");
        usebyMonthView.setText((calendar.get(Calendar.MONTH) + 1) + "");
        usebyDayView.setText(calendar.get(Calendar.DATE) + "");
    }

    @OnClick(R.id.btn_update)
    public void onUpdateClick(View view) {
        long ciid = intent.getLongExtra(TAG_COSMETIC_ITEM_ID, 0);
        long cid = intent.getLongExtra(TAG_COSMETIC_ID, 0);
        String dateAdded = intent.getStringExtra(TAG_REG_DATE);
        int term = intent.getIntExtra(TAG_USEBY, 0);

        UpdateCosmeticItemRequest request = new UpdateCosmeticItemRequest(this, ciid + "", cid + "", dateAdded, term + "");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<CosmeticItem>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<CosmeticItem>> request, NetworkResult<CosmeticItem> result) {
                if (result.getCode() == 1) {
                    updateView.setVisibility(View.GONE);
                    cancelView.setVisibility(View.GONE);
                    Toast.makeText(CosmeticDetailActivity.this, "수정되었습니다.", Toast.LENGTH_SHORT).show();

                    CosmeticItem item = result.getResult();
                    setDateView(item.getDateAdded(), item.getCosmeticTerm());
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<CosmeticItem>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(CosmeticDetailActivity.this, errorCode + " : " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_cancel)
    public void onCancelClick(View view) {
        updateView.setVisibility(View.GONE);
        cancelView.setVisibility(View.GONE);
        setDateView(intent.getStringExtra(TAG_REG_DATE), intent.getIntExtra(TAG_USEBY, 0));
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

    private void setCosmeticView() {
        Glide.with(this).load(intent.getStringExtra(TAG_IMAGE)).into(cosmeticImage);
        brandView.setText(intent.getStringExtra(TAG_BRAND));
        colorCodeView.setText(intent.getStringExtra(TAG_COLOR_CODE));
        colorNameView.setText(intent.getStringExtra(TAG_COLOR_NAME));
        cosmeticView.setText(intent.getStringExtra(TAG_NAME));
    }

    private void setDateView(String date, int term) {
        DateCalculator calculator = new DateCalculator();
        Calendar regDate = calculator.StrToCal(date);
        registerYearView.setText(regDate.get(Calendar.YEAR) + "");
        registerMonthView.setText((regDate.get(Calendar.MONTH) + 1) + "");
        registerDayView.setText(regDate.get(Calendar.DATE) + "");

        regDate.add(Calendar.DATE, term);
        usebyYearView.setText(regDate.get(Calendar.YEAR) + "");
        usebyMonthView.setText((regDate.get(Calendar.MONTH) + 1) + "");
        usebyDayView.setText(regDate.get(Calendar.DATE) + "");
    }
}
