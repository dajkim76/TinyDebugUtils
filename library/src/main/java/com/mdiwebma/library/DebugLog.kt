package com.mdiwebma.library

import android.util.Log

/**
 * Print source line if msg is single line
 * Adjust level (Don't print log if level is LEVEL_DISABLE)
 * Write to log file if writeToFile is true
 */
object DebugLog {

    const val LEVEL_VEBOSE = 1
    const val LEVEL_DEBUG = 2
    const val LEVEL_INFO = 3
    const val LEVEL_WARNING = 4
    const val LEVEL_ERROR = 5
    const val LEVEL_FATAL = 6
    const val LEVEL_DISABLE = Int.MAX_VALUE

    @JvmStatic
    var level = LEVEL_VEBOSE

    @JvmStatic
    var DEBUG = false

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
        if (!DEBUG) return
        log(LEVEL_VEBOSE, tag, msg, throwable)
    }

    @JvmStatic
    fun d(tag: String, msg: String, throwable: Throwable? = null) {
        if (!DEBUG) return
        log(LEVEL_DEBUG, tag, msg, throwable)
    }

    @JvmStatic
    fun i(tag: String, msg: String, throwable: Throwable? = null) {
        if (!DEBUG) return
        log(LEVEL_INFO, tag, msg, throwable)
    }

    @JvmStatic
    fun w(tag: String, msg: String, throwable: Throwable? = null) {
        if (!DEBUG) return
        log(LEVEL_WARNING, tag, msg, throwable)
    }

    @JvmStatic
    fun e(tag: String, msg: String, throwable: Throwable? = null) {
        if (!DEBUG) return
        log(LEVEL_ERROR, tag, msg, throwable)
    }

    @JvmStatic
    fun f(tag: String, msg: String, throwable: Throwable? = null) {
        if (!DEBUG) return
        log(LEVEL_FATAL, tag, msg, throwable)
    }

    /**
     * Always print log. Ignore level and BuildConfig.DEBUG
     */
    @JvmStatic
    fun t(tag: String, msg: String, throwable: Throwable? = null) {
        log(Int.MAX_VALUE, tag, msg, throwable)
    }

    private fun log(logLevel: Int, tag: String, msg: String, throwable: Throwable? = null) {
        if (logLevel >= level) {
            val trace = getTrace(msg)
            var level: String? = null
            when (logLevel) {
                LEVEL_VEBOSE -> {
                    level = "V"
                    Log.v(tag, trace, throwable)
                }
                LEVEL_DEBUG -> {
                    level = "D"
                    Log.d(tag, trace, throwable)
                }
                LEVEL_INFO -> {
                    level = "I"
                    Log.i(tag, trace, throwable)
                }
                LEVEL_WARNING -> {
                    level = "W"
                    Log.w(tag, trace, throwable)
                }
                LEVEL_ERROR -> {
                    level = "E"
                    Log.e(tag, trace, throwable)
                }
                LEVEL_FATAL -> {
                    level = "F"
                    Log.e(tag, trace, throwable)
                }
                else -> {
                    level = "T"
                    Log.e(tag, trace, throwable)
                }
            }
            if (writeToFile || logLevel == LEVEL_FATAL) {
                FileLog.write(level, tag, trace)
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
