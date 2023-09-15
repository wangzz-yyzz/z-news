package com.z_news.dao

import android.content.Context
import android.util.Log
import com.z_news.orm.OrmDbHelper
import com.z_news.pojo.News

class NewsDao(context: Context) {
    private val helper = OrmDbHelper()
    private val liteOrm = helper.getInstance(context)

    fun insertNews(list:ArrayList<News>){
        Log.d("debug","save data")
        liteOrm.save(list)
    }

    fun selectNewsAll():ArrayList<News>{
        Log.d("debug","select data")
        return liteOrm.query(News::class.java)
    }
}