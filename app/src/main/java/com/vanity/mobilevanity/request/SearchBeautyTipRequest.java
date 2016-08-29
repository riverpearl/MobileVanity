package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.BeautyTip;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class SearchBeautyTipRequest extends AbstractRequest<NetworkResult<List<BeautyTip>>> {
    Request mRequest;

    public SearchBeautyTipRequest(Context context, String type) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("searchbeautytip")
                .addQueryParameter("type", type)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    public SearchBeautyTipRequest(Context context, String type, String order, String query) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("searchbeautytip")
                .addQueryParameter("type", type)
                .addQueryParameter("query", " ")
                .addQueryParameter("order", order)
                .build();

        mRequest = new Request.Builder()
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
        return mRequest;
    }
}
