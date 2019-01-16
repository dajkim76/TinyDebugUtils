package com.mdiwebma.tinydebugutils

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.mdiwebma.library.DebugUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //test()
        //testCrash()
        Handler().post {
            DebugUtils.alert("dfsdF")
            //testCrash()
            //DebugUtils.checkState(false, "kadjfls")
            DebugUtils.checkNotNull(null, "dfjsdlfjl")
        }
        //finish()
    }

    fun test() {
        DebugUtils.notReached(RuntimeException("failed"))
    }

    fun testCrash() {
        Integer.parseInt("abc")
    }
}
