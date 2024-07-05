package com.example.myappuidesignpractice

import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import java.util.*

class CustomTimePickerDialog(private val context: Context, private val t : TimePickerDialog.OnTimeSetListener) {
    private var onTimeSetListener: TimePickerDialog.OnTimeSetListener? = t
    private var h = 0
    private var m = 0

    fun show() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
            t,
            hour,
            minute,
            false
        )
        h = hour
        m = minute


        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "확인") { dialog, which ->
            /*val timePicker = (dialog as TimePickerDialog)
            onTimeSetListener?.onTimeSet(
                null,
                h,
                m
            )*/
            t
        }

        timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "취소") { dialog, which ->
            dialog.dismiss()
        }

        timePickerDialog.setTitle("시간을 지정해주세요")
        /*timePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        */timePickerDialog.show()
    }

    fun setOnTimeSetListener(listener: TimePickerDialog.OnTimeSetListener) {
        onTimeSetListener = listener
    }

    /*private fun setCustomLayout() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.custom_time_picker_dialog, null)

        // 제목 설정
        val dialogTitle = view.findViewById<TextView>(R.id.dialogTitle)
        dialogTitle.text = "Select Time"

        // TimePicker 설정
        val timePicker = view.findViewById<TimePicker>(R.id.timePicker)
        timePicker.setIs24HourView(true)
        timePicker.hour = initialHourOfDay
        timePicker.minute = initialMinute

        setView(view)
    }*/

    companion object {
        private const val HOUR = "hour"
        private const val MINUTE = "minute"
        //설정한 시간 저장
        fun saveInstanceState(timePickerDialog: CustomTimePickerDialog): Bundle {
            val bundle = Bundle()
            val calendar = Calendar.getInstance()
            bundle.putInt(HOUR, calendar.get(Calendar.HOUR_OF_DAY))
            bundle.putInt(MINUTE, calendar.get(Calendar.MINUTE))
            return bundle
        }

        fun restoreInstanceState(bundle: Bundle, context: Context, t : TimePickerDialog.OnTimeSetListener): CustomTimePickerDialog {
            val timePickerDialog = CustomTimePickerDialog(context, t)
            timePickerDialog.show()
            return timePickerDialog
        }
    }
}

/*
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.TimePicker

class com.example.myappuidesignpractice.CustomTimePickerDialog(
    context: Context,
    listener: TimePickerDialog.OnTimeSetListener?,
    hourOfDay: Int,
    minute: Int,
    is24HourView: Boolean
) : TimePickerDialog(context, listener, hourOfDay, minute, is24HourView) {

    companion object {
        private const val KEY_HOUR = "hour"
        private const val KEY_MINUTE = "minute"
        private const val KEY_IS_24_HOUR_VIEW = "is24HourView"
    }

    override fun onSaveInstanceState(): Bundle {
        val bundle = super.onSaveInstanceState()
        bundle.putInt(KEY_HOUR, timePicker.currentHour)
        bundle.putInt(KEY_MINUTE, timePicker.currentMinute)
        bundle.putBoolean(KEY_IS_24_HOUR_VIEW, timePicker.is24HourView)
        return bundle
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val hour = savedInstanceState.getInt(KEY_HOUR)
        val minute = savedInstanceState.getInt(KEY_MINUTE)
        val is24HourView = savedInstanceState.getBoolean(KEY_IS_24_HOUR_VIEW)
        updateTime(hour, minute)
        setIs24HourView(is24HourView)
    }
}*/
