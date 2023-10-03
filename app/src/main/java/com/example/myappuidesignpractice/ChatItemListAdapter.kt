package com.example.myappuidesignpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myappuidesignpractice.databinding.ItemLayoutBinding

//채팅방
class ChatItemListAdapter : RecyclerView.Adapter<ChatItemListAdapter.ItemViewHolder>(){
    private lateinit var binding : ItemLayoutBinding
    var itemData = ArrayList<String>()
    private lateinit var context : Context

    inner class ItemViewHolder(private val binding : ItemLayoutBinding ) : RecyclerView.ViewHolder(binding.root) {
        private var position : Int? = null
        var tv_item = binding.itemTv
        fun bind(itemDataString: String, position : Int) {
            this.position = position
            tv_item.text = itemDataString
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemData[position]!!, position)
        //val content : SearchData = searchItemData[position]!!
        //holder.tv_date.text = content.scheduleText
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, holder.adapterPosition, itemData[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return itemData.size
    }


    interface ItemClickListener {
        fun onClick(view: View, position: Int, itemId: String)
    }
    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}