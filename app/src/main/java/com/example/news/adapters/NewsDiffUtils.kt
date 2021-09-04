package com.example.news.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.news.newsApi.model.ArticlesItem

class NewsDiffUtils(
    private val newList: List<ArticlesItem>,
    private val oldList: List<ArticlesItem>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }
}