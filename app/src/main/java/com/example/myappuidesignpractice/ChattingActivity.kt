package com.example.myappuidesignpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myappuidesignpractice.databinding.ActivityChattingBinding

class ChattingActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityChattingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}