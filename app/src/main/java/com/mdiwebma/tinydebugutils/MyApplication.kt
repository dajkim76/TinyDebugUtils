package com.mdiwebma.tinydebugutils

import android.app.Application
import com.mdiwebma.library.TinyDebugUtils

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            TinyDebugUtils.init(this)
        }
    }
}
