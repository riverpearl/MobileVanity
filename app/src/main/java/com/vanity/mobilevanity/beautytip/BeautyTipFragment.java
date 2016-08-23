package com.vanity.mobilevanity.beautytip;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanity.mobilevanity.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeautyTipFragment extends Fragment {


    public BeautyTipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beauty_tip, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_set)
    public void onSetClick(View view) {
        Intent intent = new Intent(getContext(), BeautyTipWriteActivity.class);
        startActivity(intent);
    }

}
