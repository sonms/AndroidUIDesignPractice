package com.example.myappuidesignpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myappuidesignpractice.databinding.ItemLayoutBinding
import com.example.myappuidesignpractice.databinding.ItemSliderBinding


class ImageSliderAdapter() :
    RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder>() {
    private var context: Context? = null
    private lateinit var binding : ItemSliderBinding
    var sliderText = arrayListOf<String>()
    private var position = 0

    inner class MyViewHolder(private val binding : ItemSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        private var mView: TextView = binding.textSlider
        var itemPosition = 0
        fun bindSliderText(s: String) {
            itemPosition = this.adapterPosition
            mView.text = sliderText[itemPosition]

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindSliderText(sliderText[position])
    }

    override fun getItemCount(): Int {
        return sliderText.size
    }
}