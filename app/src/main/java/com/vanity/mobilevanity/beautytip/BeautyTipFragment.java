package com.vanity.mobilevanity.beautytip;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
                Intent intent = new Intent(getContext(), BeautyTipDetailActivity.class);
                startActivity(intent);
            }
        });

        mAdapter.setOnAdapterCommentClickListener(new BeautyTipAdapter.OnAdapterCommentClickListener() {
            @Override
            public void onAdapterCommentClick(View view, Comment comment) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setIcon(android.R.drawable.ic_btn_speak_now);
//                builder.setTitle("Comment");
//                builder.setMessage("Message");
//
//                final EditText input = new EditText(view.getContext());
//                builder.setView(input);
//
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String value = input.getText().toString();
//                        value.toString();
//                    }
//                });
//                builder.show();
                Toast.makeText(getContext(), "djdj", Toast.LENGTH_SHORT).show();
            }
        });


        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);

        init();
        initData();
        return view;
    }

    // Spinner
    private void init() {
        String[] items = getResources().getStringArray(R.array.items);
        sAdapter.addAll(items);
    }

    @OnClick(R.id.btn_set)
    public void onSetClick(View view) {
        Intent intent = new Intent(getContext(), BeautyTipWriteActivity.class);
        startActivity(intent);
    }

    //    @OnClick(R.id.btn_comment)
//    public void onCommentClick() {
//        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//
//        alert.setTitle("Comment");
//        alert.setMessage("Message");
//
//        final EditText input = new EditText(getContext());
//        alert.setView(input);
//
//        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                String value = input.getText().toString();
//                value.toString();
//            }
//        });
//
//        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        alert.show();
//    }
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
