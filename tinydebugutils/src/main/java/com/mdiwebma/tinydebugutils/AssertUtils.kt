package com.mdiwebma.tinydebugutils

import android.util.Log

object AssertUtils {

    @JvmStatic
    var DEBUG = false

    @JvmStatic
    fun fail(message: String) {
        if (!DEBUG) return
        check(false, message, null)
    }

    @JvmStatic
    fun fail(ex: Throwable) {
        if (!DEBUG) return
        check(false, null, ex)
    }

    @JvmStatic
    fun fail(message: String, ex: Throwable) {
        if (!DEBUG) return
        check(false, message, ex)
    }

    @JvmStatic
    fun checkNotNull(any: Any?) {
        if (!DEBUG) return
        check(any != null, null, null)
    }

    @JvmStatic
    fun checkNotNull(any: Any?, message: String?) {
        if (!DEBUG) return
        check(any != null, message, null)
    }

    @JvmStatic
    fun checkState(state: Boolean) {
        if (!DEBUG) return
        check(state, null, null)
    }

    @JvmStatic
    fun checkState(state: Boolean, message: String?) {
        if (!DEBUG) return
        check(state, message, null)
    }

    private fun check(state: Boolean, message: String?, throwable: Throwable?) {
        if (!state) {
            Log.e("ASSERT", message, throwable)
            throw if (throwable != null) Error(message, throwable) else Error(message)
        }
    }
}

