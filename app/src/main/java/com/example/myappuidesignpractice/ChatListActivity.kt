package com.example.myappuidesignpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappuidesignpractice.Data.practiceData
import com.example.myappuidesignpractice.databinding.ActivityChatListBinding

class ChatListActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityChatListBinding
    private var adapter : ChatItemListAdapter? = null

    private var itemList = ArrayList<practiceData>()
    private var manager : LinearLayoutManager = LinearLayoutManager(this)
    private var dataPos = 0
    private var check = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initChatList()

        /*adapter!!.setItemClickListener(object : ChatItemListAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, itemId: String) {
                adapter!!.changeBackground()
            }
        })*/

    }

    private fun initChatList() {
        setData()
        adapter = ChatItemListAdapter()
        adapter!!.itemData = itemList
        mBinding.userListRV.adapter = adapter
        mBinding.userListRV.setHasFixedSize(true)
        mBinding.userListRV.layoutManager = manager
        /*mBinding.chattingRv.adapter = adapter
         mBinding.chattingRv.setHasFixedSize(true)
         mBinding.chattingRv.layoutManager = manager*/
    }

    private fun setData() {
        itemList.add(practiceData("a", false))
        itemList.add(practiceData("b", false))
        itemList.add(practiceData("c", false))
    }
}