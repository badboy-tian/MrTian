package com.i7play.mrtian.utils
import android.content.Context


/**
 * Created by Administrator on 2017/8/15.
 * 本地数据保存类
 */

open class SPUtils {
    private lateinit var context: Context
    private fun setContext(context: Context) {
        this.context = context
    }

    fun currentID(): String? {
        return SharedPrefsUtils.getStringPreference(context, "currentId")
    }

    fun currentID(currentID: String) {
        SharedPrefsUtils.setStringPreference(context, "currentId", currentID)
    }

    companion object {
        private var ins: SPUtils? = null

        fun init(context: Context) {
            SPUtils.getInstance().setContext(context)
        }

        fun getInstance(): SPUtils {
            if (ins == null) {
                ins = SPUtils()
            }
            return ins!!
        }
    }
}
