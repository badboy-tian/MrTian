package com.i7play.newbtc.base

import android.os.Bundle
import android.view.View
import com.github.jdsjlzx.recyclerview.LRecyclerView
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.i7play.mrtian.base.BaseLazyFragment
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.statelayout_error.view.*

/**
 * Created by Administrator on 2017/10/25.
 */
abstract class ListLazyFragment : BaseLazyFragment() {
    var isStart = true
    var isRefresh = true
    val pageSize = 10
    var pageIndex = 1
    var pageCount = 1

    val datas = ArrayList<Any>()
    override fun fetchData() {
        loadData()
    }

    abstract fun loadData()
    abstract fun getLAdapter(): LRecyclerViewAdapter?
    abstract fun getList(): LRecyclerView
    abstract fun getMultiView(): MultiStateView
    abstract fun handleIntent(arguments: Bundle)

    override fun initView() {
        if (arguments != null) {
            handleIntent(arguments!!)
        }

        initList(getList())
        getList().adapter = getLAdapter()
    }

    override fun initListener() {
        super.initListener()
        getList().setOnRefreshListener {
            pageIndex = 1
            pageCount = 1

            isStart = false
            isRefresh = true
            loadData()
        }

        getList().setOnLoadMoreListener {
            if (pageIndex < pageCount) {
                pageIndex++
                isStart = false
                isRefresh = false
                loadData()
            } else {
                getList().setNoMore(true)
            }
        }
    }

    private fun emputy() {
        getMultiView().viewState = MultiStateView.VIEW_STATE_EMPTY
        getMultiView().refreshBtn.visibility = View.VISIBLE
        getMultiView().refreshBtn.setOnClickListener {
            loading()
            loadData()
        }
    }

    protected fun content() {
        getMultiView().viewState = MultiStateView.VIEW_STATE_CONTENT
    }

    protected fun loading() {
        getMultiView().viewState = MultiStateView.VIEW_STATE_LOADING
    }

    protected fun noNetwork() {
        getMultiView().viewState = MultiStateView.VIEW_STATE_ERROR
        getMultiView().refreshBtn.setOnClickListener {
            loading()
            loadData()
        }
    }

    fun check() {
        getList().refreshComplete(pageSize)
        getLAdapter()?.notifyDataSetChanged()
        if (datas.size == 0) {
            emputy()
        } else {
            content()
        }
    }
}