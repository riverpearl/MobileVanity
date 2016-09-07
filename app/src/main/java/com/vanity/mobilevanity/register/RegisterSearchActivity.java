package com.vanity.mobilevanity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vanity.mobilevanity.MainActivity;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.SearchResultAdapter;
import com.vanity.mobilevanity.cosmetic.CosmeticListActivity;
import com.vanity.mobilevanity.cosmetic.HomeFragment;
import com.vanity.mobilevanity.data.Brand;
import com.vanity.mobilevanity.data.Constant;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.Product;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.BrandListRequest;
import com.vanity.mobilevanity.request.SearchCosmeticListRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterSearchActivity extends AppCompatActivity {

    @BindView(R.id.spinner_brand)
    Spinner brandView;

    @BindView(R.id.spinner_category)
    Spinner categoryView;

    @BindView(R.id.spinner_item)
    Spinner itemView;

    @BindView(R.id.edit_keyword)
    EditText keywordView;

    @BindView(R.id.rv_cosmetic)
    RecyclerView listView;

    @BindView(R.id.text_guide)
    TextView guideView;

    SearchResultAdapter resultAdapter;
    ArrayAdapter<String> brandAdapter;
    ArrayAdapter<String> categoryAdapter;
    ArrayAdapter<String> itemAdapter;
    List<Brand> brands;

    private int requestCode = 0;
    private int category = 0;
    private int tabpos = -1;

    public final static String TAG_REQUEST_CODE = "requestcode";
    public final static String TAG_COSMETIC_ID = "cid";
    public final static String TAG_IMAGE = "image";
    public final static String TAG_BRAND = "brand";
    public final static String TAG_COLOR_CODE = "colorcode";
    public final static String TAG_COLOR_NAME = "colorname";
    public final static String TAG_NAME = "name";
    public final static String TAG_USEBY = "useby";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_search);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        requestCode = intent.getIntExtra(TAG_REQUEST_CODE, 0);
        category = intent.getIntExtra(CosmeticListActivity.TAG_CATEGORY, 0);
        tabpos = intent.getIntExtra(CosmeticListActivity.TAG_TAB_POS, 0);

        brandView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchRequest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        categoryView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setItemView(position);
                searchRequest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        itemView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchRequest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        keywordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchRequest();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        resultAdapter = new SearchResultAdapter();
        resultAdapter.setOnAdapterItemClickListener(new SearchResultAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, Cosmetic data, int position) {
                Intent intent = new Intent(RegisterSearchActivity.this, RegisterDetailActivity.class);
                intent.putExtra(RegisterDetailActivity.TAG_REQUEST_CODE, requestCode);

                if (category != 0 && tabpos != -1) {
                    intent.putExtra(CosmeticListActivity.TAG_CATEGORY, category);
                    intent.putExtra(CosmeticListActivity.TAG_TAB_POS, tabpos);
                }

                intent.putExtra(RegisterDetailActivity.TAG_SEARCH_TYPE, RegisterDetailActivity.INDEX_TYPE_SEARCH);
                intent.putExtra(TAG_COSMETIC_ID, data.getId());
                intent.putExtra(TAG_IMAGE, data.getImage());
                intent.putExtra(TAG_COLOR_CODE, data.getColorCode());
                intent.putExtra(TAG_COLOR_NAME, data.getColorName());
                intent.putExtra(TAG_NAME, data.getProduct().getName());
                intent.putExtra(TAG_BRAND, data.getProduct().getBrand().getName());
                intent.putExtra(TAG_USEBY, data.getProduct().getUseBy());
                startActivity(intent);
            }
        });
        listView.setAdapter(resultAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cancel :
                if (requestCode == HomeFragment.KEY_HOME) {
                    Intent intent = new Intent(RegisterSearchActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if (requestCode == CosmeticListActivity.KEY_COSMETIC_LIST) {
                    Intent intent = new Intent(RegisterSearchActivity.this, CosmeticListActivity.class);
                    intent.putExtra(CosmeticListActivity.TAG_CATEGORY, category);
                    intent.putExtra(CosmeticListActivity.TAG_TAB_POS, tabpos);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return true;
            case android.R.id.home :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        BrandListRequest request = new BrandListRequest(this);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<Brand>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<Brand>>> request, NetworkResult<List<Brand>> result) {
                if (result.getCode() == 1) {
                    brands = result.getResult();
                    List<String> brandList = new ArrayList<String>();
                    brandList.add("브랜드");

                    for (int i = 0; i < brands.size(); i++) {
                        brandList.add(brands.get(i).getName());
                    }

                    brandAdapter = new ArrayAdapter<String>(RegisterSearchActivity.this, android.R.layout.simple_list_item_1, brandList);
                    brandView.setAdapter(brandAdapter);

                    setCategoryView();
                    setItemView(Constant.INDEX_CATEGROY_NONE);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<Brand>>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(RegisterSearchActivity.this, errorCode + " : " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCategoryView() {
        String[] categoryList = getResources().getStringArray(R.array.categories);
        categoryAdapter = new ArrayAdapter<String>(RegisterSearchActivity.this, android.R.layout.simple_list_item_1, categoryList);
        categoryView.setAdapter(categoryAdapter);
    }

    private void setItemView(int category) {
        String[] itemList;

        switch (category) {
            case Constant.INDEX_CATEGROY_NONE :
            default :
                itemList = new String[] { "품목" };
                break;
            case Constant.INDEX_CATEGORY_EYE :
                itemList = getResources().getStringArray(R.array.items_eye);
                break;
            case Constant.INDEX_CATEGORY_LIP :
                itemList = getResources().getStringArray(R.array.items_lips);
                break;
            case Constant.INDEX_CATEGORY_SKIN :
                itemList = getResources().getStringArray(R.array.items_skin);
                break;
            case Constant.INDEX_CATEGORY_FACE :
                itemList = getResources().getStringArray(R.array.items_face);
                break;
            case Constant.INDEX_CATEGORY_CLEANSING :
                itemList = getResources().getStringArray(R.array.items_cleansing);
                break;
            case Constant.INDEX_CATEGORY_TOOL :
                itemList = getResources().getStringArray(R.array.items_tool);
                break;
        }

        itemAdapter = new ArrayAdapter<String>(RegisterSearchActivity.this, android.R.layout.simple_list_item_1, itemList);
        itemView.setAdapter(itemAdapter);
    }

    private void searchRequest() {
        String brandid;
        String category;
        String item;
        String keyword = keywordView.getText().toString();

        int brandPos = brandView.getSelectedItemPosition();
        int categoryPos = categoryView.getSelectedItemPosition();
        int itemPos = itemView.getSelectedItemPosition();

        if (brandPos == 0 && categoryPos == 0 && itemPos == 0 && TextUtils.isEmpty(keyword)) {
            resultAdapter.clear();
            return;
        }

        if (brandPos == 0) brandid = "";
        else brandid = (brands.get(brandPos - 1).getId()) + "";

        if (categoryPos == 0) category = "";
        else category = categoryPos + "";

        if (itemPos == 0) item = "";
        else item = itemPos + "";

        if (TextUtils.isEmpty(keyword)) keyword = "";

        SearchCosmeticListRequest request = new SearchCosmeticListRequest(this, brandid, category, item, keyword);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<Cosmetic>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<Cosmetic>>> request, NetworkResult<List<Cosmetic>> result) {
                if (result.getCode() == 1) {
                    List<Cosmetic> cosmetics = result.getResult();
                    resultAdapter.clear();
                    resultAdapter.addAll(cosmetics);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<Cosmetic>>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(RegisterSearchActivity.this, errorCode + " : " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
