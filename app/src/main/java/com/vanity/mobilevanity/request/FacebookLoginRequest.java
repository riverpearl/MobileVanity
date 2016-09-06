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
public class FacebookLoginRequest extends AbstractRequest<NetworkResult<User>> {

    Request mRequest;
    public FacebookLoginRequest(Context context, String access_token, String registrationId) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("facebooklogin")
                .build();

        RequestBody body = new FormBody.Builder()
                .add("access_token", access_token)
                .add("registrationId", registrationId)
                .build();

        mRequest = new Request.Builder()
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
        return mRequest;
    }
}
