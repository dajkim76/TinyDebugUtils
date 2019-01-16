package com.mdiwebma.library

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build


object ApplicationHolder {
    const val CHANNEL_NAME = "Debug!"

    @JvmStatic
    var application: Application? = null
        get() {
            return field ?: throw RuntimeException("application is null")
        }

    @JvmStatic
    fun init(application: Application) = apply {
        this.application = application

        createDefaultNotificationChannel(application)
        LiveActivityHolder.init(application)
        CrashHandler.init()
    }

    private fun createDefaultNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.packageName,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }
}
