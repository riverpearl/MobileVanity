package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.manager.NetworkRequest;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class CommentListRequest extends AbstractRequest<NetworkResult<Comment>> {
    Request request;

    public CommentListRequest(Context context, String beautyTipId) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("commentlist")
                .addQueryParameter("beautytipid", beautyTipId)
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<Comment>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
