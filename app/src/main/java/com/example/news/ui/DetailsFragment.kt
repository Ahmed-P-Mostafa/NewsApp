package com.example.news.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.news.R
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentDetailsBinding
import com.example.news.newsApi.model.ArticlesItem
import com.example.news.viewModel.NewsViewModel

private const val ARTICLE = "param1"


class DetailsFragment : BaseFragment<FragmentDetailsBinding,NewsViewModel>() {
    private val TAG = "DetailsFragment"

    private val args :DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        binding.news = args.article

    }

    override fun getLayoutId()= R.layout.fragment_details

    override fun initializeViewModel(): NewsViewModel {
        return ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
    }


}