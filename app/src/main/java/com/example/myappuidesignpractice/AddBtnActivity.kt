package com.example.myappuidesignpractice

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.myappuidesignpractice.databinding.ActivityAddBtnBinding

class AddBtnActivity : AppCompatActivity() {
    private lateinit var abinding : ActivityAddBtnBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        abinding = ActivityAddBtnBinding.inflate(layoutInflater)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.back_arrow_icon)
        setContentView(abinding.root)

        abinding.add.setOnClickListener {
            //버튼 레이아웃 설정
            val btn = Button(this).apply {
                width = 100
                height = 100
                setOnClickListener {
                    println("ciclcc")
                }
                setBackgroundResource(R.color.black)
                setBackgroundResource(R.drawable.baseline_directions_car_24)
                //색변경 겹침
                //backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@AddBtnActivity, R.color.black))
            }
            abinding.addBtnLl.addView(btn)
        }




    }
}
