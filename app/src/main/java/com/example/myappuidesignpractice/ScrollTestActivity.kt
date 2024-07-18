package com.example.myappuidesignpractice

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myappuidesignpractice.databinding.ActivityScrollTestBinding


class ScrollTestActivity : AppCompatActivity() {
    private var mBinding : ActivityScrollTestBinding? = null
    private var commentEditText = ""
    private lateinit var mChildOfContent: ViewGroup
    private var currentlyScrolled = 0
    private var totalScreenHeight = 0
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityScrollTestBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)

        mChildOfContent = findViewById(R.id.read_comment_ll)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        totalScreenHeight = displayMetrics.heightPixels

        //setKeyboardVisibilityListener()

//        mBinding!!.readCommentEt.setOnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) {
//                handleShiftUp(v)
//            }
//        }
        mBinding!!.teb.setOnClickListener {
            val loadingDialog = LoadingProgressDialog(this)
            loadingDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            //loadingDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            //로딩창
            loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialog.window?.attributes?.windowAnimations = R.style.FullScreenDialog // 위에서 정의한 스타일을 적용


            loadingDialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            loadingDialog.show()
        }

        mBinding!!.readCommentEt.setOnClickListener {
            mBinding!!.readCommentLl.visibility = View.VISIBLE
            // 자동으로 포커스 줘서 댓글 달게 하기
            mBinding!!.readCommentEt.text = null
            mBinding!!.readCommentLl.requestFocus()
            commentEditText = ""
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(mBinding!!.readCommentLl.findFocus(), InputMethodManager.SHOW_IMPLICIT)
            this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

            /*setKeyboardVisibilityListener(object : OnKeyboardVisibilityListener {
                override fun onVisibilityChanged(visible: Boolean) {
                    // 키보드가 보이면 view를 보이게 하고, 아니면 숨기기
                    if (visible) {
                        Log.d("tet", "올")
                    } else {
                        Log.d("tet", "내")
                    }
                }
            })*/
            handleShiftUp(mBinding!!.readCommentLl)
        }

        mBinding!!.readCommentEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mBinding!!.readSendComment.visibility = View.GONE
                mBinding!!.readEditSendComment.visibility = View.GONE
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Nothing to do in this method
            }

            override fun afterTextChanged(editable: Editable) {
                val newText = editable.toString()
                Log.e("commentTest", "After text changed: $newText")

                if (newText.isNotEmpty()) {
                    commentEditText = newText
                }
            }
        })


    }

    interface OnKeyboardVisibilityListener {
        fun onVisibilityChanged(visible : Boolean)
    }

    private fun handleShiftUp(focusedView: View?) {
        if (focusedView == null) {
            Toast.makeText(this, "focusedView is null", Toast.LENGTH_SHORT).show()
            return
        }
        val location = IntArray(2)
        focusedView.getLocationInWindow(location)
        val absY = location[1]
        val oneFourth: Int = totalScreenHeight / 4
        if (absY > oneFourth) {
            val distanceToScroll: Int = absY - oneFourth + currentlyScrolled
            currentlyScrolled = distanceToScroll
            mChildOfContent.scrollTo(0, distanceToScroll)
            Toast.makeText(this, "Shift up $distanceToScroll", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setKeyboardVisibilityListener(onKeyboardVisibilityListener: OnKeyboardVisibilityListener) {
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            private val rect = Rect()
            override fun onGlobalLayout() {
                mChildOfContent.getWindowVisibleDisplayFrame(rect)
                val heightDiff = mChildOfContent.rootView.height - (rect.bottom - rect.top)
                if (heightDiff > totalScreenHeight / 4) {
                    val focusedView = currentFocus
                    handleShiftUp(focusedView)
                } else {
                    currentlyScrolled = 0
                    mChildOfContent.scrollTo(0, 0)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        /*setKeyboardVisibilityListener(object : OnKeyboardVisibilityListener {
            override fun onVisibilityChanged(visible: Boolean) {
                if (visible) {
                    Log.d("READ Keyboard test", "키보드 올리기")
                    mBinding!!.view.visibility = View.VISIBLE
                } else {
                    Log.d("READ Keyboard test", "키보드 내리기")
                    mBinding!!.view.visibility = View.GONE
                    //키보드가 숨겨졌을 때
                    mBinding!!.readCommentLl.visibility = View.VISIBLE
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed(java.lang.Runnable {
                        mBinding!!.readCommentEt.clearFocus()
                    },1000)
                    mBinding!!.readCommentEt.text = null
                }
            }
        })*/
    }
}