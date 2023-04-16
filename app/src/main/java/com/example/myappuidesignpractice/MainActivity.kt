package com.example.myappuidesignpractice

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.myappuidesignpractice.databinding.ActivityMainBinding
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
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
    //페이지 마다 점표시
    private lateinit var layoutIndicator : LinearLayout

    val callback: OnPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(pos: Int) {
            super.onPageSelected(pos)
            println(pos)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initRV()
        initVP()
        //initIndicators(pageData.size)


        val rv = mBinding.recyclerviewtest
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

        val indicator : WormDotsIndicator = mBinding.springDot
        //indicator.setViewPager2(mBinding.viewpager)
        indicator.attachTo(mBinding.viewpager)

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
        //layoutIndicator = mBinding.layoutIndicators
        pageData = initDataSet()
        viewPagerAdapter = ImageSliderAdapter()
        viewPagerAdapter!!.sliderText = pageData
        mBinding.viewpager.adapter = viewPagerAdapter
        //adapter!!.setHasStableIds(true)
        //슬라이드할 경우 실행할 이벤트
        mBinding.viewpager.registerOnPageChangeCallback(callback)
    }


    private fun initDataSet() : ArrayList<String> {
        var itemList = arrayListOf<String>()
        itemList.add("Scientist")
        itemList.add("Game")
        itemList.add("Sports")

        return itemList
    }
    /*private fun initIndicators(cnt : Int) {
        val indicators = ArrayList<ImageView>(cnt)
        println(indicators)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(16, 8, 16, 8)

        for (i in 0 until indicators.size) {
            indicators[i] = ImageView(this)
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                com.example.myappuidesignpractice.R.drawable.bg_indicator_inactive))
            indicators[i].layoutParams = params
            layoutIndicator.addView(indicators[i])
        }

        setCurrentIndicator(0)
    }

    private fun setCurrentIndicator(position : Int) {
        val childCnt = layoutIndicator.childCount
        print(position)
        for (i in 0 until childCnt) {
            val iv : ImageView = layoutIndicator.getChildAt(i) as ImageView
            if (i == position) {
                iv.setImageDrawable(ContextCompat.getDrawable(
                    this,
                    com.example.myappuidesignpractice.R.drawable.bg_indicator_active
                ))
            } else {
                iv.setImageDrawable(ContextCompat.getDrawable(
                    this,
                    com.example.myappuidesignpractice.R.drawable.bg_indicator_inactive
                ))
            }
        }

    }*/
}