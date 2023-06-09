package com.example.myappuidesignpractice.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
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

        setContentView(sBinding.root)
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
    private fun deepLink() {
        //토스 스킴값 supertoss://
        /*WEB_VIEW.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return shouldOverrideUrlLoading(view, request)
            }
        }*/
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