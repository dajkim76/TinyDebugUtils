package com.mdiwebma.tinydebugutils

import android.app.Application


internal object ApplicationHolder {

    private var cachedApplication: Application? = null

    @JvmStatic
    val application: Application
        get() {
            return cachedApplication ?: throw RuntimeException("application is null")
        }

    @JvmStatic
    fun init(application: Application) {
        this.cachedApplication = application
    }
}
