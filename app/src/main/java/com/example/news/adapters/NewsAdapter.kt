package com.example.news.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.databinding.NewsItemBinding
import com.example.news.newsApi.model.ArticlesItem


class NewsAdapter(private var list: List<ArticlesItem?>?) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val TAG = "NewsAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewBinding: NewsItemBinding = DataBindingUtil.inflate<NewsItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.news_item,
            parent,
            false
        )

        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = list?.get(position)

        holder.bind(item)
        /*holder.itemView.setOnClickListener {
            listener?.onItemClicked(item!!, position)
        }*/
    }

    override fun getItemCount() = list?.size ?: 0


    class ViewHolder(val viewBinding: NewsItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(news: ArticlesItem?) {
            viewBinding.news = news
            viewBinding.invalidateAll()
        }
    }

    fun changeData(list: List<ArticlesItem?>) {
        this.list = list
        notifyDataSetChanged()
    }

    //TODO remove comments
/*    interface onItemClickListener {
        fun onItemClicked(item: ArticlesItem, position: Int)
    }

    var listener: onItemClickListener? = null

    fun onItemClickListener(listener: onItemClickListener) {
        this.listener = listener
    }*/


}
