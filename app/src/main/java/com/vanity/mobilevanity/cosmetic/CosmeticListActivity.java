package com.vanity.mobilevanity.cosmetic;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.CosmeticAdapter;
import com.vanity.mobilevanity.data.Brand;
import com.vanity.mobilevanity.data.Constant;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.Product;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.register.RegisterBarcodeActivity;
import com.vanity.mobilevanity.request.CosmeticListRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CosmeticListActivity extends AppCompatActivity {
    @BindView(R.id.tabs)
    TabLayout tabs;

    TabHost tabHost;

    @BindView(R.id.rv_cosmetic)
    RecyclerView listView;

    CosmeticAdapter mAdapter;
    long id;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int category = intent.getIntExtra(HomeFragment.TAG_CATEGORY, 0);

        mAdapter = new CosmeticAdapter();
        mAdapter.setOnAdapterItemClickListener(new CosmeticAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, Cosmetic data, int position) {
                Intent intent = new Intent(CosmeticListActivity.this, CosmeticDetailActivity.class);
                startActivity(intent);
            }
        });
        listView.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        init();

        initTab(category);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mAdapter.clear();
                init();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mAdapter.clear();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mAdapter.clear();
                init();
            }
        });

    }

    private void initTab(int category) {
        List<String> tabName = new ArrayList<>();

        switch (category) {
            case Constant.INDEX_CATEGORY_EYE:
                tabName.add("섀도우");
                tabName.add("마스카라");
                tabName.add("아이라이너");
                tabName.add("아이브로우");
                tabName.add("프라이머");

                break;

            case Constant.INDEX_CATEGORY_LIP: {
                tabName.add("립스틱");
                tabName.add("립글로스");
                tabName.add("립틴트");
                tabName.add("립케어");

                break;
            }
            case Constant.INDEX_CATEGORY_SKIN: {
                tabName.add("스킨/토너");
                tabName.add("로션/크림");
                tabName.add("에센스/세럼");
                tabName.add("선케어");
                break;
            }
            case Constant.INDEX_CATEGORY_FACE: {
                tabName.add("BB/파운데이션");
                tabName.add("파우더/팩트");
                tabName.add("프라이머/베이스");
                tabName.add("치크/하이라이터");
                break;
            }
            case Constant.INDEX_CATEGORY_CLEANSING: {
                tabName.add("클렌징");
                tabName.add("폼클렌징");
                break;
            }
            case Constant.INDEX_CATEGORY_TOOL: {
                tabName.add("브러쉬");
                tabName.add("퍼프");
                break;
            }
        }

        for (int i = 0; i < tabName.size(); i++) {
            tabs.addTab(tabs.newTab().setText(tabName.get(i)).setTag(tabName.get(i)));
        }

    }

    public void init() {
        Cosmetic cosmetic = new Cosmetic();
        Product product = new Product();
        Brand brand = new Brand();

        cosmetic.setColorName("Color Name");
        cosmetic.setColorCode("COLOR01");

        brand.setName("Brand");
        product.setBrand(brand);
        cosmetic.setProduct(product);

        product.setName("Product");
        cosmetic.setProduct(product);

        product.setUseBy(1);
        cosmetic.setProduct(product);

        mAdapter.add(cosmetic);
    }

    @OnClick(R.id.fab_add_cosmetic)
    public void onFloatingClick(View view) {
        Intent intent = new Intent(this, RegisterBarcodeActivity.class);
        startActivity(intent);
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
