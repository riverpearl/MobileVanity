package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class FacebookSignupRequest extends AbstractRequest<NetworkResult<User>> {
    MediaType jpeg = MediaType.parse("image/jpeg");
    Request mRequest;

    public FacebookSignupRequest(Context context, String nickname, String email, String skinType, String skinTone, String gender, File userProfile) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("facebooksignup")
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("nickname", nickname)
                .addFormDataPart("email", email)
                .addFormDataPart("skinType", skinType)
                .addFormDataPart("skinTone", skinTone)
                .addFormDataPart("gender", gender);

        if (userProfile != null) {
            builder.addFormDataPart("userprofile", userProfile.getName(),
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
        return new TypeToken<NetworkResult<User>>() {
        }.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
