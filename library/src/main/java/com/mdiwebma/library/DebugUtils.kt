package com.mdiwebma.library

object DebugUtils {

    @JvmStatic
    fun checkState(state: Boolean) {
        if (!state) {
            DebugMessageHandler.checkState(StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun checkState(state: Boolean, message: String) {
        if (!state) {
            DebugMessageHandler.checkState("$message\n\n" + StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun checkNotNull(any: Any?) {
        if (any == null) {
            DebugMessageHandler.checkNotNull(StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun checkNotNull(any: Any?, message: String) {
        if (any == null) {
            DebugMessageHandler.checkNotNull("$message\n\n" + StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun exception() {
        DebugMessageHandler.exception(StackTracer.getCurrentStackTraceString())
    }

    @JvmStatic
    fun exception(message: String) {
        DebugMessageHandler.exception("$message\n\n" + StackTracer.getCurrentStackTraceString())
    }

    @JvmStatic
    fun exception(throwable: Throwable) {
        DebugMessageHandler.exception(StackTracer.getStackTraceString(throwable.stackTrace))
    }

    @JvmStatic
    fun exception(message: String, throwable: Throwable) {
        DebugMessageHandler.exception("$message!\n\n" + StackTracer.getStackTraceString(throwable.stackTrace))
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
    fun notify(message: String?) {
        DebugMessageHandler.notify(message)
    }
}
