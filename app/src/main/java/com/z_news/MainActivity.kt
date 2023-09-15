package com.z_news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.z_news.activities.ContentActivity
import com.z_news.adapters.NewsAdapter
import com.z_news.adapters.NewsAdapter.ItemClickListener
import com.z_news.dao.NewsDao
import com.z_news.pojo.News
import com.z_news.service.HttpCallBackInterface
import com.z_news.service.NewsService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.news.*
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener{
    private var list: ArrayList<News> = ArrayList()
    private var newsService = NewsService()
    private var adapter: NewsAdapter = NewsAdapter(list,this)
    var gson = Gson()
    private var currentTag = "科技"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        setClick()
        initDrawer()
    }

    private fun initData() {
        val layoutManager = LinearLayoutManager(this)
        news_list.layoutManager = layoutManager
        // 初始化数据为数据库中的数据
        list = loadData()
        adapter = NewsAdapter(list, this)
        news_list.adapter = adapter

        // 刷新
        main_refresh.setOnRefreshListener { refreshData() }

        refreshData()

        saveData()
    }

    /**
     * 设置item的点击事件
     */
    private fun setClick() {
        adapter.setOnItemClickListener(object : ItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("debug", "click$position")
                // 点击后页面跳转
                val intent = Intent()
                intent.setClass(this@MainActivity, ContentActivity::class.java)
                intent.putExtra("data", gson.toJson(list[position]))
                startActivity(intent)
            }
        })
    }

    private fun refreshData() {
        main_refresh.measure(0,0)
        main_refresh.isRefreshing = true
        var new_list: ArrayList<News>
        newsService.getAllNews(currentTag, object :HttpCallBackInterface{
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("debug", e.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                Log.d("debug", "callback")
                new_list = newsService.parseData(response, currentTag)

                runOnUiThread {
                    list.clear()
                    for (i in new_list){
                        list.add(i)
                    }
                    tag.text = list[0].tname
                    adapter.notifyDataSetChanged()
                    saveData()
                    main_refresh.isRefreshing = false
                    Toast.makeText(this@MainActivity, "Refresh Done", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun initDrawer() {
        nav.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("debug",item.title.toString())
        currentTag = item.title.toString()
        Log.d("debug",item.title.toString())
        drawer_layout.closeDrawers()
        refreshData()
        return true
    }

    private fun saveData(){
        val dao = NewsDao(this)
        dao.insertNews(list)
    }

    private fun loadData():ArrayList<News>{
        val dao = NewsDao(this)
        return dao.selectNewsAll()
    }
}