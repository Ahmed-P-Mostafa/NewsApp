package com.example.news.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.news.R
import com.example.news.databinding.FragmentDetailsBinding
import com.example.news.newsApi.model.ArticlesItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.news_item.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.atan


private const val ARTICLE = "param1"


class DetailsFragment : Fragment() {
    private val TAG = "DetailsFragment"
    lateinit var detailsBinding :FragmentDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)

            //val view = inflater.inflate(R.layout.fragment_details, container, false)
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ")
        
        return detailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")


        val bundle :Bundle = requireArguments()
        val articlesItem = bundle.getSerializable("Article") as ArticlesItem
        detailsBinding.news = articlesItem






        val inputFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val outputFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH)
        Log.d(TAG, "onBindViewHolder: outputFormatter : $outputFormatter")
        val date: LocalDate = LocalDate.parse(articlesItem.publishedAt, inputFormatter)
        val formattedDate: String = outputFormatter.format(date)



        val input: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)



        var time : Date? = null
        time = input.parse(articlesItem.publishedAt)
        val simpleTimeFormatter = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        val formattedTime = simpleTimeFormatter.format(time)

        /*Picasso.get().load(articlesItem.urlToImage).into(detailsBinding.itemImage)
        detailsBinding.itemTitle.setText(articlesItem.title)
        detailsBinding.itemAuthor.setText(articlesItem.author)
        detailsBinding.itemDescription.setText(articlesItem.description)*/
        detailsBinding.itemDate.setText(formattedDate)
        detailsBinding.itemTime.setText(formattedTime)


    }
}