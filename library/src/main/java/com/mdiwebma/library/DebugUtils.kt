package com.mdiwebma.library

import android.os.Looper
import android.util.Log

object DebugUtils {

    @JvmStatic
    fun checkState(state: Boolean) {
        if (!BuildConfig.DEBUG) return
        if (!state) {
            DebugMessageHandler.checkState(StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun checkState(state: Boolean, message: String) {
        if (!BuildConfig.DEBUG) return
        if (!state) {
            DebugMessageHandler.checkState("$message\n\n" + StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun checkNotNull(any: Any?) {
        if (!BuildConfig.DEBUG) return
        if (any == null) {
            DebugMessageHandler.checkNotNull(StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun checkNotNull(any: Any?, message: String) {
        if (!BuildConfig.DEBUG) return
        if (any == null) {
            DebugMessageHandler.checkNotNull("$message\n\n" + StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun exception() {
        if (!BuildConfig.DEBUG) return
        DebugMessageHandler.exception(StackTracer.getCurrentStackTraceString())
    }

    @JvmStatic
    fun exception(message: String) {
        if (!BuildConfig.DEBUG) return
        DebugMessageHandler.exception("$message\n\n" + StackTracer.getCurrentStackTraceString())
    }

    @JvmStatic
    fun exception(throwable: Throwable) {
        if (!BuildConfig.DEBUG) return
        DebugMessageHandler.exception(Log.getStackTraceString(throwable))
    }

    @JvmStatic
    fun exception(message: String, throwable: Throwable) {
        if (!BuildConfig.DEBUG) return
        DebugMessageHandler.exception("$message!\n\n" + Log.getStackTraceString(throwable))
    }

    @JvmStatic
    fun toast(message: String?) {
        if (!BuildConfig.DEBUG) return
        DebugMessageHandler.toast(message)
    }

    @JvmStatic
    fun alert(message: String?) {
        if (!BuildConfig.DEBUG) return
        DebugMessageHandler.alert(message)
    }

    @JvmStatic
    fun notify(message: String?) {
        if (!BuildConfig.DEBUG) return
        DebugMessageHandler.notify(message)
    }

    @JvmStatic
    fun checkMainThread() {
        if (!BuildConfig.DEBUG) return
        if (Looper.myLooper() != Looper.getMainLooper()) {
            DebugMessageHandler.exception("checkMainThread failed\n\n" + StackTracer.getCurrentStackTraceString())
        }
    }
}
