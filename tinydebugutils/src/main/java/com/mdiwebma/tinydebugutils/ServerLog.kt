package com.mdiwebma.tinydebugutils

import java.security.SecureRandom

object ServerLog {

    private const val BOUND = 10000

    private var handler: ServerLogHandler? = null
    @JvmStatic
    var canSend: Boolean = false
        get() = field && handler != null

    @JvmStatic
    fun send(tag: String, message: String, throwable: Throwable? = null) {
        handler?.send(tag, message, throwable)
    }

    @JvmStatic
    fun maybeSend(tag: String, message: String, throwable: Throwable? = null) {
        if (canSend) {
            handler?.send(tag, message, throwable)
        }
    }

    @JvmStatic
    fun setSamplingPercent(percent: Float) {
        canSend = when {
            percent <= 0f -> false
            percent >= 100f -> true
            else -> {
                SecureRandom().nextInt(BOUND) <= ((percent / 100) * BOUND).toInt()
            }
        }
    }

    @JvmStatic
    fun setHandler(handler: ServerLogHandler?) {
        this.handler = handler
    }
}
