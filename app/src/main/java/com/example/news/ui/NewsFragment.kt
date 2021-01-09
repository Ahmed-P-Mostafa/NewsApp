package com.example.news.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.news.R
import com.example.news.adapters.NewsAdapter
import com.example.news.databinding.FragmentNewsBinding
import com.example.news.newsApi.model.ArticlesItem
import com.example.news.newsApi.model.Source
import com.example.news.viewModel.NewsViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {


    var adapter = NewsAdapter(null)
    private val TAG = "SportsFragment"
    val viewModel: NewsViewModel by viewModels()
    lateinit var newsBinding :FragmentNewsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.fragment_news, container, false)
         newsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_news,container,false)
        
        return newsBinding.getRoot()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: fragment Created")

        newsBinding.newsRecycler.adapter = adapter

        viewModel.getSourcesResponse()
        viewModel.sourcesLiveData.observe(viewLifecycleOwner, {
            showTabs(it)
        })

        adapter.onItemClickListener(object : NewsAdapter.onItemClickListener {

            override fun onItemClicked(item: ArticlesItem, position: Int) {

                val ft :FragmentTransaction = parentFragmentManager.beginTransaction()

                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                 val detailsFragment = DetailsFragment()

                val bundle = Bundle()
                val article = item
                bundle.putSerializable("Article",article)
                detailsFragment.arguments = bundle
                ft.replace(R.id.fragment,detailsFragment)
                ft.addToBackStack(null)
                ft.commit()
            }

        })
    }

    private fun showTabs(list: List<Source?>?) {
        Log.d(TAG, "showTabs: ")
        list?.forEach { item ->
            val tab = newsBinding.tabLayout.newTab()
            tab.setText(item?.name)
            tab.setTag(item)
            newsBinding.tabLayout.addTab(tab)
        }
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer {
            adapter.changeData(it)
            //newsBinding.progressBar.visibility = View.GONE
        })
        val source = newsBinding.tabLayout.getTabAt(0)?.tag as Source
        viewModel.getNewsResponse(source.id.toString(),source.language.toString())

        newsBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                newsBinding.progressBar.visibility = View.VISIBLE
                Log.d(TAG, "onTabSelected: ${tab?.text}")


                val source = tab?.tag as Source

                viewModel.getNewsResponse(source.id.toString(),source.language.toString())
                viewModel.newsLiveData.observe(viewLifecycleOwner,{
                    //progressBar.visibility = View.GONE
                    adapter.changeData(it)
                })

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
               // newsBinding.progressBar.visibility = View.VISIBLE
                Log.d(TAG, "onTabReselected: ${tab?.text.toString()}")

                val source = tab?.tag as Source
                viewModel.getNewsResponse(source.id.toString(),source.language.toString())
                viewModel.newsLiveData.observe(viewLifecycleOwner,{
                    //newsBinding.progressBar.visibility = View.GONE
                    adapter.changeData(it)
                })
            }
        })
    }


}