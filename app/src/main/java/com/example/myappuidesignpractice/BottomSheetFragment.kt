package com.example.myappuidesignpractice.BottomSheetFragment

import android.app.Dialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.myappuidesignpractice.AnotherBottomSheetFragment
import com.example.myappuidesignpractice.R
import com.example.myappuidesignpractice.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AnotherBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BottomSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var abBinding : FragmentBottomSheetBinding
    //필터로 선택한 데이터들을 외부로 전송하기 위한 리스너
    private var listener: OnSendFromBottomSheetDialog? = null
    private var commentEditText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        abBinding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        /*abBinding.readCommentEt.requestFocus()
        val imm = requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(abBinding.readCommentEt.findFocus(), InputMethodManager.SHOW_IMPLICIT)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)*/
        /*val constraintLayout =abBinding.constraintLayout
        val keyboardSpacer = abBinding.keyboardSpacer
        val testTextView = abBinding.testtv*/

        /*constraintLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            constraintLayout.getWindowVisibleDisplayFrame(r)
            val screenHeight = constraintLayout.rootView.height
            val keypadHeight = screenHeight - r.bottom

            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)

            if (keypadHeight > screenHeight * 0.15) {
                // 키보드가 올라온 경우
                constraintSet.setMargin(R.id.testtv, ConstraintSet.BOTTOM, keypadHeight + 30)
            } else {
                // 키보드가 내려간 경우
                constraintSet.setMargin(R.id.testtv, ConstraintSet.BOTTOM, 30)
            }

            constraintSet.applyTo(constraintLayout)
        }*/

        abBinding!!.readCommentEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                abBinding!!.readSendComment.visibility = View.GONE
                abBinding!!.readEditSendComment.visibility = View.GONE
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Nothing to do in this method
            }

            override fun afterTextChanged(editable: Editable) {
                val newText = editable.toString()
                Log.e("commentTest", "After text changed: $newText")

                if (newText.isNotEmpty()) {
                    commentEditText = newText
                    abBinding.readSendComment.visibility = View.VISIBLE
                }
            }
        })

        abBinding.readSendComment.setOnClickListener {
            if (listener == null && commentEditText == "") return@setOnClickListener
            listener?.sendValue(commentEditText)
            dismiss()
        }
       /* abBinding.filterNewest.setOnClickListener {
            Toast.makeText(requireActivity(), "최신 순", Toast.LENGTH_SHORT).show()
            if (listener == null) return@setOnClickListener
            listener?.sendValue("최신 순")
            dismiss()
        }

        *//*abBinding.filterCloseDistance.setOnClickListener {
            Toast.makeText(requireActivity(), "가까운 순", Toast.LENGTH_SHORT).show()
            if (listener == null) return@setOnClickListener
            listener?.sendValue("가까운 순")
            dismiss()
        }*//*

        abBinding.filterLowestPrice.setOnClickListener {
            Toast.makeText(requireActivity(), "낮은 가격 순", Toast.LENGTH_SHORT).show()
            if (listener == null) return@setOnClickListener
            listener?.sendValue("낮은 가격 순")
            dismiss()
        }

        abBinding.filterNearingEnd.setOnClickListener {
            Toast.makeText(requireActivity(), "마감 임박 순", Toast.LENGTH_SHORT).show()
            if (listener == null) return@setOnClickListener
            listener?.sendValue("마감 임박 순")
            dismiss()
        }*/

        /*
        * if (listener == null) return@setOnClickListener
            listener?.sendValue("$selectTargetDate, ${participateNumberOfPeople}, $isCheckSmoke, $isCheckGender, $isCheckSchool")
            dismiss()
            * */

        return abBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        bottomSheet.layoutParams = layoutParams
    }

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        *//*val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = false
        }*//*
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                //setupFullHeight(it)
                //behaviour.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        return dialog
    }*/

    override fun onResume() {
        super.onResume()
        // EditText에 포커스 및 가상 키보드 표시
        Log.d("bottomsheet", "onresume")
        abBinding.readCommentEt.requestFocus()
        val manager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        manager!!.showSoftInput( abBinding.readCommentEt, InputMethodManager.SHOW_IMPLICIT)
    }

    interface OnSendFromBottomSheetDialog {
        fun sendValue(value: String)
    }

    fun setCallback(listener: OnSendFromBottomSheetDialog) {
        this.listener = listener
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AnotherBottomSheetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AnotherBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}