package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class UpdateLikeRequest extends AbstractRequest<NetworkResult<BeautyTip>> {
    Request mRequest;

    public UpdateLikeRequest(Context context, String beautytipid, String like) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("updatelike")
                .addQueryParameter("like", like)
                .build();

        mRequest = new Request.Builder()
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
        return mRequest;
    }
}
