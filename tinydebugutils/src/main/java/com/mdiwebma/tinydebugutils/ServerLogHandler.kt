package com.mdiwebma.library

interface ServerLogHandler {

    fun send(tag: String, message: String, throwable: Throwable?)
}
