package com.example.myappuidesignpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myappuidesignpractice.databinding.ActivityAddBtnBinding

class AddBtnActivity : AppCompatActivity() {
    private lateinit var abinding : ActivityAddBtnBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        abinding = ActivityAddBtnBinding.inflate(layoutInflater)

        setContentView(abinding.root)

        val btn = Button(this).apply {
            width = 200
            height = 200
            setOnClickListener {
                println("ciclcc")
            }
        }

        abinding.addBtnLl.addView(btn)
    }
}