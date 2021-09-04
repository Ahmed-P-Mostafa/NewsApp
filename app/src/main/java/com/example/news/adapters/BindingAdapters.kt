package com.example.news.adapters

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.news.newsApi.model.ArticlesItem
import com.example.news.ui.NewsFragmentDirections
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@BindingAdapter("imageURL")
fun loadImage(image: ImageView, url: String?) {
    if (url!=null)
        Picasso.get().load(url).into(image)
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("renderDate")
fun renderDate(textView: TextView, dateString: String) {
    val inputFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    val outputFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH)

    val date: LocalDate = LocalDate.parse(dateString, inputFormatter)
    val formattedDate: String = outputFormatter.format(date)
    textView.setText(formattedDate)
}

@BindingAdapter("renderTime")
fun renderTime(textView: TextView,timeString:String) {

    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.ENGLISH)
    val time = input.parse(timeString)
    val simpleTimeFormatter = SimpleDateFormat("hh:mm a",Locale.ENGLISH)
    val formattedTime = simpleTimeFormatter.format(time)
    textView.setText(formattedTime)
}

@BindingAdapter("android:goToDetails")
fun sendDataToUpdateFragment(layout: ConstraintLayout, articlesItem: ArticlesItem){
    layout.setOnClickListener {
        val action = NewsFragmentDirections.actionHomeFragmentToDetailsFragment(articlesItem)
        layout.findNavController().navigate(action)

    }

}