package com.example.myappuidesignpractice

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.Window
import android.widget.ProgressBar

class LoadingProgressDialog(context: Context?) : Dialog(context!!) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 다이얼 로그 제목을 안보이게...
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.loading_dialog_layout)
        // AnimationDrawable 시작
        /*val loadingProgress = findViewById<ProgressBar>(R.id.loading_progress)
        (loadingProgress.progressDrawable as AnimationDrawable).start()*/
    }
}