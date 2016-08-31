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
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.FAQListRequest;

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

        FAQListRequest request = new FAQListRequest(this);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<FAQ>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<FAQ>>> request, NetworkResult<List<FAQ>> result) {
                if (result.getCode() == 1) {
                    List<FAQ> faqs = result.getResult();
                    List<FAQ> list = new ArrayList<>();

                    for (int i = 0; i < faqs.size(); i++) {
                        List<String> content = new ArrayList<>();
                        content.add(faqs.get(i).getContent());

                        FAQ faq = new FAQ();
                        faq.setTitle(faqs.get(i).getTitle());
                        faq.setChild(content);

                        list.add(faq);
                    }

                    faqAdapter = new FAQAdapter(FAQActivity.this, list);
                    listView.setAdapter(faqAdapter);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<FAQ>>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });

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
        if (item.getItemId() == R.id.menu_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
