package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.manager.NetworkRequest;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class BeautyTipInfoRequest extends AbstractRequest<NetworkResult<BeautyTip>> {
    Request request;

    public BeautyTipInfoRequest(Context context, String beautyTipId) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("beautytipinfo")
                .addQueryParameter("beautytipid", beautyTipId)
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<BeautyTip>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
