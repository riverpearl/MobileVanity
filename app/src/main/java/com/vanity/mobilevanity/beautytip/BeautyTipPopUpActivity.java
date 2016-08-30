package com.vanity.mobilevanity.beautytip;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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

public class BeautyTipPopUpActivity extends AppCompatActivity {
    @BindView(R.id.popup_list)
    RecyclerView listView;

    @BindView(R.id.edit_comment)
    EditText commentEdit;

    BeautyTipPopUpAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        layoutParams.dimAmount = 0.7f;
//        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_beauty_tip_pop_up);
        ButterKnife.bind(this);

        mAdapter = new BeautyTipPopUpAdapter();
        listView.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        init();
    }

    public void init() {
        Comment comment = new Comment();
        User user = new User();
        // 프로필 이미지 값 추가
        comment.setContent("Content");
        mAdapter.add(comment);

    }

    public static final String comment = "comment";

    @OnClick(R.id.image_profile)
    public void onInsertComment() {
        Intent popup = getIntent();
        long id = popup.getLongExtra("commentid", 0);
        InsertCommentRequest request = new InsertCommentRequest(getBaseContext(), "" + id, comment);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<Comment>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<Comment>> request, NetworkResult<Comment> result) {
                Comment comment = result.getResult();
                mAdapter.clear();
                mAdapter.add(comment);
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<Comment>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent intent = getIntent();
//        long beautyid = intent.getLongExtra("beautytipid", 0);
//        CommentListRequest request = new CommentListRequest(getBaseContext(), ""+beautyid);
//        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<Comment>>>() {
//            @Override
//            public void onSuccess(NetworkRequest<NetworkResult<List<Comment>>> request, NetworkResult<List<Comment>> result) {
//                List<Comment> comment = result.getResult();
//            }
//
//            @Override
//            public void onFail(NetworkRequest<NetworkResult<List<Comment>>> request, int errorCode, String errorMessage, Throwable e) {
//
//            }
//        });
//
//    }
}
