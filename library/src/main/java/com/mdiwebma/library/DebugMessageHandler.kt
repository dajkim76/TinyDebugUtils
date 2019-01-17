package com.mdiwebma.library

import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast


internal object DebugMessageHandler {

    private const val WHAT_ALERT = 0
    private const val WHAT_TOAST = 1
    private const val WHAT_EXCEPTION = 2
    private const val WHAT_NOTIFY = 3

    private val handler = MessageHandler(Looper.getMainLooper())

    class Data {
        var title = "Debug!"
        var message: String?

        constructor(message: String?) {
            this.message = message
        }

        constructor(message: String?, title: String) {
            this.message = message
            this.title = title
        }
    }

    class MessageHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                WHAT_ALERT -> alertImpl(msg.obj as Data)
                WHAT_TOAST -> toastImpl(msg.obj as Data)
                WHAT_EXCEPTION -> exceptionImpl(msg.obj as Data)
                WHAT_NOTIFY -> notifyImpl(msg.obj as Data)
            }
        }
    }

    fun checkState(message: String) {
        val msg = handler.obtainMessage()
        msg.what = WHAT_EXCEPTION
        msg.obj = Data(message, "CheckState!")
        handler.sendMessage(msg)

        FileLog.write("CheckState!", message)
    }

    fun checkNotNull(message: String) {
        val msg = handler.obtainMessage()
        msg.what = WHAT_EXCEPTION
        msg.obj = Data(message, "CheckNotNull!")
        handler.sendMessage(msg)

        FileLog.write("CheckNotNull!", message)
    }

    fun alert(message: String?) {
        val msg = handler.obtainMessage()
        msg.what = WHAT_ALERT
        msg.obj = Data(message, "Alert!")
        handler.sendMessage(msg)
    }

    private fun alertImpl(data: Data) {
        val activity = LiveActivityHolder.liveActivity ?: return toastImpl(data)
        AlertDialog.Builder(activity)
            .setTitle(data.title)
            .setMessage(data.message)
            .setCancelable(false)
            .setPositiveButton("OK", null)
            .show()
    }

    fun toast(message: String?) {
        val msg = handler.obtainMessage()
        msg.what = WHAT_TOAST
        msg.obj = Data(message)
        handler.sendMessage(msg)
    }

    private fun toastImpl(data: Data) {
        Toast.makeText(ApplicationHolder.application, data.message, Toast.LENGTH_SHORT).show()
    }

    fun exception(message: String?) {
        val msg = handler.obtainMessage()
        msg.what = WHAT_EXCEPTION
        msg.obj = Data(message, "Exception!")
        handler.sendMessage(msg)

        FileLog.write("Exception!", message)
    }

    private fun exceptionImpl(data: Data) {
        if (LiveActivityHolder.liveActivity != null) {
            AlertDialog.Builder(LiveActivityHolder.liveActivity)
                .setTitle(data.title)
                .setMessage(data.message)
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .show()
        } else {
            toast(data.title + "\n\n" + data.message)
            notifyImpl(data)
        }
    }

    fun notify(message: String?) {
        val msg = handler.obtainMessage()
        msg.what = WHAT_NOTIFY
        msg.obj = Data(message, "Info!")
        handler.sendMessage(msg)
    }

    private fun notifyImpl(data: Data) {
        val context = ApplicationHolder.application!!.applicationContext
        val intent =
            DebugMessageViewerActivity.createIntent(context, data.title, data.message)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = TinyDebugUtils.createNotificationBuilder(context)
            .setSmallIcon(R.drawable.ic_stat_clear)
            .setContentTitle(data.title)
            .setContentText(data.message)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager =
            context.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(data.message.hashCode(), notification)
    }
}
