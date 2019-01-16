package com.mdiwebma.library

import android.app.Activity
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast


internal object DebugMessageHandler {

    private const val WHAT_SHOW_TEXT = 0
    private const val WHAT_SHOW_TOAST = 1
    private const val WHAT_SHOW_NOT_REACHED = 2
    private const val WHAT_SHOW_NOTIFY = 3

    private val handler = MessageHandler(Looper.getMainLooper())

    class MessageHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                WHAT_SHOW_TEXT -> alertImpl(msg.obj as? String)
                WHAT_SHOW_TOAST -> toastImpl(msg.obj as? String)
                WHAT_SHOW_NOT_REACHED -> notReachedImpl(msg.obj as? String)
                WHAT_SHOW_NOTIFY -> notifyImpl(msg.obj as? String)
            }
        }
    }

    fun alert(message: String?) {
        val msg = handler.obtainMessage()
        msg.what = WHAT_SHOW_TEXT
        msg.obj = message
        handler.sendMessage(msg)
    }

    private fun alertImpl(message: String?) {
        val activity = LiveActivityHolder.liveActivity ?: return toastImpl(message)
        AlertDialog.Builder(activity)
            .setTitle("Debug!")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK", null)
            .show()
    }

    fun toast(message: String?) {
        val msg = handler.obtainMessage()
        msg.what = WHAT_SHOW_TOAST
        msg.obj = message
        handler.sendMessage(msg)
    }

    private fun toastImpl(message: String?) {
        Toast.makeText(ApplicationHolder.application, message, Toast.LENGTH_SHORT).show()
    }

    fun notReached(message: String?) {
        val msg = handler.obtainMessage()
        msg.what = WHAT_SHOW_NOT_REACHED
        msg.obj = message
        handler.sendMessage(msg)
    }

    private fun notReachedImpl(message: String?) {
        // TODO make notification
        val activity = LiveActivityHolder.liveActivity ?: return toastImpl(message)
        AlertDialog.Builder(activity)
            .setTitle("Debug!")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK", null)
            .show()
    }

    fun notify(message: String) {
        val msg = handler.obtainMessage()
        msg.what = WHAT_SHOW_NOTIFY
        msg.obj = message
        handler.sendMessage(msg)
    }

    private fun notifyImpl(message: String?) {
        val context = ApplicationHolder.application!!.applicationContext
        val intent =
            DebugMessageViewerActivity.createIntent(context, message)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, context.packageName)
        } else {
            Notification.Builder(context)
        }

        val notification = builder
            .setSmallIcon(R.drawable.ic_stat_clear)
            .setTicker("Debug!")
            .setContentTitle("Debug!")
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager =
            context.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(message.hashCode(), notification)
    }
}
