package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.NetworkResult;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class LikeBeautyTipListRequest extends AbstractRequest<NetworkResult<List<BeautyTip>>> {
    Request request;

    public LikeBeautyTipListRequest(Context context) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("likebeautytiplist")
                .build();

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<List<BeautyTip>>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
