package com.mdiwebma.tinydebugutils

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.mdiwebma.library.DebugLog
import com.mdiwebma.library.DebugUtils
import com.mdiwebma.library.FileLog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //test()
        //testCrash()
        Handler().post {
            ////DebugUtils.alert("dfsdF")
            //testCrash()
            //DebugUtils.checkState(false, "kadjfls")
            DebugUtils.checkNotNull(null, "dfjsdlfjl")
        }
        DebugLog.v("tag", "vvv", RuntimeException())
        DebugLog.d("tag", "ddd", RuntimeException())
        //ContextCompat.checkSelfPermission()
        //finish()
        FileLog.write("aaa", "bbb55")
    }

    fun test() {
        DebugUtils.exception(RuntimeException("failed\na"))
    }

    fun testCrash() {
        Integer.parseInt("abc")
    }
}
