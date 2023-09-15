package com.z_news.pojo

import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.enums.AssignType

@Table("news")
class News{
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    var id:Int = 1
    var title:String="title"
    var digest:String="digest"
    var imgsrc:String="imgsrc"
    var tname:String="tname"
    var url:String="url"
}
