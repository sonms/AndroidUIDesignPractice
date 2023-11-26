package com.example.myappuidesignpractice

import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import com.example.myappuidesignpractice.databinding.ActivityChipAndBottomNavigationTestBinding
import com.google.android.material.chip.Chip
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class ChipAndBottomNavigationTestActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChipAndBottomNavigationTestBinding
    //칩 생성
    private var chipList = kotlin.collections.ArrayList<Chip>()
    private var selectFormattedTime : String? = ""
    private var selectTime : String? = ""
    private var hour1 = 0
    private var minute1 = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChipAndBottomNavigationTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tempList()

        binding.datePickerActionsBtm.setOnClickListener {
            val myCalender = Calendar.getInstance()
            val hour = myCalender[Calendar.HOUR_OF_DAY]
            val minute = myCalender[Calendar.MINUTE]
            val myTimeListener =
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    if (view.isShown) {
                        myCalender[Calendar.HOUR_OF_DAY] = hourOfDay
                        myCalender[Calendar.MINUTE] = minute
                        val tempS = hourOfDay.toString() + "시 " + minute + "분"
                        selectFormattedTime = LocalTime.parse(tempS, DateTimeFormatter.ofPattern("H시 m분")).format(
                            DateTimeFormatter.ofPattern("HH:mm"))

                        selectTime = if (hourOfDay > 12) {
                            val pm = hourOfDay - 12;
                            "오후 " + pm + "시 " + minute + "분"
                        } else {
                            hour1 = hourOfDay
                            minute1 = minute
                            "오전 " + hourOfDay + "시 " + minute + "분"
                        }
                        //selectTime = "${hourOfDay} 시 ${minute} 분"
                        println("${selectFormattedTime}")
                        println(hourOfDay)
                        binding.setTime.text = selectTime
                    }
                }
            val timePickerDialog = CustomTimePickerDialog(
            this, myTimeListener
        )

                /*TimePickerDialog(
                this,
                //여기서 테마 설정해서 커스텀하기
                android.R.style.ThemeHolo,
                myTimeListener,
                hour,
                minute,
                true
            )*/

            /*timePickerDialog.setTitle("시간 선택 :")
            timePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            */
            timePickerDialog.show()
        }

        binding.bottomTv.setOnClickListener {
            val date = LocalDate.of(LocalDate.now().year, LocalDate.now().month, 3)
            val dayOfWeek: DayOfWeek = date.dayOfWeek
            val tempDayOfWeek = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
            println("${LocalDate.now().year.toString()} 오늘, ${tempDayOfWeek.toString().substring(0, 1)}, 3)")
            println(LocalDate.now().monthValue.toString())
            binding.bottomEt.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.bottomEt.findFocus(), InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun tempList() {
        chipList.add(createNewChip("등교 a"))
        chipList.add(createNewChip("흡연 b"))
        chipList.add(createNewChip("남자"))

        for (i in chipList.indices) {
            // 마지막 Chip 뷰의 인덱스를 계산
            val lastChildIndex = binding.readSetFilterCg.childCount - 1

            // 마지막 Chip 뷰의 인덱스가 0보다 큰 경우에만
            // 현재 Chip을 바로 그 앞에 추가
            if (lastChildIndex >= 0) {
                binding.readSetFilterCg.addView(chipList[i], lastChildIndex)
            } else {
                // ChipGroup에 자식이 없는 경우, 그냥 추가
                binding.readSetFilterCg.addView(chipList[i])
            }
        }
    }


    private fun createNewChip(text: String): Chip {
        val chip = layoutInflater.inflate(R.layout.notice_board_chip_layout, null, false) as Chip
        chip.text = text
        //chip.isCloseIconVisible = false
        return chip
    }
}