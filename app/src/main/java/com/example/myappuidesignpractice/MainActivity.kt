package com.example.myappuidesignpractice

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myappuidesignpractice.databinding.ActivityMainBinding
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityMainBinding
    private var position = 0
    private lateinit var adapter: ItemAdapter
    private var testData = arrayListOf<String?>()
    private var manager : LinearLayoutManager = LinearLayoutManager(this)
    //뷰페이저
    private lateinit var viewPagerAdapter : ImageSliderAdapter
    private var pageData = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initRV()
        initVP()

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
        //슬라이드할 경우 실행할 이벤트
        mBinding.viewpager.registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }
        })
    }

    private fun initRV() {
        adapter = ItemAdapter()
        adapter!!.itemData = testData
        mBinding.recyclerviewtest.adapter = adapter
        //adapter!!.setHasStableIds(true)
        mBinding.recyclerviewtest.setHasFixedSize(true)
        mBinding.recyclerviewtest.layoutManager = manager
    }
    private fun initVP() {
        pageData = initDataSet()
        viewPagerAdapter = ImageSliderAdapter()
        viewPagerAdapter!!.sliderText = pageData
        mBinding.viewpager.adapter = viewPagerAdapter
        //adapter!!.setHasStableIds(true)
    }
    private fun initDataSet() : ArrayList<String> {
        var itemList = arrayListOf<String>()
        itemList.add("Scientist")
        itemList.add("Game")
        itemList.add("Sports")

        return itemList
    }
}