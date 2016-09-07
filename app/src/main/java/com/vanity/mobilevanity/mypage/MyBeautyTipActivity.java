package com.vanity.mobilevanity.mypage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.MyBeautyTipAdapter;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.request.DeleteBeautyTipRequest;
import com.vanity.mobilevanity.request.SearchBeautyTipRequest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBeautyTipActivity extends AppCompatActivity {

    @BindView(R.id.rv_my_beauty_tip)
    RecyclerView listView;

    MyBeautyTipAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_beauty_tip);

        ButterKnife.bind(this);

        mAdapter = new MyBeautyTipAdapter();
        mAdapter.setOnAdapterItemClickListener(new MyBeautyTipAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, BeautyTip data, int position) {
                Intent intent = new Intent(MyBeautyTipActivity.this, BeautyTipDetailActivity.class);
                intent.putExtra("beautytipid", data.getId());
                startActivity(intent);
            }
        });
        mAdapter.setOnAdapterItemLongClickListener(new MyBeautyTipAdapter.OnAdapterItemLongClickListener() {
            @Override
            public void onAdapterItemLongClick(View view, BeautyTip data, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyBeautyTipActivity.this);
                builder.setTitle("뷰티 팁 삭제");
                builder.setMessage("삭제하시겠습니까?");

                final long id = data.getId();
                final int pos = position;
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteMyBeautyTipRequest(id, pos);
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
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
        getMyBeautyTipList();
    }

    private void getMyBeautyTipList() {
        String type = "user";
        SearchBeautyTipRequest request = new SearchBeautyTipRequest(this, type);
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
                Toast.makeText(MyBeautyTipActivity.this, errorCode + " : " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteMyBeautyTipRequest(long id, int position) {
        final int pos = position;

        DeleteBeautyTipRequest request = new DeleteBeautyTipRequest(this, id + "");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<BeautyTip>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<BeautyTip>> request, NetworkResult<BeautyTip> result) {
                if (result.getCode() == 1) {
                    Toast.makeText(MyBeautyTipActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    mAdapter.remove(pos);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<BeautyTip>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });

    }
}
