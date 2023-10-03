package com.example.myappuidesignpractice

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myappuidesignpractice.databinding.ActivityChattingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ChattingActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityChattingBinding
    private var adapter : ChattingItemListAdapter? = null
    private var chatData = ArrayList<MessageData>()
    private var manager : LinearLayoutManager = LinearLayoutManager(this)
    private var chattingRoomData = ""

    private var sendMessage = ""

    private lateinit var imageFileToServer : File
    private var imageUri : Uri? = null

    companion object {
        const val Request_Gallery = 1
        const val gallery = 101
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        chattingRoomData = intent.getStringExtra("name") as String


        setInitData()


       /* //아이템 클릭 시 바꾸기
        adapter!!.setItemClickListener(object : ChattingItemListAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, itemId: String) {
                val temp = chatData[position]


            }
        })*/

        //갤러리 열어서 사진가져오는 버튼
        mBinding.addEt.setOnClickListener {
            getGallery()
        }

        mBinding.messageET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }
            override fun afterTextChanged(editable: Editable) {
                sendMessage = editable.toString()
            }
        })

        mBinding.messageSendIV.setOnClickListener {
            if (imageUri != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    chatData.add(MessageData("me", sendMessage, imageUri))
                    mBinding.messageET.text.clear()
                }
                mBinding.setFrame.visibility = View.GONE
                imageUri = null
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    chatData.add(MessageData("user1", sendMessage, null))
                    mBinding.messageET.text.clear()
                }
                mBinding.setFrame.visibility = View.GONE
                imageUri = null
            }
            adapter!!.notifyDataSetChanged()
        }
    }

    private fun setInitData() {
        mBinding.messageTopName.text = chattingRoomData

        adapter = ChattingItemListAdapter()
        adapter!!.chattingItemData = chatData
        mBinding.chattingRv.adapter = adapter
        mBinding.chattingRv.setHasFixedSize(true)
        mBinding.chattingRv.layoutManager = manager
    }

    private fun getGallery() {
       /* val writePermission = ContextCompat.checkSelfPermission(this@ChattingActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this@ChattingActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this@ChattingActivity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE), Request_Gallery)
        } else {

        }*/
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )
        imageResult.launch(intent)
    }

    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == RESULT_OK) {
            imageUri = it.data?.data

            //나중에 서버에서 원하는 형식으로 변환하기 위한 곳
            /*imageUri?.let {
                imageFileToServer = File()
            }*/
            mBinding.setFrame.visibility = View.VISIBLE

            Glide.with(this@ChattingActivity)
                .load(imageUri)
                .fitCenter()
                .apply(RequestOptions().override(300,300))
                .into(mBinding.setImage)
        }

    }

    /*private fun getPath(uri : Uri) : String {

    }*/
}