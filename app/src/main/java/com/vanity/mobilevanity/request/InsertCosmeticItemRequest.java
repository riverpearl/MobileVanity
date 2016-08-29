package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.CosmeticItem;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class InsertCosmeticItemRequest extends AbstractRequest<NetworkResult<CosmeticItem>> {
    Request mRequest;

    public InsertCosmeticItemRequest(Context context, String cosmeticid, String dateAdded, String cosmeticTerm) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("insertcosmeticitem")
                .build();

        RequestBody body = new FormBody.Builder()
                .add("cosmeticid", cosmeticid)
                .add("dateAdded", dateAdded)
                .add("cosmeticTerm", cosmeticTerm)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .post(body)
                .tag(context)
                .build();

    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<CosmeticItem>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
