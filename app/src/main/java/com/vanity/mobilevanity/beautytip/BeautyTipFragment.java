package com.vanity.mobilevanity.beautytip;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.vanity.mobilevanity.request.MyInfoRequest;
import com.vanity.mobilevanity.request.SearchBeautyTipRequest;
import com.vanity.mobilevanity.request.UpdateBeautyTipRequest;
import com.vanity.mobilevanity.request.UpdateLikeRequest;

import java.io.File;
import java.util.Collections;
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

    String order;
    private User user;

    public static final String TAG_COMMENT = "commentdialog";
    public static final String TAG_BEAUTY_TIP_ID = "beautytipid";
    public static final String TAG_USER_PROFILE = "userprofile";
    public static final String TAG_USER_NICKNAME = "usernickname";

    public BeautyTipFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beauty_tip, container, false);
        ButterKnife.bind(this, view);

        sAdapter = new BeautyTipSpinnerAdapter();
        spinner.setAdapter(sAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int position, final long id) {
                Toast.makeText(getContext(), "item:" + sAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                // final String order;

                if (spinner.getSelectedItemPosition() == 0)
                    order = "recent";
                else
                    order = "rank";

                SearchBeautyTipRequest searchRequest = new SearchBeautyTipRequest(getContext(), keyword, query, order);
                NetworkManager.getInstance().getNetworkData(searchRequest, new NetworkManager.OnResultListener<NetworkResult<List<BeautyTip>>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<List<BeautyTip>>> request, NetworkResult<List<BeautyTip>> result) {
                        if (result.getCode() == 1) {
                            List<BeautyTip> beautySpinner = result.getResult();
                            Collections.reverse(beautySpinner);
                            mAdapter.clear();
                            mAdapter.addAll(beautySpinner);
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<List<BeautyTip>>> request, int errorCode, String errorMessage, Throwable e) {
                    }
                });

                //onStart();
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
                intent.putExtra(TAG_BEAUTY_TIP_ID, beautyTip.getId());
                startActivity(intent);
            }
        });

        mAdapter.setOnAdapterCommentClickListener(new BeautyTipAdapter.OnAdapterCommentClickListener() {
            @Override
            public void onAdapterCommentClick(View view, BeautyTip beautyTip, Comment comment) {
                FragmentManager fm = getFragmentManager();
                BeautyTipCommentFragment dialog = new BeautyTipCommentFragment();
                Bundle args = new Bundle();
                final long BeautyTipId = beautyTip.getId();

                MyInfoRequest request = new MyInfoRequest(getContext());
                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                        if (result.getCode() == 1)
                            user = result.getResult();
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });

                if (user != null) {
                    args.putLong(TAG_BEAUTY_TIP_ID, BeautyTipId);
                    args.putString(TAG_USER_PROFILE, user.getUserProfile());
                    args.putString(TAG_USER_NICKNAME, user.getUserNickName());
                    dialog.setArguments(args);
                    dialog.show(fm, TAG_COMMENT);

                }
            }
        });

        mAdapter.setOnAdapterLikeClickListener(new BeautyTipAdapter.OnAdapterLikeClickListener() {
            @Override
            public void onAdapterLikeClick(View view, BeautyTip beautyTip, final int position) {
                String like;
                if (beautyTip.isLike()) like = "false";
                else like = "true";

                UpdateLikeRequest likeRequest = new UpdateLikeRequest(getContext(), "" + beautyTip.getId(), like);
                NetworkManager.getInstance().getNetworkData(likeRequest, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                        if (result.getCode() == 1) {
                            BeautyTip beauty = result.getResult();
                            mAdapter.set(position, beauty);
                        }
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {
                    }
                });
            }
        });

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        init();
        return view;
    }

    private void init() {
        String[] items = getResources().getStringArray(R.array.beautytip_sort);
        sAdapter.addAll(items);
    }

    @OnClick(R.id.btn_set)
    public void onSetClick(View view) {
        Message msg = setHandler.obtainMessage(0);
        setHandler.removeMessages(0);
        setHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler setHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(getContext(), BeautyTipWriteActivity.class);
            intent.putExtra(BeautyTipWriteActivity.TAG_SEARCH_TYPE, BeautyTipWriteActivity.INDEX_TYPE_FRAGMENT);
            startActivity(intent);
        }
    };

    public static final String keyword = "keyword";
    public static final String query = "";

    @Override
    public void onStart() {
        super.onStart();
        if (spinner.getSelectedItemPosition() == 0)
            order = "recent";
        else
            order = "rank";

        SearchBeautyTipRequest searchRequest = new SearchBeautyTipRequest(getContext(), keyword, query, order);
        NetworkManager.getInstance().getNetworkData(searchRequest, new NetworkManager.OnResultListener<NetworkResult<List<BeautyTip>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<BeautyTip>>> request, NetworkResult<List<BeautyTip>> result) {
                if (result.getCode() == 1) {
                    List<BeautyTip> beautySpinner = result.getResult();
                    Collections.reverse(beautySpinner);
                    mAdapter.clear();
                    mAdapter.addAll(beautySpinner);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<BeautyTip>>> request, int errorCode, String errorMessage, Throwable e) {
            }
        });
    }
}
