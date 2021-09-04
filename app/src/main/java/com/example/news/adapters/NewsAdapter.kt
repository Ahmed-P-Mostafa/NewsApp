package com.example.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.databinding.NewsItemBinding
import com.example.news.newsApi.model.ArticlesItem


class NewsAdapter(private var articlesList: List<ArticlesItem?>?) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

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
        val item = articlesList?.get(position)
        holder.bind(item)
    }

    override fun getItemCount() = articlesList?.size ?: 0


    class ViewHolder(private val viewBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(news: ArticlesItem?) {
            viewBinding.news = news
            viewBinding.invalidateAll()
        }
    }

    fun changeData(list: List<ArticlesItem?>) {
        this.articlesList = list
        notifyDataSetChanged()
    }

}
