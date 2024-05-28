package com.epinephrine00.buddyjet

import android.content.res.ColorStateList
import android.graphics.Color
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var monthTextView : TextView
    private lateinit var gridLayout : GridLayout
    private lateinit var loading : TextView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        monthTextView = findViewById(R.id.monthTextView)
        gridLayout = findViewById(R.id.gridLayout)
        loading = findViewById(R.id.loading)

        // 현재 년월 표시(상단)
        val currentTime = LocalDate.now()
        monthTextView.setText(currentTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월")))

        this.getWeeksData(currentTime)//.plusDays(5))
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeeksData(today:LocalDate){
        Log.d("MainActivity", "getMonthData Called!")

        var year = today.year
        var month = today.format(DateTimeFormatter.ofPattern("MM")).toInt()
        var day = today.dayOfMonth
        var dydlf = today.dayOfWeek.value%7
        Log.d("MainActivivty", String.format("%d년 %d월 %d일 %d번째 요일", year, month, day, dydlf))

        for(i:Int in 1..dydlf){
            var tmpDate = today.minusDays(i.toLong())
            var tmpday = tmpDate.dayOfMonth
            var tmpdydlf = tmpDate.dayOfWeek.value
            addDayDataIntoCalander(tmpday, tmpdydlf, 0, 10, false, (dydlf-i), 1)
        }
        addDayDataIntoCalander(day, dydlf, 100, 10, true, dydlf, 1)
        for(i:Int in 1..(6-dydlf)){
            var tmpDate = today.plusDays(i.toLong())
            var tmpday = tmpDate.dayOfMonth
            var tmpdydlf = tmpDate.dayOfWeek.value
            addDayDataIntoCalander(tmpday, tmpdydlf, 10*i, 20, false, (dydlf+i), 1)
        }
        for(i:Int in (7-dydlf)..(13-dydlf)){
            var tmpDate = today.plusDays(i.toLong())
            var tmpday = tmpDate.dayOfMonth
            var tmpdydlf = tmpDate.dayOfWeek.value
            addDayDataIntoCalander(tmpday, tmpdydlf, 85, 10*i, false, (dydlf+i)-7, 2)

        }

        loading.visibility = View.INVISIBLE
    }

    fun addDayDataIntoCalander(day:Int, dydlf:Int, tndlq:Int, wlcnf:Int, isToday:Boolean, col:Int, row:Int){
        val ll = LinearLayout(this).apply{
            orientation = LinearLayout.VERTICAL
            layoutParams = GridLayout.LayoutParams().apply {
                width = 0 //GridLayout.LayoutParams.WRAP_CONTENT
                height = GridLayout.LayoutParams.WRAP_CONTENT
                rowSpec = GridLayout.spec(row)
                columnSpec = GridLayout.spec(col, 1f)
            }
        }
        if (isToday){
            ll.setBackgroundColor(Color.parseColor("#E0E0E0"))
            if (tndlq>wlcnf){
                ll.setBackgroundColor(Color.parseColor("#E0E0F0"))
            }
            if (tndlq<wlcnf){
                ll.setBackgroundColor(Color.parseColor("#F0E0E0"))
            }
        }
        else if (tndlq>wlcnf){
            ll.setBackgroundColor(Color.parseColor("#EEEEFF"))
        }
        else if (tndlq<wlcnf){
            ll.setBackgroundColor(Color.parseColor("#FFEEEE"))
        }

        val tv1 = TextView(this)
        tv1.text = day.toString()
        if (dydlf==6)
            tv1.setTextColor(Color.parseColor("#2222EE"))
        else if (dydlf%7==0)
            tv1.setTextColor(Color.parseColor("#EE2222"))
        else
            tv1.setTextColor(Color.parseColor("#222222"))
        tv1.setTextSize(16f)
        tv1.gravity = Gravity.CENTER
        ll.addView(tv1, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ))

        val tv2 = TextView(this)
        tv2.text = String.format("+%d",tndlq)
        tv2.setTextColor(Color.parseColor("#2222EE"))
        tv2.setTextSize(10f)
        tv2.gravity = Gravity.CENTER
        if (tndlq==0)
            tv2.visibility = View.INVISIBLE
        ll.addView(tv2, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ))

        val tv3 = TextView(this)
        tv3.text = String.format("-%d",wlcnf)
        tv3.setTextColor(Color.parseColor("#EE2222"))
        tv3.setTextSize(10f)
        tv3.gravity = Gravity.CENTER
        if (wlcnf==0)
            tv3.visibility = View.INVISIBLE
        ll.addView(tv3, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ))

        ll.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                Toast.makeText(this, "LinearLayout touched", Toast.LENGTH_SHORT).show()
            }
            true
        }

        gridLayout.addView(ll)
    }
}