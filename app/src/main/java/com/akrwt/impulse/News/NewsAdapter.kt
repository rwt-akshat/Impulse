package com.akrwt.impulse.News

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.akrwt.impulse.R


class NewsAdapter(
    var mContext: Context,
    private var mNews: ArrayList<NewsModel>
) :
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var newsTitle: TextView? = null
        internal var newsDes: TextView? = null


        init {
            this.newsTitle = itemView.findViewById(R.id.newsT)
            this.newsDes = itemView.findViewById(R.id.newsD)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false)

        return MyViewHolder(v)

    }

    override fun getItemCount(): Int {
        return mNews.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val newsCurrent = mNews.get(position)

        val title = newsCurrent.getNews()
        val date = newsCurrent.getDate()



        holder.newsTitle!!.text = title
        holder.newsDes!!.text = date


    }
}