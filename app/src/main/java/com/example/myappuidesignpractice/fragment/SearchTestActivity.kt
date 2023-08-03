package com.example.myappuidesignpractice.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappuidesignpractice.PostData
import com.example.myappuidesignpractice.SearchAdapter
import com.example.myappuidesignpractice.databinding.ActivitySearchTestBinding
import java.net.URISyntaxException


class SearchTestActivity : AppCompatActivity() {
    private var manager : LinearLayoutManager = LinearLayoutManager(this)
    private val sBinding by lazy {
        ActivitySearchTestBinding.inflate(layoutInflater)
    }
    private var searchAdapter : SearchAdapter? = null
    private var searchTestData = ArrayList<PostData>()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNoticeBoardRecyclerView()

        var searchViewTextListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
                override fun onQueryTextSubmit(s: String): Boolean {
                    return false
                }

                //텍스트 입력/수정시에 호출
                override fun onQueryTextChange(s: String): Boolean {
                    if (s.isEmpty()) {
                        searchTestData.clear()
                        setData()
                        searchAdapter!!.searchData = searchTestData
                        searchAdapter!!.notifyDataSetChanged()
                    } else {
                        searchAdapter!!.filter.filter(s)
                        /*val df = ThirdFragment()
                        val bundle = Bundle()
                        bundle.putString("title", s)

                        df.arguments = bundle*/
                    }
                    Log.d("this", "SearchVies Text is changed : $s")
                    return false
                }
            }
        sBinding.searchEt.setOnQueryTextListener(searchViewTextListener)
        sBinding.stestb.setOnClickListener {
            deepLink()
        }

        searchAdapter!!.setItemClickListener(object : SearchAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, itemId: Int) {
                println("cacalclaclca")
            }

        })

        setContentView(sBinding.root)
    }

    private fun initSwipeRefresh() {
        sBinding.refreshSwipeLayout.setOnRefreshListener {
            //새로고침 시 터치불가능하도록
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                setData()
                searchAdapter!!.searchData = searchTestData
                //noticeBoardAdapter.recyclerView.startLayoutAnimation()
                sBinding.refreshSwipeLayout.isRefreshing = false
                searchAdapter!!.notifyDataSetChanged()
            }, 1000)
            //터치불가능 해제ss
            //activity?.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }
    private fun setData() {
        searchTestData.add(PostData("a", 0, "s", "ssssdsf", "dsf", "sdf"))
        searchTestData.add(PostData("b", 1, "ss", "qqqq", "dsf", "sdf"))
        searchTestData.add(PostData("v", 2, "sb", "uuuuu", "dsf", "sdf"))
        searchTestData.add(PostData("d", 3, "so", "kkkkk", "dsf", "sdf"))
    }

    private fun initNoticeBoardRecyclerView() {
        setData()
        searchAdapter = SearchAdapter()
        searchAdapter!!.searchData = searchTestData
        sBinding.searchRV.adapter = searchAdapter
        //레이아웃 뒤집기 안씀
        //manager.reverseLayout = true
        //manager.stackFromEnd = true
        sBinding.searchRV.setHasFixedSize(true)
        sBinding.searchRV.layoutManager = manager

    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun deepLink() {
        // 딥링크 URI를 정확히 작성해야 합니다.
        val uri = Uri.parse("uitest://deeplink?cost=10000")

        val intent = Intent(Intent.ACTION_VIEW, uri)

        // 토스 앱의 패키지 이름을 알아야 합니다.
        val tossPackageName = "viva.republica.toss"

        // 토스 앱이 설치되어 있는지 확인하고 실행합니다.
        if (isPackageInstalled(tossPackageName)) {
            intent.setPackage(tossPackageName)
            startActivity(intent)
        } else {
            // 토스 앱이 설치되어 있지 않은 경우 처리할 내용을 여기에 추가합니다.
            Toast.makeText(this, "토스 앱이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    /*fun initViews() {
        WEB_VIEW.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return shouldOverrideUrlLoading(view, request)
            }
        }
    }

    private fun shouldOverrideUrlLoading(view: WebView, url: String) : Boolean {
        url?.let {
            if (!URLUtil.isNetworkUrl(url) && !URLUtil.isJavaScriptUrl(url)) {
                // 딥링크로 URI 객체 만들기
                val uri = try {
                    Uri.parse(url)
                } catch (e: Exception) {
                    return false
                }

                return when (uri.scheme) {
                    "intent" -> {
                        startSchemeIntent(it) // Intent 스킴인 경우
                    }
                    else -> {
                        return try {
                            startActivity(Intent(Intent.ACTION_VIEW, uri)) // 다른 딥링크 스킴이면 실행
                            true
                        } catch (e: java.lang.Exception) {
                            false
                        }
                    }
                }
            } else {
                return false
            }
        } ?: return false
    }


    *//*Intent 스킴을 처리하는 함수*//*
    fun startSchemeIntent(url: String): Boolean {
        val schemeIntent: Intent = try {
            Intent.parseUri(url, Intent.URI_INTENT_SCHEME) // Intent 스킴을 파싱
        } catch (e: URISyntaxException) {
            return false
        }
        try {
            startActivity(schemeIntent) // 앱으로 이동
            return true
        } catch (e: ActivityNotFoundException) { // 앱이 설치 안 되어 있는 경우
            val packageName = schemeIntent.getPackage()

            if (!packageName.isNullOrBlank()) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packageName") // 스토어로 이동
                    )
                )
                return true
            }
        }
        return false
    }*/
}