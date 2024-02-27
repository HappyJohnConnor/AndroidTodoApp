package com.example.todo4.utility

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.Calendar
import java.util.Date
import kotlin.properties.Delegates

class DatePickerDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private var year by Delegates.notNull<Int>()
    private var month by Delegates.notNull<Int>()
    private var day by Delegates.notNull<Int>()
    private var dueDate by Delegates.notNull<Long>()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val timeInMills = arguments?.getLong("time", 0) ?: 0


        if (timeInMills != 0L) {
            val c = Calendar.getInstance().apply { time = Date(timeInMills) }
            year = c.get(Calendar.YEAR)
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)

        } else {
            //Calender()を取得
            val calendar: Calendar = Calendar.getInstance()
            //年を示すフィールドの値を取得
            year = calendar.get(Calendar.YEAR)
            //月を示すフィールドの値を取得
            month = calendar.get(Calendar.MONTH)
            //日を示すフィールドの値を取得
            day = calendar.get(Calendar.DAY_OF_MONTH)
        }


        //指定された日付でDatePickerを作成して返す
        return DatePickerDialog(
            requireContext(),
            this,
            year,
            month,
            day
        )
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val cal = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }
        val bundle = bundleOf(BUNDLE_KEY_DUEDATE to cal.timeInMillis)
        setFragmentResult(FRAGMENT_KEY, bundle)

    }

    companion object {
        const val FRAGMENT_KEY = "from dialog"
        const val BUNDLE_KEY_DUEDATE = "bundle key"
    }
}