package com.vanity.mobilevanity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.SearchResultAdapter;
import com.vanity.mobilevanity.data.Brand;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.data.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterSearchActivity extends AppCompatActivity {

    @BindView(R.id.rv_cosmetic)
    RecyclerView listView;

    SearchResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_search);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        resultAdapter = new SearchResultAdapter();
        resultAdapter.setOnAdapterItemClickListener(new SearchResultAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, Cosmetic data, int position) {
                Intent intent = new Intent(RegisterSearchActivity.this, RegisterDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });
        listView.setAdapter(resultAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        init();
    }

    private void init() {
        for (int i = 0; i < 10; i++) {
            Cosmetic cosmetic = new Cosmetic();
            Product product = new Product();
            Brand brand = new Brand();

            brand.setName("brand " + i);
            product.setBrand(brand);
            product.setName("product name " + i);
            cosmetic.setProduct(product);
            cosmetic.setColorCode("CODE" + i);
            cosmetic.setColorName("컬러 " + i);

            resultAdapter.add(cosmetic);
        }
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
                finish();
                return true;
            case android.R.id.home :
                Intent intent = new Intent(RegisterSearchActivity.this, RegisterBarcodeActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(RegisterSearchActivity.this, RegisterBarcodeActivity.class);
        startActivity(intent);
        finish();
    }
}
