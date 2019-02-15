package com.mdiwebma.library

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import java.text.DateFormat
import java.util.Date
import java.util.Locale


internal object CrashHandler {

    private val defaultUncaughtHandler = Thread.getDefaultUncaughtExceptionHandler()

    @JvmStatic
    fun init() {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            val context = ApplicationHolder.application
            val contentText = getContentText(context, throwable)
            val intent =
                DebugMessageViewerActivity.createIntent(context, "Crashed!", contentText)
            val pendingIntent =
                PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val notification = TinyDebugUtils.createNotificationBuilder(context)
                .setSmallIcon(R.drawable.ic_stat_clear)
                .setContentTitle("Crashed!")
                .setContentText(contentText)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            FileLog.canWrite = true
            FileLog.write("Crashed!", contentText)

            val notificationManager =
                context.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(contentText.hashCode(), notification)

            defaultUncaughtHandler.uncaughtException(thread, throwable)
        }
    }

    private fun getContentText(context: Context, throwable: Throwable): String {
        val sb = StringBuilder()
        // Date time
        sb.append(DateFormat.getDateTimeInstance().format(Date(System.currentTimeMillis())))
            .append("\n")
        // phone info
        sb.append(Build.MANUFACTURER).append(" ")
            .append(Build.MODEL).append(" ")
            .append(Build.DISPLAY).append("\n")
        // app info
        try {
            sb.append("App: ").append(context.packageName).append("\n")
            sb.append(Utils.getVersionBuildInfo().replace('\t', '\n')).append("\n")
            sb.append("OS Version:").append(Build.VERSION.SDK_INT).append("\n")
            sb.append("OS Locale: ").append(Locale.getDefault().toString()).append("\n\n")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return sb.toString() + Log.getStackTraceString(throwable)
    }
}

