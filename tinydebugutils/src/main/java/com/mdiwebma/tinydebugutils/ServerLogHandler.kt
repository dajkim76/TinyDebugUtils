package com.mdiwebma.tinydebugutils

interface ServerLogHandler {

    fun send(tag: String, message: String, throwable: Throwable?)
}
