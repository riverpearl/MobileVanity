package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.BannerData;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.Product;
import com.vanity.mobilevanity.data.Sale;
import com.vanity.mobilevanity.data.User;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class SaleInfoRequest extends AbstractRequest<NetworkResult<Sale>> {
    Request mRequest;

    public SaleInfoRequest(Context context, String type, String productid) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("saleinfo")
                .addQueryParameter("type", type)
                .addQueryParameter("productid", productid)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    public SaleInfoRequest(Context context, String type, String datetype, String date) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("saleinfo")
                .addQueryParameter("type", type)
                .addQueryParameter(datetype, date)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {

        return new TypeToken<NetworkResult<Sale>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
