package com.example.myappuidesignpractice

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.myappuidesignpractice.databinding.ActivityMainBinding
import com.example.myappuidesignpractice.fragment.FirstFragment
import com.example.myappuidesignpractice.fragment.SearchTestActivity
import com.example.myappuidesignpractice.fragment.SecondFragment
import com.example.myappuidesignpractice.fragment.ThirdFragment
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
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
    //splash screen animation
    var isReady = false
    var isStart = false
    //퍼미션
    private var isCheckPermissions = false
    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 100
    private val REQUEST_PERMISSIONS = 1
    var permission = mutableMapOf<String, String>()
    //뷰페이저 페이지체인지 콜백
    val callback: OnPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(pos: Int) {
            super.onPageSelected(pos)
            //println(pos)
            when(pos) {
                0 -> {
                    setFragment(TAG_A, FirstFragment())
                }

                1 -> {
                    setFragment(TAG_B, SecondFragment())
                }

                2 -> {
                    setFragment(TAG_C, ThirdFragment())
                }
            }
        }
    }
    //프래그먼트
    private val TAG_A = "a_fragment"
    private val TAG_B = "b_fragment"
    private val TAG_C = "c_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        //안드로이드 버전이 S이상일때만 적용
        //initSplashScreen()
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener {splashScreenView ->
                val animScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 8f)
                val animScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 8f)
                val animAlpha = PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f)

                ObjectAnimator.ofPropertyValuesHolder(
                    splashScreenView.iconView,
                    animAlpha,
                    animScaleX,
                    animScaleY
                ).run {
                    interpolator = AnticipateInterpolator()
                    duration = 300L
                    doOnEnd { splashScreenView.remove() }
                    start()
                }
            }
        }*/
            super.onCreate(savedInstanceState)



        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //layout = mBinding.frameLayout

        //pr = layout.layoutParams as android.widget.LinearLayout.LayoutParams

        initRV()
        initVP()
        //initIndicators(pageData.size)
        checkPermission()

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

        val alphaAdapter = AlphaInAnimationAdapter(adapter!!)
        rv.adapter = ScaleInAnimationAdapter(alphaAdapter).apply {
            setDuration(2000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }

        /*rv.adapter  = SlideInBottomAnimationAdapter(adapter!!).apply {
            setDuration(1000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }*/

        mBinding.floatingActionButton.setOnClickListener {
            /*testData.add("test")
            adapter.notifyItemInserted(position)
            position += 1*/

            val intent = Intent(this, SearchTestActivity::class.java).apply {

            }
            startActivity(intent)

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

        mBinding.testbtn.setOnClickListener {
            val intent = Intent(this, LocationTestActivity::class.java).apply {
                putExtra("grant", isCheckPermissions)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == permission.size) {
            grantResults.forEach {
                if(it == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(applicationContext, "서비스의 필요한 권한입니다.\n권한에 동의해주세요.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    isCheckPermissions = true
                }
            }
        }
    }

    private fun checkPermission() {
        permission["fine"] = android.Manifest.permission.ACCESS_FINE_LOCATION
        permission["coarse"] = android.Manifest.permission.ACCESS_COARSE_LOCATION

        //현재 권한 검사
        var denied = permission.count { ContextCompat.checkSelfPermission(this, it.value) == PackageManager.PERMISSION_DENIED }

        //마시멜로 버전 이후
        if(denied > 0) {
            requestPermissions(permission.values.toTypedArray(), PERMISSIONS_REQUEST_CODE)
        }
    }

    private fun initData() {
        // 별도의 데이터 처리가 없기 때문에 3초의 딜레이를 줌.
        // 선행되어야 하는 작업이 있는 경우, 이곳에서 처리 후 isReady를 변경.
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
        }
        isReady = true
    }

    private fun initSplashScreen() {
        initData()
        val splashScreen = installSplashScreen()
        val content: View = findViewById(android.R.id.content)
        // SplashScreen이 생성되고 그려질 때 계속해서 호출된다.
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    return if (isReady) {
                        // 3초 후 Splash Screen 제거
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready
                        false
                    }
                }
            }
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener {splashScreenView ->
                val animScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 8f)
                val animScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 8f)
                val animAlpha = PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f)

                ObjectAnimator.ofPropertyValuesHolder(
                    splashScreenView.iconView,
                    animAlpha,
                    animScaleX,
                    animScaleY
                ).run {
                    interpolator = AnticipateInterpolator()
                    duration = 300L
                    doOnEnd { splashScreenView.remove() }
                    start()
                }
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

    fun setFragment(tag : String, fragment: Fragment) {
        val manager : FragmentManager = supportFragmentManager
        val bt = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null) {
            bt.add(R.id.fragmenttest, fragment, tag)
        }

        val f = manager.findFragmentByTag(TAG_A)
        val s = manager.findFragmentByTag(TAG_B)
        val t = manager.findFragmentByTag(TAG_C)

        if (f != null) {
            bt.hide(f)
        }
        if (s != null) {
            bt.hide(s)
        }
        if (t != null) {
            bt.hide(t)
        }

        if (tag == TAG_A) {
            if (f != null) {
                bt.show(f)
            }
        }
        else if (tag == TAG_B) {
            if (s != null) {
                bt.show(s)
            }
        }
        else if (tag == TAG_C) {
            if (t != null) {
                bt.show(t)
            }
        }
        bt.commitAllowingStateLoss()
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