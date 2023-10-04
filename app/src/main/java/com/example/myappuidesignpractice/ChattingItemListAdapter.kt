package com.example.myappuidesignpractice

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myappuidesignpractice.databinding.ChattingItemBinding
import com.example.myappuidesignpractice.databinding.ItemLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//채팅
class ChattingItemListAdapter : RecyclerView.Adapter<ChattingItemListAdapter.ItemViewHolder>(){
    private lateinit var binding : ChattingItemBinding
    var chattingItemData = ArrayList<MessageData>()
    private lateinit var context : Context

    inner class ItemViewHolder(private val binding : ChattingItemBinding ) : RecyclerView.ViewHolder(binding.root) {
        private var position : Int? = null
        var message = binding.userMsg
        var user = binding.userName
        var userImage = binding.userImsg

        fun bind(itemData : MessageData, position : Int) {
            this.position = position
            if (itemData.sendId == "me") {
                message.text = itemData.message!!.toString()
                //userImage.setImageURI(Uri.parse(itemData.userImage!!.toString()))
                user.text = itemData.sendId
                user.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
            } else {
                message.text = itemData.message!!.toString()
                //userImage.setImageURI(Uri.parse(itemData.userImage!!.toString()))
                user.text = itemData.sendId
                user.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        binding = ChattingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = chattingItemData[position]


        if (currentItem.sendId == "me") {
            println("me")
            binding.userImsg.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.Main).launch {
                holder.user.text = currentItem.sendId
                holder.user.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
                holder.message.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
                holder.userImage.textAlignment = View.TEXT_ALIGNMENT_VIEW_END

                Glide.with(context)
                    .load(currentItem.userImage)
                    .fitCenter()
                    .apply(RequestOptions().override(300,300))
                    .into(holder.userImage)
            }

        } else {
            holder.user.text = currentItem.sendId
            holder.user.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
            holder.message.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
            holder.userImage.textAlignment = View.TEXT_ALIGNMENT_VIEW_START

        }

        holder.bind(currentItem, position)

    }

    override fun getItemCount(): Int {
        return chattingItemData.size
    }


    interface ItemClickListener {
        fun onClick(view: View, position: Int, itemId: String)
    }
    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}