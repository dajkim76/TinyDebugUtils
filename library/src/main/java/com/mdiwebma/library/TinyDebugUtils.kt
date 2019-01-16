package com.mdiwebma.library

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object TinyDebugUtils {

    @JvmStatic
    fun init(application: Application) {
        ApplicationHolder.init(application)
        createDefaultNotificationChannel(application)
        LiveActivityHolder.init(application)
        CrashHandler.init()
    }

    private fun createDefaultNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getChannelId(context),
                "Debug!",
                NotificationManager.IMPORTANCE_LOW
            )
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    private fun getChannelId(context: Context) = context.packageName + ".debug"

    @JvmStatic
    fun createNotificationBuilder(context: Context): Notification.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, getChannelId(context))
        } else {
            Notification.Builder(context)
        }
    }
}
