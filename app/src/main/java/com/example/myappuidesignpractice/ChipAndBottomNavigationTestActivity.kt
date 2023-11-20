package com.example.myappuidesignpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myappuidesignpractice.databinding.ActivityChipAndBottomNavigationTestBinding
import com.google.android.material.chip.Chip

class ChipAndBottomNavigationTestActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChipAndBottomNavigationTestBinding
    //칩 생성
    private var chipList = kotlin.collections.ArrayList<Chip>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChipAndBottomNavigationTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tempList()

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

    private fun tempList() {
        chipList.add(createNewChip("등교 a"))
        chipList.add(createNewChip("흡연 b"))
        chipList.add(createNewChip("남자"))
    }


    private fun createNewChip(text: String): Chip {
        val chip = layoutInflater.inflate(R.layout.notice_board_chip_layout, null, false) as Chip
        chip.text = text
        //chip.isCloseIconVisible = false
        return chip
    }
}