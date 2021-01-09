package com.example.news.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageURL")
fun loadImage(image:ImageView,url:String){
Picasso.get().load(url).into(image)
}