package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.FAQ;
import com.vanity.mobilevanity.data.NetworkResult;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class FAQListRequest extends AbstractRequest<NetworkResult<FAQ>> {
    Request request;

    public FAQListRequest(Context context) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("faqlist")
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<FAQ>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return null;
    }
}
