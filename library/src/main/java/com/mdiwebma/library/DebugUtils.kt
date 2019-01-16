package com.mdiwebma.library

object DebugUtils {

    @JvmStatic
    fun checkState(state: Boolean) {
        if (!state) {
            DebugMessageHandler.alert("check failed\n\n" + StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun checkState(state: Boolean, message: String) {
        if (!state) {
            DebugMessageHandler.alert("check failed\n$message\n\n" + StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun checkNotNull(any: Any?) {
        if (any == null) {
            DebugMessageHandler.alert("checkNotNull failed\n\n" + StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun checkNotNull(any: Any?, message: String) {
        if (any == null) {
            DebugMessageHandler.alert("checkNotNull failed\n$message\n\n" + StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun notReached() {
        DebugMessageHandler.notReached("notReached!\n\n" + StackTracer.getCurrentStackTraceString())
    }

    @JvmStatic
    fun notReached(message: String) {
        DebugMessageHandler.notReached("$message\n\n" + StackTracer.getCurrentStackTraceString())
    }

    @JvmStatic
    fun notReached(throwable: Throwable) {
        DebugMessageHandler.notReached("notReached!\n\n" + StackTracer.getStackTraceString(throwable.stackTrace))
    }

    @JvmStatic
    fun notReached(message: String, throwable: Throwable) {
        DebugMessageHandler.notReached("$message!\n\n" + StackTracer.getStackTraceString(throwable.stackTrace))
    }

    @JvmStatic
    fun toast(message: String?) {
        DebugMessageHandler.toast(message)
    }

    @JvmStatic
    fun alert(message: String?) {
        DebugMessageHandler.alert(message)
    }

    @JvmStatic
    fun notify(message: String) {
        DebugMessageHandler.notify(message)
    }
}
