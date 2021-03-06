package com.vanity.mobilevanity.beautytip;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.print.PageRange;
import android.support.annotation.Dimension;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
    ImageView profileView;

    @BindView(R.id.rv_comment)
    RecyclerView listView;

    @BindView(R.id.btn_send)
    Button sendButton;

    BeautyTipPopUpAdapter mAdapter;

    Bundle extra;
    private long beautyTipId = 0;
    private String userProfile = "";
    private String userNickname = "";

    private long lastClickTime = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        extra = getArguments();
        beautyTipId = extra.getLong(BeautyTipFragment.TAG_BEAUTY_TIP_ID);
        userProfile = extra.getString(BeautyTipFragment.TAG_USER_PROFILE);
        userNickname = extra.getString(BeautyTipFragment.TAG_USER_NICKNAME);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_beauty_tip_pop_up, container, false);
        ButterKnife.bind(this, view);

        sendButton.setText(R.string.dialog_button_text);
        sendButton.setTextSize(Dimension.SP, 14);

        mAdapter = new BeautyTipPopUpAdapter();
        listView.setAdapter(mAdapter);

        final LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        listView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                listView.scrollToPosition(mAdapter.getItemCount() - 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Dialog d = getDialog();
        WindowManager.LayoutParams params = d.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        d.getWindow().setAttributes(params);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (profileView != null)
            Glide.with(profileView.getContext()).load(userProfile).into(profileView);

        getCommentList();
    }

    private void getCommentList() {
        CommentListRequest request = new CommentListRequest(getContext(), "" + beautyTipId);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<Comment>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<Comment>>> request, NetworkResult<List<Comment>> result) {
                if (result.getCode() == 1) {
                    List<Comment> comments = result.getResult();
                    mAdapter.clear();
                    mAdapter.addAll(comments);
                    listView.scrollToPosition(mAdapter.getItemCount() - 1);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<Comment>>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(), "댓글 불러오기에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_send)
    public void onSendButton() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
        lastClickTime = SystemClock.elapsedRealtime();

        InsertCommentRequest request = new InsertCommentRequest(getContext(), "" + beautyTipId, inputView.getText().toString());
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<Comment>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<Comment>> request, NetworkResult<Comment> result) {
                if (result.getCode() == 1) {
                    inputView.setText("");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    getCommentList();
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<Comment>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(getContext(), "댓글 입력하기에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.btn_cancel)
    public void onCancelClick(View view) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) return;
        lastClickTime = SystemClock.elapsedRealtime();

        this.dismiss();
    }
}
