package com.mdiwebma.library

object StackTracer {

    private val ignoredStackList = arrayOf(
        "dalvik.system.VMStack.",
        "com.mdiwebma.library.StackTracer.",
        "com.mdiwebma.library.DebugUtils.",
        "java.lang.Thread."
    )

    @JvmStatic
    fun getCurrentStackTraceString(): String =
        getStackTraceString(Thread.currentThread().stackTrace)

    @JvmStatic
    fun getStackTraceString(stackTrace: Array<StackTraceElement>): String {
        val sb = StringBuilder()
        for (element in stackTrace) {
            val trace = element.toString()
            if (ignoredStackList.find { trace.startsWith(it) }.isNullOrEmpty()) {
                sb.append(trace + "\n")
            }
        }
        return sb.toString()
    }
}
