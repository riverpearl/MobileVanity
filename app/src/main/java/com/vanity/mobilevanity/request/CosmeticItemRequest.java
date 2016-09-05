package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.data.NetworkResult;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-09-05.
 */
public class CosmeticItemRequest extends AbstractRequest<NetworkResult<CosmeticItem>> {
    private Request request;

    public CosmeticItemRequest(Context context, String cosmeticitemid) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("cosmeticitem")
                .addQueryParameter("cosmeticitemid", cosmeticitemid)
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<CosmeticItem>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
