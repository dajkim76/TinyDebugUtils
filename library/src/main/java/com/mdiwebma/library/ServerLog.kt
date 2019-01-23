package com.mdiwebma.library

import java.security.SecureRandom

object ServerLog {

    private const val BOUND = 10000

    private var handler: ServerLogHandler? = null
    @JvmStatic
    var canSend: Boolean = false
        get() = field && handler != null

    @JvmStatic
    fun send(tag: String, message: String) {
        handler?.send(tag, message)
    }

    @JvmStatic
    fun maybeSend(tag: String, message: String) {
        if (canSend) {
            handler?.send(tag, message)
        }
    }

    @JvmStatic
    fun setSampling(percent: Float) {
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
