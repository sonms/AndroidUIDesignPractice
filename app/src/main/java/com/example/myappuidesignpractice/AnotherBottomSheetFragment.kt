package com.example.myappuidesignpractice

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.example.myappuidesignpractice.databinding.FragmentAnotherBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [AnotherBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AnotherBottomSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var abBinding : FragmentAnotherBottomSheetBinding
    //필터로 선택한 데이터들을 외부로 전송하기 위한 리스너
    private var listener: OnSendFromBottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        abBinding = FragmentAnotherBottomSheetBinding.inflate(inflater, container, false)

        abBinding.filterNewest.setOnClickListener {
            Toast.makeText(requireActivity(), "최신 순", Toast.LENGTH_SHORT).show()
            if (listener == null) return@setOnClickListener
            listener?.sendValue("최신 순")
            dismiss()
        }

        /*abBinding.filterCloseDistance.setOnClickListener {
            Toast.makeText(requireActivity(), "가까운 순", Toast.LENGTH_SHORT).show()
            if (listener == null) return@setOnClickListener
            listener?.sendValue("가까운 순")
            dismiss()
        }*/

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
        }

        /*
        * if (listener == null) return@setOnClickListener
            listener?.sendValue("$selectTargetDate, ${participateNumberOfPeople}, $isCheckSmoke, $isCheckGender, $isCheckSchool")
            dismiss()
            * */

        return abBinding.root
    }


    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        /*val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = false
        }*/
        val dialog = BottomSheetDialog(requireContext()) //R.style.BottomSheetDialogTheme)
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