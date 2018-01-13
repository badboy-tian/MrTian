package com.i7play.mrtian.utils

import com.i7play.mrtian.BuildConfig
import com.orhanobut.logger.Logger

/**
 * Created by Administrator on 2017/7/1.
 * 打印类
 */

object LogHelper {
    private val DEBUG = BuildConfig.DEBUG
    private val TAG = "com.i7play.newbtc"

    fun LogE(`object`: Any) {
        if (DEBUG){
            Logger.e(`object`.toString())
        }
    }

    fun LogE(cls: Class<*>, `object`: Any) {
        if (DEBUG)
            Logger.e(TAG + " " + cls.name + " : " + `object`.toString())
    }
}
