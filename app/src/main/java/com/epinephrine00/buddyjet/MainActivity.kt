package com.epinephrine00.buddyjet

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var monthTextView : TextView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        monthTextView = findViewById(R.id.monthTextView)

        // 현재 년월 표시(상단)
        val monthFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월")
        val currentTime = LocalDateTime.now()
        Log.d("MainActivity", currentTime.format(monthFormatter))
        Log.d("MainActivity", currentTime.)
        monthTextView.setText(currentTime.format(monthFormatter))

        this.getMonthData(currentTime.year, currentTime.month.toString())



    }

    fun getMonthData(year:Int, mon:String){
        Log.d("MainActivity", "getMonthData Called!")
        Log.d("MainActivity", year.toString())
        Log.d("MainActivity", mon)
    }
}