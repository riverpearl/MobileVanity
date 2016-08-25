package com.vanity.mobilevanity.cosmetic;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vanity.mobilevanity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CosmeticListFragment extends Fragment {
    private static final String ARG_COSMETIC = "param1";
    private String cosmetic;

    public CosmeticListFragment() {

    }

    public static CosmeticListFragment newInstance(String cosmetic) {
        CosmeticListFragment fragment = new CosmeticListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COSMETIC, cosmetic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cosmetic = getArguments().getString(ARG_COSMETIC);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cosmetic, container, false);
        TextView tv = (TextView) view.findViewById(R.id.text_cosmetic_tab);
        tv.setText(cosmetic);

        return view;
    }
}
