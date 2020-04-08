package com.akrwt.impulse.News

class NewsModel(
    mDate: String,
    mNews: String
) {

    constructor() : this("", "")

    private var date = mDate
    private var news = mNews


    fun getDate(): String {
        return date
    }

    fun setDate(d: String) {
        date = d
    }

    fun getNews(): String {
        return news
    }

    fun setNews(n: String) {
        news = n
    }

}