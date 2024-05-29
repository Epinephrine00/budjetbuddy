package com.epinephrine00.buddyjet

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter


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
    private lateinit var myHelper : myDBHelper
    private lateinit var sqlDB : SQLiteDatabase
    private lateinit var thqlsodur : ListView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
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
        thqlsodur = findViewById(R.id.thqlsodur)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        myHelper = myDBHelper(this)

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
        startday = today.minusDays((today.dayOfWeek.value % 7).toLong())

        this.getWeeksData()
        this.listRenderer()
        swipeRefreshLayout.setOnRefreshListener {
            this.listRenderer()
            swipeRefreshLayout.isRefreshing = false
        }
        thqlsodur.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                // 스크롤 상태가 변경될 때 호출됩니다.
                // scrollState 값은 SCROLL_STATE_IDLE, SCROLL_STATE_TOUCH_SCROLL, SCROLL_STATE_FLING 중 하나입니다.
            }

            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                // 스크롤 중에 호출됩니다.
                // firstVisibleItem: 현재 화면에 보이는 첫 번째 아이템의 인덱스
                // visibleItemCount: 현재 화면에 보이는 아이템의 개수
                // totalItemCount: ListView의 총 아이템 개수

                if (firstVisibleItem > 0) {
                    //listRenderer()
                }
            }
        })


//        var entries = ArrayList<Entry>()
//        entries.add(Entry(0f, 10f))
//        entries.add(Entry(1f, 20f))
//        entries.add(Entry(2f, 15f))
//        entries.add(Entry(3f, 25f))
//        entries.add(Entry(4f, 18f))
//        this.plotData(entries)
    }

    fun plotfMonthlyData(year: Int, month: Int){
        var entries = ArrayList<Entry>()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun listRenderer(){
        lateinit var l : List<Any>
        for(i:Int in 0..30) {
            var objDate = today.minusDays(i.toLong())
            var dateData = getDataByDate(objDate)
            if (dateData.size>0){
                try {
                    l += listOf(objDate.format(DateTimeFormatter.ofPattern("MM월 dd일")))
                } catch(e:Exception){
                    l = listOf(objDate.format(DateTimeFormatter.ofPattern("MM월 dd일")))
                }
                l += dateData
            }
        }
        var adapter = MyAdapter(this, l)
        thqlsodur.adapter = adapter
    }

    class myDBHelper(context: Context?) : SQLiteOpenHelper(context, "groupDB", null, 1) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL("CREATE TABLE groupTBL (id INTEGER PRIMARY KEY AUTOINCREMENT, date DATE, rmador INTEGER, apah TEXT);")
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(db)
        }
    }

    class MyAdapter(context: Context, private val dataList: List<Any>) : ArrayAdapter<Any>(context, 0, dataList) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val data = getItem(position)!!
            when (data){
                is String -> {
                    val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_layout_2, parent, false)
                    val listViewDate = view.findViewById<TextView>(R.id.listViewDate)
                    listViewDate?.text = data

                    return view
                }
                is Map<*, *> -> {
                    val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_layout_1, parent, false)
                    //val idTextView = view.findViewById<TextView>(R.id.idTextView)
                    //val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
                    val numberTextView = view.findViewById<TextView>(R.id.numberTextView)
                    val stringTextView = view.findViewById<TextView>(R.id.stringTextView)

                    //idTextView.text = data["id"].toString()
                    //dateTextView.text = data["date"] as String
                    var rmador = data["rmador"] as? Int ?: 0
                    var apah = data["apah"] as? String ?: ""
                    Log.d("MainActivity", String.format("%d | %s", rmador, apah))
                    numberTextView?.text = rmador.toString()
                    stringTextView?.text = apah

                    return view
                }
            }
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_layout_2, parent, false)
            val listViewDate = view.findViewById<TextView>(R.id.listViewDate)
            listViewDate?.text = ""
            return view

        }

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
            var tmpdydlf = tmpDate.dayOfWeek.value
            var isToday : Boolean = tmpDate.isEqual(today)
            var tndlq = 0
            var wlcnf = 0
            var dataList = getDataByDate(tmpDate)
            Log.d("MainActivivty", i.toString())
            for (data in dataList) {
                Log.d("MainActivivty", "ID: ${data["id"]}, 날짜: ${data["date"]}, 금액: ${data["rmador"]}, 메모: ${data["apah"]}")
                if (data["rmador"].toString().toInt() < 0)
                    wlcnf -= data["rmador"].toString().toInt()
                else
                    tndlq += data["rmador"].toString().toInt()
            }
            addDayDataIntoCalander(tmpDate, tndlq, wlcnf, isToday, tmpdydlf%7, (i/7)+1)

        }

        loading.visibility = View.INVISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addDayDataIntoCalander(date:LocalDate, tndlq:Int, wlcnf:Int, isToday:Boolean, col:Int, row:Int){
        var year = date.year
        var month = date.format(DateTimeFormatter.ofPattern("MM")).toInt()
        var day = date.dayOfMonth
        var dydlf = date.dayOfWeek.value
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
                //Toast.makeText(this, String.format("%d년 %d월 %d일", year, month, day), Toast.LENGTH_SHORT).show()
                var isMirai:Boolean = date.isAfter(today)
                showDialog(year, month, day, isMirai)
            }
            true
        }

        gridLayout.addView(ll)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDialog(year:Int, month:Int, day:Int, isMirai:Boolean) {
        // 다이얼로그 빌더 생성
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.add_prediction_dialog, null)


        val rmadordlqfur = dialogLayout.findViewById<EditText>(R.id.rmadordlqfur)
        val predictionMemo = dialogLayout.findViewById<EditText>(R.id.predictionMemo)
        val tndlqrb = dialogLayout.findViewById<RadioButton>(R.id.tndlqrb)
        val wlcnfrb = dialogLayout.findViewById<RadioButton>(R.id.wlcnfrb)
        if (!isMirai){
            tndlqrb.setText("수입")
            wlcnfrb.setText("지출")
        }
        else {
            tndlqrb.setText("예상 수입")
            wlcnfrb.setText("예상 지출")
        }

        with(builder) {
            setTitle(String.format("%d년 %d월 %d일", year, month, day))
            setPositiveButton("추가") { dialog, which ->
                //Toast.makeText(context, "Accepted!", Toast.LENGTH_SHORT).show()
                try {
                    addNewPrediction(
                        year,
                        month,
                        day,
                        tndlqrb.isChecked,
                        rmadordlqfur.text.toString().toInt(),
                        predictionMemo.text.toString()
                    )
                }catch (e:Exception){
                    Toast.makeText(context, "입력 실패", Toast.LENGTH_SHORT).show()
                }
            }
            setNegativeButton("취소") { dialog, which ->
                Toast.makeText(context, "Dismissed!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            setView(dialogLayout)
            show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNewPrediction(year:Int, month:Int, day: Int, istndlq:Boolean, dlqfurrmador:Int, apah:String){
        sqlDB = myHelper.writableDatabase
        var multiplier = 1
        if (!istndlq)
            multiplier = -1
        try {
            val values = ContentValues().apply {
                put("date", String.format("%d-%02d-%02d", year, month, day))
                put("rmador", dlqfurrmador*multiplier)
                put("apah", apah)
            }
            sqlDB.insert("groupTBL", null, values)
            Toast.makeText(this, "입력 성공", Toast.LENGTH_SHORT).show()
            this.getWeeksData()
            listRenderer()

        } catch(e:Exception){
            Toast.makeText(this, "입력 실패", Toast.LENGTH_SHORT).show()
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataByDate(date: LocalDate): List<Map<String, Any>> {
        val db = myHelper.readableDatabase
        val projection = arrayOf("id", "date", "rmador", "apah")
        val selection = "date = ?"
        var year = date.year
        var month = date.format(DateTimeFormatter.ofPattern("MM")).toInt()
        var day = date.dayOfMonth
        val selectionArgs = arrayOf(String.format("%d-%02d-%02d", year, month, day))

        val cursor = db.query(
            "groupTBL",   // 테이블
            projection,   // 반환할 열
            selection,    // WHERE 절
            selectionArgs,// WHERE 절의 매개변수
            null,         // GROUP BY 절
            null,         // HAVING 절
            null          // ORDER BY 절
        )

        val dataList = mutableListOf<Map<String, Any>>()

        with(cursor) {
            while (moveToNext()) {
                val item = mutableMapOf<String, Any>()
                item["id"] = getInt(getColumnIndexOrThrow("id"))
                item["date"] = getString(getColumnIndexOrThrow("date"))
                item["rmador"] = getInt(getColumnIndexOrThrow("rmador"))
                item["apah"] = getString(getColumnIndexOrThrow("apah"))
                dataList.add(item)
            }
        }
        cursor.close()
        return dataList
    }

}