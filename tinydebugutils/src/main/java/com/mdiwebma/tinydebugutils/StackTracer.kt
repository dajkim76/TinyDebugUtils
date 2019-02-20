package com.mdiwebma.library

internal object StackTracer {

    private val ignoredStackList = arrayOf(
        "dalvik.system.VMStack.",
        "com.mdiwebma.library.StackTracer.",
        "com.mdiwebma.library.DebugUtils.",
        "java.lang.Thread."
    )

    @JvmStatic
    fun getCurrentStackTraceString(): String {
        val sb = StringBuilder()
        for (element in Thread.currentThread().stackTrace) {
            val trace = element.toString()
            if (ignoredStackList.find { trace.startsWith(it) }.isNullOrEmpty()) {
                sb.append(trace).append("\n")
            }
        }
        return sb.toString()
    }
}
