package com.example.myappuidesignpractice

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappuidesignpractice.databinding.ActivityChattingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChattingActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityChattingBinding
    private var adapter : ChatAdapter? = null
    private var chatData = mutableListOf<String?>()
    private var manager : LinearLayoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setInitReData()
       /* mBinding.messageSendIV.setOnClickListener {
            //mBinding.chatPb.setProgress(70, true)
            // ObjectAnimator를 사용하여 진행률 애니메이션 생성
            val animator = ObjectAnimator.ofInt(mBinding.chatPb, "progress", 0, 100)

            // 애니메이션 지속 시간 설정 (예: 2초)
            animator.duration = 2000

            // 애니메이션 시작
            animator.start()
        }*/

        //아이템 클릭 시 바꾸기
        adapter!!.setItemClickListener(object : ChatAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, itemId: String) {
                val temp = chatData[position]


            }
        })
    }

    private fun setInitReData() {
        setData()
        adapter = ChatAdapter()
        adapter!!.itemData = chatData

        mBinding.chattingRv.adapter = adapter
        mBinding.chattingRv.setHasFixedSize(true)
        mBinding.chattingRv.layoutManager = manager

    }

    private fun setData() {
        chatData.add("1")
        chatData.add("2")
        chatData.add("3")
    }
}