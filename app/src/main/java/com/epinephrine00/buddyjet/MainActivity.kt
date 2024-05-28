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

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MainActivity : AppCompatActivity() {
    private lateinit var monthTextView : TextView
    private lateinit var gridLayout : GridLayout
    private lateinit var loading : TextView
    private lateinit var gatsu : TextView
    private lateinit var prevWeek : Button
    private lateinit var today : LocalDate
    private lateinit var startday : LocalDate
    private lateinit var nextWeek : Button
    private lateinit var lineChart : LineChart
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        monthTextView = findViewById(R.id.monthTextView)
        gridLayout = findViewById(R.id.gridLayout)
        loading = findViewById(R.id.loading)
        prevWeek = findViewById(R.id.prevWeek)
        nextWeek = findViewById(R.id.nextWeek)
        lineChart = findViewById(R.id.lineChart)
        gatsu = findViewById(R.id.gatsu)

        prevWeek.setOnClickListener {
            startday = startday.minusDays(7)
            this.getWeeksData()
        }
        nextWeek.setOnClickListener {
            startday = startday.plusDays(7)
            this.getWeeksData()
        }

        // 현재 년월 표시(상단)
        today = LocalDate.now()
        startday = today.minusDays((today.dayOfWeek.value%7).toLong())

        this.getWeeksData()

//        var entries = ArrayList<Entry>()
//        entries.add(Entry(0f, 10f))
//        entries.add(Entry(1f, 20f))
//        entries.add(Entry(2f, 15f))
//        entries.add(Entry(3f, 25f))
//        entries.add(Entry(4f, 18f))
//        this.plotData(entries)
    }

    fun plotData(entries:ArrayList<Entry>){
        val dataSet = LineDataSet(entries, "지출")
        dataSet.color  = Color.RED
        dataSet.lineWidth = 3f
        dataSet.setCircleColor(Color.RED)
        dataSet.setDrawCircleHole(false)
        dataSet.circleRadius = 1.5f
        dataSet.setDrawValues(false)
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.description.isEnabled = false

        // hide Chart Description
        val description = Description()
        description.isEnabled = false
        lineChart.description = description
        // hide....what?
        lineChart.xAxis.isEnabled = false
        lineChart.axisLeft.isEnabled = false
        lineChart.axisRight.isEnabled = false
        // hide grid lines
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.axisRight.setDrawGridLines(false)
        // hide legend
        lineChart.legend.isEnabled = false
        lineChart.setVisibleXRange(0f, 31f)
        lineChart.invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeeksData(){
        Log.d("MainActivity", "getMonthData Called!")

        monthTextView.setText(startday.plusDays(4).format(DateTimeFormatter.ofPattern("yyyy년 MM월")))
        gatsu.setText(String.format("%d월", startday.plusDays(4).format(DateTimeFormatter.ofPattern("MM")).toInt()))

//        var todayYear = today.year
//        var todayMonth = today.format(DateTimeFormatter.ofPattern("MM")).toInt()
//        var todayDay = today.dayOfMonth
//        var todaydydlf = today.dayOfWeek.value%7
        //Log.d("MainActivivty", String.format("%d년 %d월 %d일 %d번째 요일", year, month, day, dydlf))

        for(i:Int in 0..13){
            var tmpDate = startday.plusDays(i.toLong())
            var tmpday = tmpDate.dayOfMonth
            var tmpdydlf = tmpDate.dayOfWeek.value
            var isToday : Boolean = tmpDate.isEqual(today)
            var tndlq = 10*i
            var wlcnf = 50
            addDayDataIntoCalander(tmpday, tmpdydlf, tndlq, wlcnf, isToday, tmpdydlf%7, (i/7)+1)

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
        ll.setBackgroundColor(Color.parseColor("#FFFFFF"))
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