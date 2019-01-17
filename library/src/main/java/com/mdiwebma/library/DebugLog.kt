package com.mdiwebma.library

import android.util.Log

object DebugLog {

    const val LEVEL_NONE = 0
    const val LEVEL_VEBOSE = 1
    const val LEVEL_DEBUG = 2
    const val LEVEL_INFO = 3
    const val LEVEL_WARNING = 4
    const val LEVEL_ERROR = 5
    const val LEVEL_FATAL = 6

    @JvmStatic
    var level = LEVEL_VEBOSE

    @JvmStatic
    var writeToFile = false
        set(value) {
            field = value
            if (value) {
                FileLog.canWrite = true
            }
        }

    @JvmStatic
    fun v(tag: String, msg: String, throwable: Throwable? = null) {
        log(LEVEL_VEBOSE, tag, msg, throwable)
    }

    @JvmStatic
    fun d(tag: String, msg: String, throwable: Throwable? = null) {
        log(LEVEL_DEBUG, tag, msg, throwable)
    }

    @JvmStatic
    fun i(tag: String, msg: String, throwable: Throwable? = null) {
        log(LEVEL_INFO, tag, msg, throwable)
    }

    @JvmStatic
    fun w(tag: String, msg: String, throwable: Throwable? = null) {
        log(LEVEL_WARNING, tag, msg, throwable)
    }

    @JvmStatic
    fun e(tag: String, msg: String, throwable: Throwable? = null) {
        log(LEVEL_ERROR, tag, msg, throwable)
    }

    @JvmStatic
    fun fatal(tag: String, msg: String, throwable: Throwable? = null) {
        log(LEVEL_FATAL, tag, msg, throwable)
    }

    private fun log(checkLevel: Int, tag: String, msg: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG && checkLevel >= level) {
            val trace = getTrace(msg)
            when (checkLevel) {
                LEVEL_VEBOSE -> Log.v(tag, trace, throwable)
                LEVEL_DEBUG -> Log.d(tag, trace, throwable)
                LEVEL_INFO -> Log.i(tag, trace, throwable)
                LEVEL_WARNING -> Log.w(tag, trace, throwable)
                LEVEL_ERROR -> Log.e(tag, trace, throwable)
                LEVEL_FATAL -> Log.e(tag, trace, throwable)
            }
            if (writeToFile) {
                FileLog.write(tag, trace)
            }
        }
    }

    private val ignoredStackList = arrayOf(
        "dalvik.system.VMStack.",
        "com.mdiwebma.library.DebugLog.",
        "java.lang.Thread."
    )

    /**
     * Append (source code file:line) to message
     * Jump to source line in Logcat
     */
    private fun getTrace(message: String): String {
        if (message.indexOf('\n') < 0) {
            for (element in Thread.currentThread().stackTrace) {
                var trace = element.toString()
                if (ignoredStackList.find { trace.startsWith(it) }.isNullOrEmpty()) {
                    var index = trace.lastIndexOf('(')
                    trace = trace.substring(index)
                    return "$message $trace"
                }
            }
        }
        return message
    }
}
