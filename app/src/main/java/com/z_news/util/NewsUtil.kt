package com.z_news.util

class NewsUtil {
    val newsNum:Int = 20

    fun getUrlByTag(tag:String):String{
        var add:String = "T1348649580692"
        if (tag=="科技"){
            add = "T1348649580692"
        } else if (tag=="体育"){
            add = "T1348649079062"
        } else if (tag=="头条"){
            add = "T1348647909107"
        } else if (tag=="历史"){
            add = "T1368497029546"
        } else if (tag=="军事"){
            add = "T1348648141035"
        } else if (tag=="要闻"){
            add = "T1467284926140"
        }
        return formatUrl(add)
    }

    fun getCodeByTag(tag:String):String{
        var add:String = "T1348649580692"
        if (tag=="科技"){
            add = "T1348649580692"
        } else if (tag=="体育"){
            add = "T1348649079062"
        } else if (tag=="头条"){
            add = "T1348647909107"
        } else if (tag=="历史"){
            add = "T1368497029546"
        } else if (tag=="军事"){
            add = "T1348648141035"
        } else if (tag=="要闻"){
            add = "T1467284926140"
        }
        return add
    }

    fun formatUrl(add:String):String{
        return "http://c.m.163.com/nc/article/headline/$add/0-20.html"
    }
}