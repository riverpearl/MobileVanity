package com.vanity.mobilevanity.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.NetworkResultTemp;
import com.vanity.mobilevanity.manager.NetworkRequest;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

/**
 * Created by Tacademy on 2016-08-29.
 */
public abstract class AbstractRequest<T> extends NetworkRequest<T> {
    protected HttpUrl.Builder getBaseUrlBuilder() {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme("https");
        builder.host("mobilevanityserver.appspot.com");
        return builder;
    }

    @Override
    protected T parse(ResponseBody body) throws IOException {
        String text = body.string();
        Gson gson = new Gson();
        T result = gson.fromJson(text, getType());
        return result;
    }

    protected Type getType(int code) {
        return getType();
    }

    protected abstract Type getType();
}
