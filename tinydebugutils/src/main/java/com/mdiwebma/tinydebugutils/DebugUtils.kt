package com.mdiwebma.tinydebugutils

import android.os.Looper
import android.util.Log

object DebugUtils {

    private const val TAG_CHECK_STATE = "CheckState!"
    private const val TAG_CHECK_NOT_NULL = "CheckNotNull!"
    private const val TAG_EXCEPTION = "Exception!"

    private val VERSION_BUILD_INFO: String by lazy { Utils.getVersionBuildInfo() }

    @JvmStatic
    var DEBUG = false

    @JvmStatic
    fun checkState(state: Boolean) {
        if (DEBUG) {
            if (!state) {
                DebugMessageHandler.checkState("$VERSION_BUILD_INFO\n" + StackTracer.getCurrentStackTraceString())
            }
        } else if (!state && ServerLog.canSend) {
            ServerLog.send(TAG_CHECK_STATE, StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun checkState(state: Boolean, message: String) {
        if (DEBUG) {
            if (!state) {
                DebugMessageHandler.checkState("$message\n\n$VERSION_BUILD_INFO\n" + StackTracer.getCurrentStackTraceString())
            }
        } else if (!state && ServerLog.canSend) {
            ServerLog.send(
                TAG_CHECK_STATE,
                "$message\n\n" + StackTracer.getCurrentStackTraceString()
            )
        }
    }

    @JvmStatic
    fun checkNotNull(any: Any?) {
        if (DEBUG) {
            if (any == null) {
                DebugMessageHandler.checkNotNull("$VERSION_BUILD_INFO\n" + StackTracer.getCurrentStackTraceString())
            }
        } else if (any == null && ServerLog.canSend) {
            ServerLog.send(TAG_CHECK_NOT_NULL, StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun checkNotNull(any: Any?, message: String) {
        if (DEBUG) {
            if (any == null) {
                DebugMessageHandler.checkNotNull("$message\n\n$VERSION_BUILD_INFO\n" + StackTracer.getCurrentStackTraceString())
            }
        } else if (any == null && ServerLog.canSend) {
            ServerLog.send(
                TAG_CHECK_NOT_NULL,
                "$message\n\n" + StackTracer.getCurrentStackTraceString()
            )
        }
    }

    @JvmStatic
    fun exception() {
        if (DEBUG) {
            DebugMessageHandler.exception("$VERSION_BUILD_INFO\n" + StackTracer.getCurrentStackTraceString())
        } else if (ServerLog.canSend) {
            ServerLog.send(TAG_EXCEPTION, StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun exception(message: String) {
        if (DEBUG) {
            DebugMessageHandler.exception("$message\n\n$VERSION_BUILD_INFO\n" + StackTracer.getCurrentStackTraceString())
        } else if (ServerLog.canSend) {
            ServerLog.send(TAG_EXCEPTION, "$message\n\n" + StackTracer.getCurrentStackTraceString())
        }
    }

    @JvmStatic
    fun exception(throwable: Throwable) {
        if (DEBUG) {
            DebugMessageHandler.exception(
                "$VERSION_BUILD_INFO\n" + Log.getStackTraceString(
                    throwable
                )
            )
        } else if (ServerLog.canSend) {
            ServerLog.send(TAG_EXCEPTION, "", throwable)
        }
    }

    @JvmStatic
    fun exception(message: String, throwable: Throwable) {
        if (DEBUG) {
            DebugMessageHandler.exception(
                "$message!\n\n$VERSION_BUILD_INFO\n" + Log.getStackTraceString(
                    throwable
                )
            )
        } else if (ServerLog.canSend) {
            ServerLog.send(TAG_EXCEPTION, message, throwable)
        }
    }

    @JvmStatic
    fun toast(message: String?) {
        if (DEBUG) {
            DebugMessageHandler.toast(message)
        }
    }

    @JvmStatic
    fun alert(message: String?) {
        if (DEBUG) {
            DebugMessageHandler.alert(message)
        }
    }

    @JvmStatic
    fun alert(title: String, message: String?) {
        if (DEBUG) {
            DebugMessageHandler.alert(title, message)
        }
    }

    @JvmStatic
    fun notify(message: String?) {
        if (DEBUG) {
            DebugMessageHandler.notify(message)
        }
    }

    @JvmStatic
    fun checkMainThread() {
        if (DEBUG) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                DebugMessageHandler.exception("checkMainThread failed\n\n$VERSION_BUILD_INFO\n" + StackTracer.getCurrentStackTraceString())
            }
        } else if (ServerLog.canSend) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                ServerLog.send(
                    "CheckMainThread!",
                    "$VERSION_BUILD_INFO\n" + StackTracer.getCurrentStackTraceString()
                )
            }
        }
    }
}
