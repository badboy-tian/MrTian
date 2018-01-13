package com.i7play.mrtian.base
import android.app.Application
import com.i7play.mrtian.utils.SPUtils
import com.i7play.mrtian.utils.ActivityManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import xiaofei.library.datastorage.database.DatabaseStorage

/**
 * Created by tian on 2017/11/29.
 */
abstract class App : Application() {
    lateinit var db: DatabaseStorage
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ActivityManager.init(this)
        SPUtils.init(this)
        db = DatabaseStorage.getInstance(this)
        Logger.addLogAdapter(AndroidLogAdapter())
        //CrashReport.initCrashReport(applicationContext, "3ac22cf9ae", true)
    }

    abstract fun crashId(): Int
}