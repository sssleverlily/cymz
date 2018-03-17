package com.zia.gankcqupt_mvp.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by zia on 2017/5/18.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(final String address, final okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
