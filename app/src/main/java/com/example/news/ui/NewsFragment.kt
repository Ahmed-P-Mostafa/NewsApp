package com.example.news.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.news.R
import com.example.news.adapters.NewsAdapter
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentNewsBinding
import com.example.news.newsApi.model.ArticlesItem
import com.example.news.pojo.models.DataState
import com.example.news.pojo.models.Failure
import com.example.news.pojo.observeOnce
import com.example.news.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel>(),
    SearchView.OnQueryTextListener {

    private val STATE_KEY = "STATE"

    var adapter = NewsAdapter()
    private val TAG = "NewsFragment"

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_KEY, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null && viewModel.savedInstanceState) {
            Log.d(TAG, "onViewCreated: save instance is null")
            viewModel.getArticles()
        }
        setHasOptionsMenu(true)

        binding.newsRecycler.adapter = adapter
        observe()
    }

    private fun observe() {
        viewModel.articlesLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Loading -> {
                    handleLoader(true)
                }
                is DataState.Success -> {
                    handleLoader(false)
                    if (it.data.articles!=null){
                        adapter.changeData(it.data.articles)
                        viewModel.articleList.value = it.data.articles
                    }else{
                        handleError(Failure.ServerError.NotFound)
                        binding.noDataIv.visibility = View.VISIBLE
                    }
                }
                is DataState.Failed -> {
                    binding.noDataIv.visibility = View.VISIBLE
                    handleLoader(false)
                    handleError(it.error)
                }
            }
        })

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) searchInArticles(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) searchInArticles(newText)

        return true
    }

    private fun searchInArticles(text: String) {
        viewModel.searchInArticles(text).observeOnce(viewLifecycleOwner, {
            adapter.changeData(it)
        })
    }

    override fun getLayoutId() = R.layout.fragment_news

    override fun initializeViewModel(): NewsViewModel {
        return ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    private fun handleLoader(state: Boolean) {
        when (state) {
            true -> binding.progressBar.visibility = View.VISIBLE
            false -> binding.progressBar.visibility = View.GONE
        }
    }

    private fun handleError(error: Failure) {
        when (error) {
            is Failure.ServerError.AccessDenied -> showError("Server Access Denied !")

            is Failure.ServerError.NotFound -> showError("Server not fount !")

            is Failure.ServerError.ServerUnavailable -> showError("Server unavailable !")

            is Failure.NetworkConnection -> showError("No internet connection !")

            is Failure.UnknownError -> showError("Something went wrong !")
        }
    }


    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()

    }

}