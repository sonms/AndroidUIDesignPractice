package com.example.myappuidesignpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myappuidesignpractice.databinding.ActivityChatListBinding

class ChatListActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityChatListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)
    }
}