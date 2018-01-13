package com.i7play.mrtian.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.LocaleList
import android.util.Log
import android.webkit.WebView

import java.util.Locale

/**
 * Created by tian on 2017/12/27.
 */

object I18NUtils {

    private val TAG = "I18NUtils"
    private val thLocale = Locale("th")

    /**
     * 设置本地化语言
     *
     * @param context
     * @param type
     */
    fun setLocale(context: Context, type: Int) {
        // 解决webview所在的activity语言没有切换问题
        WebView(context).destroy()
        // 切换语言
        val resources = context.resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        config.locale = getLocaleByType(type)
        Log.d(TAG, "setLocale: " + config.locale.toString())
        resources.updateConfiguration(config, dm)
    }

    /**
     * 根据type获取locale
     *
     * @param type
     * @return
     */
    private fun getLocaleByType(type: Int): Locale {
        val locale: Locale
        // 应用用户选择语言
        when (type) {
            0 ->
                //由于API仅支持7.0，需要判断，否则程序会crash(解决7.0以上系统不能跟随系统语言问题)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val localeList = LocaleList.getDefault()
                    locale = localeList.get(localeList.size() - 1)
                } else {
                    locale = Locale.getDefault()
                }
            2 -> locale = Locale.ENGLISH
            1 -> locale = Locale.CHINESE
            else -> locale = thLocale
        }
        return locale
    }

    /**
     * 根据sp数据设置本地化语言
     *
     * @param context
     */
    fun setLocale(context: Context) {
        val type = getLanguageType(context)
        setLocale(context, type)
    }

    /**
     * 判断是否是相同语言
     * @param context
     * @return
     */
    fun isSameLanguage(context: Context): Boolean {
        val type = getLanguageType(context)
        return isSameLanguage(context, type)
    }

    /**
     * 判断是否是相同语言
     * @param context
     * @param type
     * @return
     */
    fun isSameLanguage(context: Context, type: Int): Boolean {
        val locale = getLocaleByType(type)
        val appLocale = context.resources.configuration.locale
        val equals = appLocale == locale
        Log.d(TAG, "isSameLanguage: " + locale.toString() + " / " + appLocale.toString() + " / " + equals)
        return equals
    }

    /**
     * sp存储本地语言类型
     *
     * @param context
     * @param type
     */
    fun putLanguageType(context: Context, type: Int) {
        val sp = context.getSharedPreferences(AppConstants.I18N, Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putInt(AppConstants.LOCALE_LANGUAGE, type)
        edit.commit()
    }

    /**
     * sp获取本地存储语言类型
     *
     * @param context
     * @return
     */
    public fun getLanguageType(context: Context): Int {
        val sp = context.getSharedPreferences(AppConstants.I18N, Context.MODE_PRIVATE)
        return sp.getInt(AppConstants.LOCALE_LANGUAGE, 0)
    }

    /**
     * 跳转主页
     * @param activity
     */
    fun toRestartMainActvity(activity: Activity) {
        /*ActivityManager.finishAllActivity()
        val intent = Intent(activity, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intent)*/
    }
}