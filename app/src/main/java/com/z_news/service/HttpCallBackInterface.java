package com.z_news.service;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public interface HttpCallBackInterface extends Callback {

    public void onFailure(Call call, IOException e);

    public void onResponse(Call call, Response response) throws IOException;
}
