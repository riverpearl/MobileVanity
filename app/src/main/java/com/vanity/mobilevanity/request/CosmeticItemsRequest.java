package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class CosmeticItemsRequest extends AbstractRequest<NetworkResult<List<CosmeticItem>>> {
    Request mRequest;

    public CosmeticItemsRequest(Context context, String category, String item) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("cosmeticitems")
                .addQueryParameter("category", category)
                .addQueryParameter("item", item)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<List<CosmeticItem>>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
