package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class DeleteCosmeticItemRequest extends AbstractRequest<NetworkResult<CosmeticItem>> {
    Request mRequest;

    public DeleteCosmeticItemRequest(Context context, String cosmeticitemid) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("deletecosmeticitem")
                .addQueryParameter("cosmeticitemid", cosmeticitemid)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .delete()
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<CosmeticItem>>() {}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
