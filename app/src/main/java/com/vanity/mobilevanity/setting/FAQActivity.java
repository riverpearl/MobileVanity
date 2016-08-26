package com.vanity.mobilevanity.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.FAQAdapter;
import com.vanity.mobilevanity.data.FAQ;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FAQActivity extends AppCompatActivity {

    @BindView(R.id.rv_faq)
    RecyclerView listView;

    FAQAdapter faqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        ButterKnife.bind(this);

        init();
        listView.setAdapter(faqAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);
    }

    private void init() {
        List<FAQ> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            List<String> content = new ArrayList<>();
            content.add("content " + i);

            FAQ faq = new FAQ();
            faq.setTitle("title " + i);
            faq.setChild(content);

            list.add(faq);
        }

        faqAdapter = new FAQAdapter(this, list);
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
