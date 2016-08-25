package com.vanity.mobilevanity.beautytip;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.BeautyTipAdapter;
import com.vanity.mobilevanity.adapter.BeautyTipSpinnerAdapter;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeautyTipFragment extends Fragment {
    @BindView(R.id.spinner)
    Spinner spinner;
    BeautyTipSpinnerAdapter sAdapter;

    @BindView(R.id.beautytiplist)
    RecyclerView listView;
    BeautyTipAdapter mAdapter;


    public BeautyTipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beauty_tip, container, false);
        ButterKnife.bind(this, view);
        //  spinner.setAdapter(sAdapter);
        mAdapter = new BeautyTipAdapter();
        listView.setAdapter(mAdapter);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

//        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(getContext(), "item:" + sAdapter.getItem(position), Toast.LENGTH_SHORT).show();
//            }
//        });

        //initData();
        initData();
        return view;
    }

    // Spinner
//    private void initData() {
//        String[] items = getResources().getStringArray(R.array.items);
//        sAdapter.addAll(items);
//    }

    @OnClick(R.id.btn_set)
    public void onSetClick(View view) {
        Intent intent = new Intent(getContext(), BeautyTipWriteActivity.class);
        startActivity(intent);
    }

    public void initData() {
        for (int i = 0; i < 4; i++) {
            BeautyTip beautyTip = new BeautyTip();
            User user = new User();

            beautyTip.setTitle("Title");
            beautyTip.setPreviewImage("PreviewImage");
            beautyTip.setContent("Content");
            user.setUserNickname("NickName");
            beautyTip.setUser(user);
            beautyTip.setLikeNum(5);
            beautyTip.setCommentNum(10);
            beautyTip.setReadNum(15);

            mAdapter.add(beautyTip);
        }
    }
}
