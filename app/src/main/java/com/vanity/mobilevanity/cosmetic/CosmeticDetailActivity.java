package com.vanity.mobilevanity.cosmetic;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.vanity.mobilevanity.sale.SaleFragment;

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

public class CosmeticDetailActivity extends AppCompatActivity {

    @BindView(R.id.text_brand)
    TextView brandView;

    @BindView(R.id.text_cosmetic_name)
    TextView cosmeticView;

    @BindView(R.id.image_cosmetic)
    ImageView cosmeticImage;

    @BindView(R.id.text_unit)
    TextView unitView;

    @BindView(R.id.text_register_year)
    TextView registerYearView;

    @BindView(R.id.text_register_month)
    TextView registerMonthView;

    @BindView(R.id.text_register_date)
    TextView registerDateView;

    @BindView(R.id.text_usebydate_year)
    TextView useByDateYearView;

    @BindView(R.id.text_usebydate_month)
    TextView useByDateMonthView;

    @BindView(R.id.text_usebydate_date)
    TextView useByDateDateView;

    @BindView(R.id.text_start_sale)
    TextView saleView;

    @BindView(R.id.text_capacity)
    TextView capacityView;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getLongExtra("productid", 0);

        CosmeticListRequest request = new CosmeticListRequest(getBaseContext(), "" + id);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<Cosmetic>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<Cosmetic>>> request, NetworkResult<List<Cosmetic>> result) {
                List<Cosmetic> cosmetics = result.getResult();
                Glide.with(cosmeticImage.getContext())
                        .load(cosmetics.get(0).getImage())
                        .into(cosmeticImage);
                cosmeticView.setText(cosmetics.get(0).getProduct().getName());
                brandView.setText(cosmetics.get(0).getProduct().getBrand().getName());
                capacityView.setText("" + cosmetics.get(0).getCapacity());
                unitView.setText("" + cosmetics.get(0).getUnit());
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<Cosmetic>>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(CosmeticDetailActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
        onSale(id);
    }

    public void onSale(long productid) {
        String type = SaleInfoRequest.TAG_TYPE_PRODUCT;
        SaleInfoRequest saleRequest = new SaleInfoRequest(getBaseContext(), type, "" + id);
        NetworkManager.getInstance().getNetworkData(saleRequest, new NetworkManager.OnResultListener<NetworkResult<List<Sale>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<Sale>>> request, NetworkResult<List<Sale>> result) {
                List<Sale> saleList = result.getResult();
                StringBuffer buffer = new StringBuffer();
                StringBuffer registerYearBuffer = new StringBuffer();
                StringBuffer registerMonthBuffer = new StringBuffer();
                StringBuffer registerDateBuffer = new StringBuffer();
                StringBuffer usebyYearBuffer = new StringBuffer();
                StringBuffer usebyMonthBuffer = new StringBuffer();

                SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSSZ");

                try {
                    Calendar startDate = Calendar.getInstance();
                    startDate.setTime(form.parse(saleList.get(0).getStartDay()));

                    Calendar endDate = Calendar.getInstance();
                    endDate.setTime(form.parse(saleList.get(0).getEndDay()));

                    String start = startDate.get(Calendar.YEAR) + "/" + startDate.get(Calendar.MONTH) + "/" + startDate.get(Calendar.DATE);
                    String end = endDate.get(Calendar.YEAR) + "/" + endDate.get(Calendar.MONTH) + "/" + endDate.get(Calendar.DATE);
                    String registerYear = startDate.get(Calendar.YEAR) + "";
                    String registerMonth = startDate.get(Calendar.MONTH) + "";
                    String registerDate = startDate.get(Calendar.DATE) + "";
                    String usebyYear = startDate.get(Calendar.YEAR) + 1 + "";
                    String usebyMonth = startDate.get(Calendar.MONTH) + 6 + "";

                    buffer.append(" 세일 : " + start + " ~ " + end + "\n");
                    registerYearBuffer.append(registerYear);
                    registerMonthBuffer.append(registerMonth);
                    registerDateBuffer.append(registerDate);
                    usebyYearBuffer.append(usebyYear);
                    usebyMonthBuffer.append(usebyMonth);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                saleView.setText(buffer);
                registerYearView.setText(registerYearBuffer);
                registerMonthView.setText(registerMonthBuffer);
                registerDateView.setText(registerDateBuffer);
                useByDateYearView.setText(usebyYearBuffer);
                useByDateMonthView.setText(usebyMonthBuffer);
                useByDateDateView.setText(registerDateBuffer);
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<Sale>>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(CosmeticDetailActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.text_start_sale)
    public void onSaleClick() {
        Intent intent = new Intent(CosmeticDetailActivity.this, MainActivity.class);
        intent.putExtra("saletab", 3);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.image_register)
    public void onRegisterButtonClick(View view) {

    }

    @OnClick(R.id.image_usebydate)
    public void onUseByDateButton(View view) {

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
}
