package com.mdiwebma.library

import android.util.Log

object AssertUtils {

    @JvmStatic
    fun fail(message: String) {
        check(false, message, null)
    }

    @JvmStatic
    fun fail(ex: Throwable) {
        check(false, null, ex)
    }

    @JvmStatic
    fun fail(message: String, ex: Throwable) {
        check(false, message, ex)
    }

    @JvmStatic
    fun checkNotNull(any: Any?) {
        check(any != null, null, null)
    }

    @JvmStatic
    fun checkNotNull(any: Any?, message: String?) {
        check(any != null, message, null)
    }

    @JvmStatic
    fun checkState(state: Boolean) {
        check(state, null, null)
    }

    @JvmStatic
    fun checkState(state: Boolean, message: String?) {
        check(state, message, null)
    }

    private fun check(state: Boolean, message: String?, throwable: Throwable?) {
        if (!state) {
            Log.e("ASSERT", message, throwable)
            throw if (throwable != null) Error(message, throwable) else Error(message)
        }
    }
}

