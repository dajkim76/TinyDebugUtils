package com.mdiwebma.tinydebugutils

import android.app.Application
import com.mdiwebma.library.ApplicationHolder

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationHolder.init(this)
    }
}
