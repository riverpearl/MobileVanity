package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class CosmeticListRequest extends AbstractRequest<NetworkResult<Cosmetic>> {
    Request mRequest;

    public CosmeticListRequest(Context context, String productid) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("cosmeticlist")
                .addQueryParameter("productid", productid)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<Cosmetic>>() {}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
