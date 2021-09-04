package com.example.news.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel<*>> :Fragment() {

    private var loader: ProgressDialog? = null

    lateinit var viewModel: VM
    lateinit var binding: DB
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        viewModel = initializeViewModel()

        return binding.root
    }

    abstract fun getLayoutId(): Int
    abstract fun initializeViewModel(): VM

    fun showLoader() {
        loader = ProgressDialog(requireContext())
        loader?.apply { ->
            setMessage("Loading...")
            setCancelable(false)
            show()
        }

    }

    fun hideLoader() {
        if (loader != null && loader?.isShowing!!) {
            loader?.dismiss()
        }
    }
}