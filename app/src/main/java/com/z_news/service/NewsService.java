package com.z_news.service;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.z_news.R;
import com.z_news.pojo.News;
import com.z_news.util.NewsUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsService extends Application {
    Gson gson = new Gson();
    NewsUtil newsUtil = new NewsUtil();

    public void getAllNews(String tag, HttpCallBackInterface callBack) {
        Log.d("debug", "getting data");
        String url = newsUtil.getUrlByTag(tag);
        OkHttpClient okHttpClient = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        //第三步构建Call对象
        final Call call = okHttpClient.newCall(request);
        call.enqueue(callBack);
    }

    public ArrayList<News> parseData(Response response, String tag) throws IOException {
        ArrayList<News> list = new ArrayList<>();
        String result = response.body().string();
        JsonParser parser = new JsonParser();

        JsonElement element = parser.parse(result);
        JsonObject object = element.getAsJsonObject();  // 转化为对象
        JsonArray newsArray = object.get(newsUtil.getCodeByTag(tag)).getAsJsonArray();

        Log.d("debug", "thread in");
        // 装填数据
        for (int i = 0; i < newsArray.size(); i++) {
            News news = new News();
            JsonObject newsObj = newsArray.get(i).getAsJsonObject();

            try {
                news.setTitle(newsObj.get("title").getAsString());
                news.setDigest(newsObj.get("digest").getAsString());
                news.setImgsrc(newsObj.get("imgsrc").getAsString());
                news.setTname(tag);
            } catch (Exception e){
                Log.d("debug", e.toString());
            }

            try {
                news.setUrl(newsObj.get("url").getAsString());
            } catch (NullPointerException e) {
                news.setUrl("");
                Log.d("debug", i + " no url");
            }
            list.add(news);
        }
        return list;
    }
}
