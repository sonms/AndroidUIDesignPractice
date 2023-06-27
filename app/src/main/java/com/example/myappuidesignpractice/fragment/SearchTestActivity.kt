package com.example.myappuidesignpractice.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappuidesignpractice.PostData
import com.example.myappuidesignpractice.SearchAdapter
import com.example.myappuidesignpractice.databinding.ActivitySearchTestBinding


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
                        val df = ThirdFragment()
                        val bundle = Bundle()
                        bundle.putString("title", s)

                        df.arguments = bundle
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
}