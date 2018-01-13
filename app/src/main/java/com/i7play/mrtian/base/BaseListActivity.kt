package com.i7play.newbtc.base

import android.content.Intent
import android.os.Bundle
import com.github.jdsjlzx.recyclerview.LRecyclerView
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.i7play.mrtian.base.BaseActivity
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.statelayout_error.view.*

/**
 * Created by Administrator on 2017/10/25.
 */
open abstract class BaseListActivity : BaseActivity() {
    var isStart = true
    var isRefresh = true
    val pageSize = 10
    var pageIndex = 1
    var pageCount = 1

    val datas = ArrayList<Any>()


    abstract fun loadData()
    abstract fun getLAdapter(): LRecyclerViewAdapter
    abstract fun getList(): LRecyclerView
    abstract fun getMultiView(): MultiStateView
    abstract fun handleIntent(intent: Intent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(intent)
        loadData()
    }

    protected open fun initView(intent: Intent?) {
        if (intent != null) {
            handleIntent(intent)
        }
        initList(getList())
        getList().adapter = getLAdapter()
    }

    override fun initListener() {
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
        if (getMultiView() == null) return
        getMultiView().viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    protected fun content() {
        if (getMultiView() == null) return
        getMultiView().viewState = MultiStateView.VIEW_STATE_CONTENT
    }

    protected fun loading() {
        if (getMultiView() == null) return
        getMultiView().viewState = MultiStateView.VIEW_STATE_LOADING
    }

    protected fun noNetwork() {
        if (getMultiView() == null) return
        getMultiView().viewState = MultiStateView.VIEW_STATE_ERROR
        getMultiView().refreshBtn.setOnClickListener {
            loading()
            loadData()
        }
    }

    fun check() {
        getList().refreshComplete(pageSize)
        if (datas.size == 0) {
            emputy()
        } else {
            content()
        }
    }
}