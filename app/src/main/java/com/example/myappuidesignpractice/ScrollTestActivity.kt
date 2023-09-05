package com.example.myappuidesignpractice

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
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
            //loadingDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            //로딩창
            loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialog.window?.attributes?.windowAnimations = R.style.FullScreenDialog // 위에서 정의한 스타일을 적용


            loadingDialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            loadingDialog.show()
        }


    }
}