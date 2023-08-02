package com.example.myappuidesignpractice

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myappuidesignpractice.databinding.ActivityScrollTestBinding

class ScrollTestActivity : AppCompatActivity() {
    private var mBinding : ActivityScrollTestBinding? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityScrollTestBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)


        mBinding!!.teb.setOnClickListener {
            val loadingDialog = LoadingProgressDialog(this)
            loadingDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            loadingDialog.show()
        }


    }
}