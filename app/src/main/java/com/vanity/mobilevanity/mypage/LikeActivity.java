package com.vanity.mobilevanity.mypage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.LikeAdapter;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.LikeBeautyTipListRequest;
import com.vanity.mobilevanity.request.UpdateLikeRequest;
import com.vanity.mobilevanity.request.UpdateMyInfoRequest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LikeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_toolbar_title)
    TextView toolbarTitleView;

    @BindView(R.id.rv_like)
    RecyclerView listView;

    LikeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitleView.setText(getResources().getString(R.string.toolbar_title_like));

        mAdapter = new LikeAdapter();
        mAdapter.setOnAdapterItemClickListener(new LikeAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, BeautyTip data, int position) {
                Intent intent = new Intent(LikeActivity.this, BeautyTipDetailActivity.class);
                intent.putExtra(BeautyTipDetailActivity.TAG_BEAUTY_TIP_ID, data.getId());
                startActivity(intent);
            }
        });
        mAdapter.setOnAdapterItemLongClickListener(new LikeAdapter.OnAdapterItemLongClickListener() {
            @Override
            public void onAdapterLongItemClick(View view, BeautyTip data, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LikeActivity.this);
                builder.setTitle(getResources().getString(R.string.activity_like_dialog_title));
                builder.setMessage((getResources().getString(R.string.activity_like_dialog_content)));

                final long id = data.getId();
                final int pos = position;
                builder.setPositiveButton(getResources().getString(R.string.positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateLikeRequest(id, pos);
                    }
                });
                builder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
            }
        });

        listView.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(manager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        LikeBeautyTipListRequest request = new LikeBeautyTipListRequest(this);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<BeautyTip>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<BeautyTip>>> request, NetworkResult<List<BeautyTip>> result) {
                if (result.getCode() == 1) {
                    List<BeautyTip> tips = result.getResult();
                    mAdapter.clear();
                    mAdapter.addAll(tips);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<BeautyTip>>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(LikeActivity.this, errorCode + " : " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLikeRequest(long id, int position) {
        final int pos = position;

        UpdateLikeRequest request = new UpdateLikeRequest(this, id + "", "false");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                if (result.getCode() == 1) {
                    Toast.makeText(LikeActivity.this, "좋아요가 취소되었습니다.", Toast.LENGTH_SHORT).show();
                    mAdapter.remove(pos);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });
    }
}
