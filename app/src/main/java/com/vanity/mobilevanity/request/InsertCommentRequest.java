package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.Comment;
import com.vanity.mobilevanity.data.NetworkResult;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class InsertCommentRequest extends AbstractRequest<NetworkResult<Comment>> {
    Request request;

    public InsertCommentRequest(Context context, String beautyTipId, String comment) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("insertcomment")
                .build();

        RequestBody body = new FormBody.Builder()
                .add("beautytipid", beautyTipId)
                .add("comment", comment)
                .build();

        request = new Request.Builder()
                .url(url)
                .post(body)
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
