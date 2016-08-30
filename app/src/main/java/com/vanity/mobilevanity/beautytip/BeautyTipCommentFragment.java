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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.BeautyTipPopUpAdapter;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.CommentListRequest;
import com.vanity.mobilevanity.request.InsertCommentRequest;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Tacademy on 2016-08-30.
 */
public class BeautyTipCommentFragment extends DialogFragment {
// 마지막에 버터나이프해주기 (꼭!)

    EditText inputView;
    ImageView userImage;
    RecyclerView listView;
    BeautyTipPopUpAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_beauty_tip_pop_up, container, false);
        ButterKnife.bind(this, view);

        listView = (RecyclerView) view.findViewById(R.id.popup_list);
        mAdapter = new BeautyTipPopUpAdapter();
        listView.setAdapter(mAdapter);

        final LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        inputView = (EditText) view.findViewById(R.id.edit_comment);
        userImage = (ImageView) view.findViewById(R.id.image_profile);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String text = inputView.getText().toString();
//                Comment comment = new Comment();
//                comment.setContent(text);
//                mAdapter.add(comment);
                Bundle extra = getArguments();
                long args = extra.getLong("commentid");

                CommentListRequest request = new CommentListRequest(getContext(), ""+args);
                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<Comment>>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<List<Comment>>> request, NetworkResult<List<Comment>> result) {
                        List<Comment>comments = result.getResult();
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<List<Comment>>> request, int errorCode, String errorMessage, Throwable e) {

                    }
                });


            }
        });


        return view;
    }

    public void init() {
        Comment comment = new Comment();
        comment.setContent(inputView.getText().toString());
        mAdapter.add(comment);
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
}
