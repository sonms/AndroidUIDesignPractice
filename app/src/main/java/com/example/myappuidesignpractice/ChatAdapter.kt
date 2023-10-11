package com.example.myappuidesignpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myappuidesignpractice.databinding.ChatItemBinding
import com.example.myappuidesignpractice.databinding.ItemLayoutBinding

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ItemViewHolder>(){
    private lateinit var binding : ChatItemBinding
    var itemData = mutableListOf<String?>()
    private lateinit var context : Context

    private var chat2Adapter : Chat2Adapter? = null
    var chat2ItemData = ArrayList<String>()
    private var manager : LinearLayoutManager? = null

    //클릭된 아이템의 위치를 저장할 변수
    private var selectedItem = -1

    inner class ItemViewHolder(private val binding : ChatItemBinding ) : RecyclerView.ViewHolder(binding.root) {
        private var position : Int? = null

        fun bind(itemDataString: String, position : Int) {
            //이중 리사이클러뷰
            chat2Adapter = Chat2Adapter()
            manager = LinearLayoutManager(context)
            binding.chat2Rv.setHasFixedSize(true)
            binding.chat2Rv.layoutManager = manager
            chat2Adapter!!.setReplyCommentData(chat2ItemData)

            this.position = position
            binding.chatTv.text = itemDataString
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemData[position]!!, position)
        //val content : SearchData = searchItemData[position]!!
        //holder.tv_date.text = content.scheduleText
        /*holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, holder.adapterPosition, chat2ItemData[holder.adapterPosition])
        }*/
        if (position == selectedItem) {
            // 클릭된 아이템 디자인 변경 로직
            // 예: itemContent.setTextColor(Color.RED)
        } else {
            // 클릭되지 않은 아이템 디자인 변경 로직
            // 예: itemContent.setTextColor(Color.BLACK)
        }

        // 아이템 클릭 리스너 설정
        binding.root.setOnClickListener {
            val clickedPosition = holder.adapterPosition
            if (clickedPosition != RecyclerView.NO_POSITION) {
                selectedItem = clickedPosition // 클릭된 아이템 위치 업데이트
                notifyDataSetChanged() // 어댑터 갱신 요청
            }
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

    private fun setMultipleSelection(adapterPosition : Int) {
        //if (itemData.get(adapterPosition).isSelected())
    }

}