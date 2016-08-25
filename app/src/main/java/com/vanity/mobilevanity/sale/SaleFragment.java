package com.vanity.mobilevanity.sale;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.BannerAdapter;
import com.vanity.mobilevanity.data.BannerData;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaleFragment extends Fragment {

    public SaleFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.text_sale_info)
    TextView saleInfoView;

    @BindView(R.id.rv_sale_banner)
    RecyclerView bannerListView;

    BannerAdapter bannerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        ButterKnife.bind(this, view);

        bannerAdapter = new BannerAdapter();
        bannerAdapter.setOnAdapterItemClickListener(new BannerAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, BannerData data, int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getUrl()));
                startActivity(intent);
            }
        });
        bannerListView.setAdapter(bannerAdapter);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        bannerListView.setLayoutManager(manager);

        initSale();
        initBanner();

        return view;
    }

    private void initSale() {
        String str = "";
        for (int i = 0; i < 5; i++) {
            str += "brand " + i + " ~ end 까지\n";
        }

        saleInfoView.setText(str);
    }

    private void initBanner() {
        for (int i = 0; i < 3; i++) {
            BannerData data = new BannerData();
            data.setUrl("http://www.google.com");
            bannerAdapter.add(data);
        }
    }
}
