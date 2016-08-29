package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
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
public class LoginRequest extends AbstractRequest<NetworkResult<User>> {
    Request request;

    public LoginRequest(Context context, String email, String password, String registrationId) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("login")
                .build();

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("registrationId", registrationId)
                .build();

        request = new Request.Builder()
                .url(url)
                .post(body)
                .tag(context)
                .build();
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<User>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
