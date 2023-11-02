package com.example.myappuidesignpractice

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myappuidesignpractice.Data.practiceData
import com.example.myappuidesignpractice.databinding.ItemLayoutBinding

//채팅방
class ChatItemListAdapter : RecyclerView.Adapter<ChatItemListAdapter.ItemViewHolder>(){
    private lateinit var binding : ItemLayoutBinding
    var itemData = ArrayList<practiceData>()
    private lateinit var context : Context
    private var selectedItem = 0

    inner class ItemViewHolder(private val binding : ItemLayoutBinding ) : RecyclerView.ViewHolder(binding.root) {
        private var position : Int? = null
        var tv_item = binding.itemTv
        fun bind(itemDataString: practiceData, position : Int) {
            this.position = position
            tv_item.text = itemDataString.name

            binding.root.setOnClickListener {
                val clickedPosition = position
                /*if (clickedPosition != RecyclerView.NO_POSITION) {
                    selectedItem = clickedPosition // 클릭된 아이템 위치 업데이트
                    notifyDataSetChanged() // 어댑터 갱신 요청
                }*/

                //중복선택가능
                setMultipleSelection(binding, itemData[clickedPosition].name, clickedPosition)
                //onItemClickListener?.let { it(expense) }            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemData[position], position)
        //val content : SearchData = searchItemData[position]!!
        //holder.tv_date.text = content.scheduleText
        /*holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, holder.adapterPosition, itemData[holder.adapterPosition])
        }*/

        // 아이템 클릭 리스너 설정

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

    private fun setMultipleSelection(binding: ItemLayoutBinding, s: String?, adapterPosition : Int) {
        if(itemData[adapterPosition].isSelected){
            itemData[adapterPosition].isSelected = false
            changeBackground(binding, adapterPosition)
        }else{
            itemData[adapterPosition].isSelected = true
            changeBackground(binding, adapterPosition)
        }
        Log.e("arrayList", itemData.toString());
        notifyDataSetChanged()
    }

    private fun changeBackground(binding: ItemLayoutBinding, position: Int) {
        if(itemData[position].isSelected){
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(context , R.color.purple_200)) //승인
            binding.itemLl.backgroundTintList = colorStateList
        }else{
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(context , R.color.black)) //승인
            binding.itemLl.backgroundTintList = colorStateList
        }

    }

}