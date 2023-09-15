package com.z_news.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.z_news.R
import com.z_news.pojo.News
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.news_item.*

class ContentActivity : AppCompatActivity() {
    var news:News = News()
    val gson:Gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        setView()
    }

    private fun setView(){
        news = gson.fromJson(intent.getStringExtra("data"), News::class.java)

        title_content.text = news.title
        Glide.with(this).load(news.imgsrc).into(img_content)
        digest_content.text = news.digest
        tname_content.text = news.tname

        enter_content.setOnClickListener(View.OnClickListener {
            Log.d("debug","click+")
            val intent:Intent = Intent();
            intent.action = "android.intent.action.VIEW"
            val uri:Uri = Uri.parse(news.url)
            intent.data = uri
            try {
                startActivity(intent)
            } catch (e:Exception){
                Log.d("debug",e.toString())
            }

        })
    }
}