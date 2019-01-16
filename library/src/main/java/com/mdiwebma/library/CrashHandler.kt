package com.mdiwebma.library

import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build

internal object CrashHandler {

    private val defaultUncaughtHandler = Thread.getDefaultUncaughtExceptionHandler()

    @JvmStatic
    fun init() {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            val context = ApplicationHolder.application!!.applicationContext
            val stackTraceString = StackTracer.getStackTraceString(throwable.stackTrace)
            val intent =
                DebugMessageViewerActivity.createIntent(context, stackTraceString)
            val pendingIntent =
                PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(context, context.packageName)
            } else {
                Notification.Builder(context)
            }

            val notification = builder
                .setSmallIcon(R.drawable.ic_stat_clear)
                .setTicker("Debug!")
                .setContentTitle("Crashed!")
                .setContentText(stackTraceString)
                .setContentIntent(pendingIntent)
                .build()

            val notificationManager =
                context.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(stackTraceString.hashCode(), notification)

            defaultUncaughtHandler.uncaughtException(thread, throwable)
        }
    }
}

