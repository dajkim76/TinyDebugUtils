package com.mdiwebma.tinydebugutils

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mdiwebma.library.DebugUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
        //testCrash()
    }

    fun test() {
        DebugUtils.notReached(RuntimeException("failed"))
    }

    fun testCrash() {
        Integer.parseInt("abc")
    }
}
