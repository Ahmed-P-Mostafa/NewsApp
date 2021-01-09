package com.example.news.newsApi.model

import com.google.gson.annotations.SerializedName

open class BaseResponse(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
