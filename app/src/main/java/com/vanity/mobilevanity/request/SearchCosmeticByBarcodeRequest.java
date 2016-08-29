package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.Cosmetic;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class SearchCosmeticByBarcodeRequest extends AbstractRequest<NetworkResult<Cosmetic>> {
    Request mRequest;

    public SearchCosmeticByBarcodeRequest(Context context, String barcode) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("searchbybarcode")
                .addQueryParameter("barcode", barcode)
                .build();

        mRequest = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<User>>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
