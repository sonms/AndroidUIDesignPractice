package com.example.myappuidesignpractice

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myappuidesignpractice.databinding.ItemLayoutBinding
import com.example.myappuidesignpractice.databinding.ItemSliderBinding


class ImageSliderAdapter(context: Context, sliderImage: Array<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder>() {
    private val context: Context
    private lateinit var binding : ItemSliderBinding
    private val sliderImage: Array<String>

    init {
        this.context = context
        this.sliderImage = sliderImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindSliderImage(sliderImage[position])
    }

    override fun getItemCount(): Int {
        return sliderImage.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mImageView: ImageView = binding.imageSlider

        fun bindSliderImage(imageURL: String?) {

        }
    }
}