package com.example.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.databinding.NewsItemBinding
import com.example.news.newsApi.model.ArticlesItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class NewsAdapter(list: ArrayList<ArticlesItem?>?) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val TAG = "NewsAdapter"
    var list = list



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*val view:View = LayoutInflater.from(parent.context).inflate(
            R.layout.news_item,
            parent,
            false
        )*/

        val viewBinding:NewsItemBinding = DataBindingUtil.inflate<NewsItemBinding>(LayoutInflater.from(parent.context),R.layout.news_item,parent,false)

        return ViewHolder(viewBinding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item  = list?.get(position)

        holder.bind(item)


        val inputFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val outputFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH)

        val date: LocalDate = LocalDate.parse(list?.get(position)?.publishedAt, inputFormatter)
        val formattedDate: String = outputFormatter.format(date)

        val input: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.ENGLISH)

        var time :Date? = null
        time = input.parse(list?.get(position)?.publishedAt)
        var simpleTimeFormatter = SimpleDateFormat("hh:mm a",Locale.ENGLISH)
        var formattedTime = simpleTimeFormatter.format(time)
        holder.viewBinding.itemTime.setText(formattedTime)
        holder.viewBinding.itemDate.setText(formattedDate)
        holder.viewBinding.news = item




        //Picasso.get().load(list?.get(position)?.urlToImage).into(holder.viewBinding.itemImage)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(item!!, position)
        }
    }

    override fun getItemCount() = list?.size?:0


    class ViewHolder(val viewBinding: NewsItemBinding):RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(news:ArticlesItem?){
            viewBinding.news = news
            viewBinding.invalidateAll()

        }
    }

    fun changeData(list: ArrayList<ArticlesItem?>){
        this.list = list
        notifyDataSetChanged()
    }

    interface onItemClickListener{
        fun onItemClicked(item: ArticlesItem, position: Int)
    }

    var listener:onItemClickListener?= null

    fun onItemClickListener(listener: onItemClickListener){
        this.listener = listener
    }


}
