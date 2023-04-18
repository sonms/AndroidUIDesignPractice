package com.example.myappuidesignpractice

import android.animation.LayoutTransition
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telecom.Call
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.myappuidesignpractice.databinding.ActivityMainBinding
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
    private lateinit var pr : LinearLayout.LayoutParams
    private lateinit var layout : LinearLayout
    private var isDetailLayout = false

    val callback: OnPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(pos: Int) {
            super.onPageSelected(pos)
            println(pos)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //layout = mBinding.frameLayout

        //pr = layout.layoutParams as android.widget.LinearLayout.LayoutParams

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

        //뷰페이저 터치이벤트
        mBinding.viewpager.getChildAt(0).setOnTouchListener { view, motionEvent ->
            /*CoroutineScope(Dispatchers.Main).launch {
                //val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                //mBinding.frameLayout.layoutParams = params

            }*/
            //mBinding.frameLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            false
        }

        mBinding.con.setOnClickListener {
            if (isDetailLayout) {
                swapFrames(R.layout.activity_main)
            } else {
                swapFrames(R.layout.activity_main_detail)
            }
        }
    }

    private fun swapFrames(layoutId : Int) {
        val con = ConstraintSet()
        con.clone(this, layoutId)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(mBinding.con, transition)
        con.applyTo(mBinding.con)
        isDetailLayout = !isDetailLayout
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

    /*override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            change(3f)
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(java.lang.Runnable {
                kotlin.run {
                    change(3f)
                }
            }, 1000)

        }
        return super.onTouchEvent(event)
    }

    private fun change(v : Float) {
        pr.height = (v*mBinding.frameLayout.height).toInt()
        mBinding.frameLayout.layoutParams = pr
    }*/
}