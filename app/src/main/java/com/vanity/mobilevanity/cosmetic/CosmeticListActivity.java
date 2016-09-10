package com.vanity.mobilevanity.cosmetic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.adapter.CosmeticAdapter;
import com.vanity.mobilevanity.data.Brand;
import com.vanity.mobilevanity.data.Constant;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.Product;
import com.vanity.mobilevanity.manager.DBManager;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.register.RegisterBarcodeActivity;
import com.vanity.mobilevanity.register.RegisterSearchActivity;
import com.vanity.mobilevanity.request.CosmeticItemsRequest;
import com.vanity.mobilevanity.request.CosmeticListRequest;
import com.vanity.mobilevanity.request.DeleteCosmeticItemRequest;
import com.vanity.mobilevanity.request.SearchCosmeticListRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CosmeticListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_toolbar_title)
    TextView toolbarTitleView;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.rv_cosmetic)
    RecyclerView listView;

    CosmeticAdapter mAdapter;

    int category = 0;
    int tabpos = 0;

    public final static String TAG_CATEGORY = "category";
    public final static String TAG_TAB_POS = "tabpos";
    public final static int KEY_COSMETIC_LIST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        category = intent.getIntExtra(TAG_CATEGORY, 0);
        tabpos = intent.getIntExtra(TAG_TAB_POS, 0);

        mAdapter = new CosmeticAdapter();
        mAdapter.setOnAdapterItemClickListener(new CosmeticAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, CosmeticItem data, int position) {
                Intent intent = new Intent(CosmeticListActivity.this, CosmeticDetailActivity.class);
                intent.putExtra(CosmeticDetailActivity.TAG_COSMETIC_ITEM_ID, data.getId());
                startActivity(intent);
            }
        });
        mAdapter.setOnAdapterItemLongClickListener(new CosmeticAdapter.OnAdapterItemLongClickListener() {
            @Override
            public void onAdapterItemLongClick(View view, CosmeticItem data, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CosmeticListActivity.this);
                builder.setTitle("등록된 제품 삭제");
                builder.setMessage("삭제하시겠습니까?");

                final long id = data.getId();
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteCosmeticItemRequest(id);
                        int a = DBManager.getInstance().deleteCosmeticItem(id);
                        Log.d("dbresult", a + "");
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
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

    public void getCosmeticItemList() {
        CosmeticItemsRequest request = new CosmeticItemsRequest(CosmeticListActivity.this, "" + category, "" + (tabpos + 1));
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<CosmeticItem>>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<List<CosmeticItem>>> request, NetworkResult<List<CosmeticItem>> result) {
                if (result.getCode() == 1) {
                    List<CosmeticItem> cosmetic = result.getResult();
                    mAdapter.clear();
                    mAdapter.addAll(cosmetic);
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<List<CosmeticItem>>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(CosmeticListActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTab() {
        List<String> tabName = new ArrayList<>();
        switch (category) {
            case Constant.INDEX_CATEGROY_NONE:
                finish();
                break;

            case Constant.INDEX_CATEGORY_EYE:
                tabName.add("섀도우");
                tabName.add("마스카라");
                tabName.add("아이라이너");
                tabName.add("아이브로우");
                tabName.add("프라이머");

                toolbarTitleView.setText(getResources().getString(R.string.toolbar_title_cosmetic_list_eye_makeup));
                break;

            case Constant.INDEX_CATEGORY_LIP: {
                tabName.add("립스틱");
                tabName.add("립글로스");
                tabName.add("립틴트");
                tabName.add("립케어");

                toolbarTitleView.setText(getResources().getString(R.string.toolbar_title_cosmetic_list_lip_makeup));
                break;
            }
            case Constant.INDEX_CATEGORY_SKIN: {
                tabName.add("스킨/토너");
                tabName.add("로션/크림");
                tabName.add("에센스/세럼");
                tabName.add("선케어");

                toolbarTitleView.setText(getResources().getString(R.string.toolbar_title_cosmetic_list_skin_care));
                break;
            }
            case Constant.INDEX_CATEGORY_FACE: {
                tabName.add("BB/파운데이션");
                tabName.add("파우더/팩트");
                tabName.add("프라이머/베이스");
                tabName.add("치크/하이라이터");

                toolbarTitleView.setText(getResources().getString(R.string.toolbar_title_cosmetic_list_face));
                break;
            }
            case Constant.INDEX_CATEGORY_CLEANSING: {
                tabName.add("클렌징");
                tabName.add("폼클렌징");

                toolbarTitleView.setText(getResources().getString(R.string.toolbar_title_cosmetic_list_cleansing));
                break;
            }
            case Constant.INDEX_CATEGORY_TOOL: {
                tabName.add("브러쉬");
                tabName.add("퍼프");

                toolbarTitleView.setText(getResources().getString(R.string.toolbar_title_cosmetic_list_tool));
                break;
            }
        }

        for (int i = 0; i < tabName.size(); i++) {
            tabs.addTab(tabs.newTab().setText(tabName.get(i)).setTag(tabName.get(i)));
        }

        if (tabpos != -1)
            tabs.getTabAt(tabpos).select();
        else tabs.getTabAt(0).select();
    }

    @OnClick(R.id.fab_add_cosmetic)
    public void onFloatingClick(View view) {
        Message msg = addCosmeticHandler.obtainMessage(0);
        addCosmeticHandler.removeMessages(0);
        addCosmeticHandler.sendMessageDelayed(msg, 1000);
    }

    private Handler addCosmeticHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(CosmeticListActivity.this, RegisterBarcodeActivity.class);
            intent.putExtra(RegisterSearchActivity.TAG_REQUEST_CODE, KEY_COSMETIC_LIST);
            intent.putExtra(TAG_CATEGORY, category);
            intent.putExtra(TAG_TAB_POS, tabpos);
            startActivity(intent);
        }
    };

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

        initTab();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mAdapter.clear();
                tabpos = tab.getPosition();
                getCosmeticItemList();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mAdapter.clear();
                NetworkManager.getInstance().cancelAll();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mAdapter.clear();
                tabpos = tab.getPosition();
                getCosmeticItemList();
            }
        });

        getCosmeticItemList();
    }

    private void deleteCosmeticItemRequest(long id) {
        final long cid = id;
        DeleteCosmeticItemRequest request = new DeleteCosmeticItemRequest(this, cid + "");
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<CosmeticItem>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<CosmeticItem>> request, NetworkResult<CosmeticItem> result) {
                if (result.getCode() == 1) {
                    DBManager.getInstance().deleteCosmeticItem(cid);
                    Toast.makeText(CosmeticListActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    getCosmeticItemList();
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<CosmeticItem>> request, int errorCode, String errorMessage, Throwable e) {

            }
        });

    }
}
