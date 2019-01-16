package com.mdiwebma.library

import android.app.Application


internal object ApplicationHolder {

    @JvmStatic
    var application: Application? = null
        get() {
            return field ?: throw RuntimeException("application is null")
        }

    @JvmStatic
    fun init(application: Application) {
        this.application = application
    }
}
