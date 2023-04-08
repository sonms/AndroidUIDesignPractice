package com.example.myappuidesignpractice

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myappuidesignpractice.databinding.ActivityMainBinding
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityMainBinding
    private var position = 0
    private lateinit var adapter: ItemAdapter
    private var testData = arrayListOf<String?>()
    private var manager : LinearLayoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initRV()

        val rv = findViewById<RecyclerView>(R.id.recyclerviewtest)
        rv.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        /*
        * recyclerView.itemAnimator = SlideInLeftAnimator().apply {
        setInterpolator(OvershootInterpolator())
        }*/
        rv.itemAnimator?.apply {
            addDuration = 1000
            removeDuration = 100
            moveDuration = 1000
            changeDuration = 100
        }

        mBinding.floatingActionButton.setOnClickListener {
            testData.add("test")
            adapter.notifyItemInserted(position)
            position += 1
        }
    }

    private fun initRV() {
        adapter = ItemAdapter()
        adapter!!.itemData = testData
        mBinding.recyclerviewtest.adapter = adapter
        //adapter!!.setHasStableIds(true)
        mBinding.recyclerviewtest.setHasFixedSize(true)
        mBinding.recyclerviewtest.layoutManager = manager
    }
}