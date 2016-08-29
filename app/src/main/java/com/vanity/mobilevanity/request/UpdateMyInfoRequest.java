package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class UpdateMyInfoRequest extends AbstractRequest<NetworkResult<User>> {
    MediaType jpeg = MediaType.parse("image/jpeg");
    Request mRequest;

    public UpdateMyInfoRequest(Context context, File userProfile, String userNickName, String skinType, String skinTone, String gender) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("updatemyinfo")
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userNickName", userNickName)
                .addFormDataPart("userNickName", userNickName)
                .addFormDataPart("skinType", skinType)
                .addFormDataPart("skinTone", skinTone)
                .addFormDataPart("gender", gender);

        if (userProfile != null) {
            builder.addFormDataPart("userProfile", userProfile.getName(),
                    RequestBody.create(jpeg, userProfile));
        }

        RequestBody body = builder.build();
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
