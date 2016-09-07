package com.vanity.mobilevanity.beautytip;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.print.PageRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.media.TransportPerformer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.BeautyTipPopUpAdapter;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.CommentListRequest;
import com.vanity.mobilevanity.request.InsertCommentRequest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class BeautyTipCommentFragment extends DialogFragment {

    @BindView(R.id.edit_comment)
    EditText inputView;
    @BindView(R.id.image_profile)
    ImageView userImage;
    @BindView(R.id.popup_list)
    RecyclerView listView;

    BeautyTipPopUpAdapter mAdapter;

    Bundle extra;
    long args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extra = getArguments();
        args = extra.getLong("commentid");
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_beauty_tip_pop_up, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new BeautyTipPopUpAdapter();
        listView.setAdapter(mAdapter);

        final LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        return view;
    }

    @OnClick(R.id.image_profile)
    public void onProfile() {
        InsertCommentRequest request = new InsertCommentRequest(getContext(), "" + args, inputView.getText().toString());
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<Comment>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<Comment>> request, NetworkResult<Comment> result) {
                if (result.getCode() == 1) {
                    Comment comment = result.getResult();
                    inputView.setText("");
                    getCommentList();
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<Comment>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(), "댓글 입력하기에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        onStart();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog d = getDialog();
        WindowManager.LayoutParams params = d.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 100;
        params.y = 100;
        d.getWindow().setAttributes(params);
    }

    @Override
    public void onStart() {
        super.onStart();

        getCommentList();
    }

    private void getCommentList() {
        CommentListRequest request = new CommentListRequest(getContext(), "" + args);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<Comment>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<Comment>>> request, NetworkResult<List<Comment>> result) {
                if (result.getCode() == 1) {
                    List<Comment> comments = result.getResult();
                    mAdapter.clear();
                    mAdapter.addAll(comments);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<Comment>>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(), "댓글 불러오기에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
