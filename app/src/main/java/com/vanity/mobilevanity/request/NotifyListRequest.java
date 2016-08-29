package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.Notify;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class NotifyListRequest extends AbstractRequest<NetworkResult<Notify>> {
    Request request;

    public NotifyListRequest(Context context, String date) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("notifylist")
                .addQueryParameter("lastDate", date)
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<Notify>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
