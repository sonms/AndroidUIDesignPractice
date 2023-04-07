package com.example.myappuidesignpractice

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.myappuidesignpractice.databinding.ActivityMainBinding
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityMainBinding
    private lateinit var adapter: ItemAdapter
    private var testData = arrayListOf<String?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.recyclerviewtest)
        rv.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
    }

    private fun initRV() {
        adapter = ItemAdapter()
        adapter!!.itemData = testData
        //adapter!!.setHasStableIds(true)
    }
}