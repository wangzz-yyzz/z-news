package com.z_news.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.z_news.R
import com.z_news.pojo.News

class NewsAdapter(private val newsList: List<News>, val context: Context): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val newsTitle: TextView = view.findViewById(R.id.news_title)
        val newsImage: ImageView = view.findViewById(R.id.news_img)
    }

    private var mItemClickListener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(itemClickListener: ItemClickListener?) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsList[position]
        holder.newsTitle.text = news.title
        Glide.with(context).load(news.imgsrc).into(holder.newsImage)

        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener { // 这里利用回调来给RecyclerView设置点击事件
                mItemClickListener!!.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}