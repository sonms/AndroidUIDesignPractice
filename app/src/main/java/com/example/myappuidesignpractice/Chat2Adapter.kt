package com.example.myappuidesignpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myappuidesignpractice.databinding.ChatItem2Binding
import com.example.myappuidesignpractice.databinding.ItemLayoutBinding

class Chat2Adapter : RecyclerView.Adapter<Chat2Adapter.ItemViewHolder>(){
    private lateinit var binding : ChatItem2Binding
    var item2Data = mutableListOf<String?>()
    private lateinit var context : Context

    inner class ItemViewHolder(private val binding : ChatItem2Binding ) : RecyclerView.ViewHolder(binding.root) {
        private var position : Int? = null

        fun bind(itemDataString: String, position : Int) {
            this.position = position
            binding.chatTv2.text = itemDataString
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        binding = ChatItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(item2Data[position]!!, position)
        //val content : SearchData = searchItemData[position]!!
        //holder.tv_date.text = content.scheduleText
    }

    override fun getItemCount(): Int {
        return item2Data.size
    }

    fun setReplyCommentData(replyComments: List<String>) {
        item2Data.clear()
        item2Data.addAll(replyComments)
        notifyDataSetChanged()
    }

}