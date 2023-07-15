package com.example.myappuidesignpractice

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myappuidesignpractice.databinding.ActivityScrollTestBinding

class ScrollTestActivity : AppCompatActivity() {
    private var mBinding : ActivityScrollTestBinding? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityScrollTestBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)



    }
}