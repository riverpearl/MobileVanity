package com.vanity.mobilevanity.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.vanity.mobilevanity.data.BeautyTip;
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
public class InsertBeautyTipRequest extends AbstractRequest<NetworkResult<BeautyTip>> {
    MediaType jpeg = MediaType.parse("image/jpeg");
    Request mRequest;

    public InsertBeautyTipRequest(Context context, String title, String content, File image) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("insertbeautytip")
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", title)
                .addFormDataPart("content", content);

        if (image != null) {
            builder.addFormDataPart("image", image.getName(),
                    RequestBody.create(jpeg, image));
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
        return new TypeToken<NetworkResult<BeautyTip>>() {}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}
