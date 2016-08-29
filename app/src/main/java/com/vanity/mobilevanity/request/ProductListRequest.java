package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.Product;
import com.vanity.mobilevanity.data.User;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class ProductListRequest extends AbstractRequest<NetworkResult<Product>> {
    Request mRequest;

    public ProductListRequest(Context context, String brandid) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("productlist")
                .addQueryParameter("brandid", brandid)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<Product>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
