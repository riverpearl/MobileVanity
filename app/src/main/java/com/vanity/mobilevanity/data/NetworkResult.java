package com.vanity.mobilevanity.data;

/**
 * Created by Tacademy on 2016-08-29.
 */
public class NetworkResult<T> {
    private T result;
    private int code;

    public T getResult() {
        return this.result;
    }
    public int getCode() {
        return this.code;
    }
}
