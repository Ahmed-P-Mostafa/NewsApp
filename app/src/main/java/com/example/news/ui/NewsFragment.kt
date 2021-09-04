package com.example.news.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentTransaction
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


    var adapter = NewsAdapter(null)
    private val TAG = "SportsFragment"

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search by source name"
        searchView.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        searchView.textDirection = View.TEXT_DIRECTION_ANY_RTL
        searchView.setOnQueryTextListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: fragment Created")

        binding.newsRecycler.adapter = adapter

        viewModel.getArticles()
        observe()

        /*adapter.onItemClickListener(object : NewsAdapter.onItemClickListener {

            override fun onItemClicked(item: ArticlesItem, position: Int) {

                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                val detailsFragment = DetailsFragment()

                val bundle = Bundle()
                val article = item
                bundle.putSerializable("Article", article)
                detailsFragment.arguments = bundle
                ft.replace(R.id.fragment, detailsFragment)
                ft.addToBackStack(null)
                ft.commit()
            }

        })*/
    }

    private fun observe() {
        viewModel.articlesLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Loading -> {
                    handleLoader(true)
                }
                is DataState.Success -> {
                    Log.d(TAG, "observers: ${it.data.totalResults}")
                    handleLoader(false)
                    adapter.changeData(it.data.articles!!)
                    viewModel.articleList.value = it.data.articles
                }
                is DataState.Failed -> {
                    binding.noDataIv.visibility = View.VISIBLE
                    handleLoader(false)

                    when (it.error) {
                        is Failure.ServerError.AccessDenied -> showError("Server Access Denied !")

                        is Failure.ServerError.NotFound -> showError("Server not fount !")

                        is Failure.ServerError.ServerUnavailable -> showError("Server unavailable !")

                        is Failure.NetworkConnection -> showError("No internet connection !")

                        is Failure.UnknownError -> showError("Something went wrong !")
                    }
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d(TAG, "onQueryTextSubmit: $query")
        if (query != null) searchInArticles(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "onQueryTextChange: $newText")
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

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }


}