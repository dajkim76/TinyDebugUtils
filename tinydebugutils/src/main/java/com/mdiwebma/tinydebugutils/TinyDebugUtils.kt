package com.mdiwebma.tinydebugutils

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object TinyDebugUtils {

    @JvmStatic
    fun init(application: Application, config: Config? = null) {
        ApplicationHolder.init(application)
        createDefaultNotificationChannel(application)
        LiveActivityHolder.init(application)
        if (config == null || config.crashHandlerEnable) {
            CrashHandler.init()
        }
        initDebugConfig()
        applyConfig(config)
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

    class Config {
        internal var debugUtilsEnable: Boolean? = null
        internal var fileLogEnable: Boolean? = null
        internal var debugLogEnable: Boolean? = null
        internal var debugLogWriteToFile: Boolean? = null
        internal var debugLogLevel: Int? = null
        internal var serverLogEnable: Boolean? = null
        internal var serverLogSamplingPercent: Float? = null
        internal var serverLogHandler: ServerLogHandler? = null
        internal var crashHandlerEnable: Boolean = true
        internal var buildTimeStamp: Long? = null
        internal var buildGitBranchName: String? = null
        internal var assertUtilsEnable: Boolean? = null

        fun debugUtilsEnable(enable: Boolean) = apply {
            debugUtilsEnable = enable
        }

        fun fileLogEnable(enable: Boolean) = apply {
            fileLogEnable = enable
        }

        fun debugLogEnable(enable: Boolean) = apply {
            debugLogEnable = enable
        }

        fun debugLogWriteToFile(writeToFile: Boolean) = apply {
            debugLogWriteToFile = writeToFile
        }

        fun debugLogLevel(level: Int) = apply {
            debugLogLevel = level
        }

        fun serverLogEnable(enable: Boolean) = apply {
            serverLogEnable = enable
        }

        fun serverLogSamplingPercent(percent: Float) = apply {
            serverLogSamplingPercent = percent
        }

        fun serverLogHandler(handler: ServerLogHandler?) = apply {
            serverLogHandler = handler
        }

        fun crashHandlerEnable(enable: Boolean) = apply {
            this.crashHandlerEnable = enable
        }

        fun buildTimeStamp(timeStamp: Long) = apply {
            this.buildTimeStamp = timeStamp
        }

        fun buildGitBranchName(branchName: String) = apply {
            this.buildGitBranchName = branchName
        }

        fun assertUtilsEnable(enable: Boolean) = apply {
            this.assertUtilsEnable = enable
        }
    }

    private fun initDebugConfig() {
        DebugUtils.DEBUG = true
        FileLog.canWrite = true
        DebugLog.DEBUG = true
        DebugLog.writeToFile = false
        DebugLog.level = DebugLog.LEVEL_VEBOSE
        ServerLog.canSend = false
        ServerLog.setHandler(null)
        AssertUtils.DEBUG = true
    }

    private fun applyConfig(config: Config?) {
        if (config != null) {
            config.debugUtilsEnable?.let { DebugUtils.DEBUG = it }
            config.fileLogEnable?.let { FileLog.canWrite = it }
            config.debugLogEnable?.let { DebugLog.DEBUG = it }
            config.debugLogWriteToFile?.let { DebugLog.writeToFile = it }
            config.debugLogLevel?.let { DebugLog.level = it }
            config.serverLogEnable?.let { ServerLog.canSend = it }
            config.serverLogSamplingPercent?.let { ServerLog.setSamplingPercent(it) }
            config.serverLogHandler?.let { ServerLog.setHandler(it) }
            config.buildTimeStamp?.let { Utils.buildTimeStamp = it }
            config.buildGitBranchName?.let { Utils.buildGitBranchName = it }
            config.assertUtilsEnable?.let { AssertUtils.DEBUG = it }
        }
    }
}
