package com.vanity.mobilevanity.beautytip;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.BeautyTipAdapter;
import com.vanity.mobilevanity.adapter.BeautyTipSpinnerAdapter;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.BeautyTipInfoRequest;
import com.vanity.mobilevanity.request.SearchBeautyTipRequest;
import com.vanity.mobilevanity.request.UpdateBeautyTipRequest;

import java.io.File;
import java.util.List;

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

    Intent intent;
    long id;

    public BeautyTipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beauty_tip, container, false);
        ButterKnife.bind(this, view);

        sAdapter = new BeautyTipSpinnerAdapter();
        spinner.setAdapter(sAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getContext(), "item:" + sAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mAdapter = new BeautyTipAdapter();
        listView.setAdapter(mAdapter);
        mAdapter.setOnAdapterItemClickListener(new BeautyTipAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, BeautyTip beautyTip, int position) {
                //   BeautyTipInfoRequest request = new BeautyTipInfoRequest(getContext(),mAdapter.getItemId(position))
                Intent intent = new Intent(getContext(), BeautyTipDetailActivity.class);
                intent.putExtra("beautytipid", beautyTip.getId());
                startActivity(intent);
            }
        });

        mAdapter.setOnAdapterCommentClickListener(new BeautyTipAdapter.OnAdapterCommentClickListener() {
            @Override
            public void onAdapterCommentClick(View view, BeautyTip beautyTip, Comment comment) {
                FragmentManager fm = getFragmentManager();
                BeautyTipCommentFragment dialog = new BeautyTipCommentFragment();
                Bundle args = new Bundle();
                args.putLong("commentid", beautyTip.getId());
                dialog.setArguments(args);
                dialog.show(fm, "dialog");
            }
        });


        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        init();
        initData();
        return view;
    }

    private void init() {
        String[] items = getResources().getStringArray(R.array.items);
        sAdapter.addAll(items);
    }

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
            user.setUserNickName("NickName");
            beautyTip.setUser(user);
            beautyTip.setLikeCount(5);
            beautyTip.setCommentNum(10);
            beautyTip.setReadNum(15);

            mAdapter.add(beautyTip);
        }
    }

    public static final String keyword = "keyword";
    public static final String query = "";

    @Override
    public void onStart() {
        super.onStart();
        String order;

        if (spinner.getSelectedItemPosition() == 0)
            order = "recent";
        else
            order = "popular";

        SearchBeautyTipRequest request = new SearchBeautyTipRequest(getContext(), keyword, query, order);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<BeautyTip>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<BeautyTip>>> request, NetworkResult<List<BeautyTip>> result) {
                List<BeautyTip> tips = result.getResult();
                mAdapter.clear();
                mAdapter.addAll(tips);
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<BeautyTip>>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
