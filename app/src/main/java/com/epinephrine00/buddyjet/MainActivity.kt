package com.epinephrine00.buddyjet

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
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
import android.widget.RadioGroup
import android.widget.Space
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs


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
    private var memoizationDateBudget:MutableMap<LocalDate, Int> = mutableMapOf()
    private lateinit var wlcnfdPtks : TextView
    private lateinit var skadmswksrh : TextView
    private lateinit var djfakTmfrjrkxdma : TextView
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
        wlcnfdPtks = findViewById(R.id.wlcnfdPtks)
        skadmswksrh = findViewById(R.id.skadmswksrh)
        djfakTmfrjrkxdma = findViewById(R.id.djfakTmfrjrkxdma)

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

        //Log.d("GetOldestDate", this.getOldestDate().toString())


//        var entries = ArrayList<Entry>()
//        entries.add(Entry(0f, 10f))
//        entries.add(Entry(1f, 20f))
//        entries.add(Entry(2f, 15f))
//        entries.add(Entry(3f, 25f))
//        entries.add(Entry(4f, 18f))
//        this.plotData(entries)

        //this.getDateBudget(today)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun plotfMonthlyData(date:LocalDate){
        memoizationDateBudget = mutableMapOf()
        var lastDate = date.plusDays(30)
        var d = lastDate.dayOfMonth
        lastDate = lastDate.minusDays(d.toLong())
        d = date.dayOfMonth
        var datePointer = date.minusDays((d-1).toLong())
        var wlcnfgkqrP : Int = 0
//        while (! datePointer.isAfter(lastDate)){
//            Log.d("memoization", datePointer.format(DateTimeFormatter.ofPattern("MM월 dd일")))
//            Log.d("memoization", getDateBudget(datePointer).toString())
//            datePointer = datePointer.plusDays(1)
//        }

        var entries_BeforeToday_dPtks = ArrayList<Entry>()
        var entries_AfterToday_dPtks = ArrayList<Entry>()
        var entries_BeforeToday_wlcnf = ArrayList<Entry>()
        var entries_AfterToday_wlcnf = ArrayList<Entry>()
        while (! datePointer.isAfter(today)){
            wlcnfgkqrP += getDatewlcnf(datePointer)
            Log.d("memoization", datePointer.format(DateTimeFormatter.ofPattern("MM월 dd일")))
            Log.d("memoization", getDateBudget(datePointer).toString())
            Log.d("memoization", wlcnfgkqrP.toString())
            entries_BeforeToday_wlcnf.add(Entry(datePointer.dayOfMonth-1.toFloat(), abs(wlcnfgkqrP).toFloat()))
            entries_BeforeToday_dPtks.add(Entry(datePointer.dayOfMonth-1.toFloat(), getDateBudget(datePointer).toFloat()))
            datePointer = datePointer.plusDays(1)
        }
        while (! datePointer.isAfter(lastDate)){
            wlcnfgkqrP += getDatewlcnf(datePointer)
            Log.d("memoization", datePointer.format(DateTimeFormatter.ofPattern("MM월 dd일")))
            Log.d("memoization", getDateBudget(datePointer).toString())
            Log.d("memoization", wlcnfgkqrP.toString())
            entries_AfterToday_wlcnf.add(Entry(datePointer.dayOfMonth-1.toFloat(), abs(wlcnfgkqrP).toFloat()))
            entries_AfterToday_dPtks.add(Entry(datePointer.dayOfMonth-1.toFloat(), getDateBudget(datePointer).toFloat()))
            datePointer = datePointer.plusDays(1)
        }
        if (date.isAfter(today)){
            skadmswksrh.text = String.format("%d원을 쓸 것 같고", abs(wlcnfgkqrP))
            wlcnfdPtks.text = String.format("", getDateBudget(today))
        }
        else {
            wlcnfdPtks.text = String.format("%d원을 썼고", abs(wlcnfgkqrP))
            skadmswksrh.text = String.format("%d원이 남았어요", getDateBudget(today))
        }
        djfakTmfrjrkxdma.text = String.format("%d원 남을 것 같아요", getDateBudget(datePointer))

        val dataSet_dPtks_BeforeToday = LineDataSet(entries_BeforeToday_dPtks, "잔고")
        val dataSet_dPtks_AfterToday = LineDataSet(entries_AfterToday_dPtks, "예상 잔고")
        val dataSet_wlcnf_BeforeToday = LineDataSet(entries_BeforeToday_wlcnf, "잔고")
        val dataSet_wlcnf_AfterToday = LineDataSet(entries_AfterToday_wlcnf, "예상 잔고")
        dataSet_dPtks_BeforeToday.color = Color.GREEN
        dataSet_dPtks_BeforeToday.lineWidth = 3f
        dataSet_dPtks_BeforeToday.setCircleColor(Color.GREEN)
        dataSet_dPtks_BeforeToday.setDrawCircleHole(false)
        dataSet_dPtks_BeforeToday.circleRadius = 1.5f
        dataSet_dPtks_BeforeToday.setDrawValues(false)
        dataSet_dPtks_AfterToday.color = Color.GREEN
        dataSet_dPtks_AfterToday.lineWidth = 3f
        dataSet_dPtks_AfterToday.setCircleColor(Color.GREEN)
        dataSet_dPtks_AfterToday.setDrawCircleHole(false)
        dataSet_dPtks_AfterToday.circleRadius = 1f
        dataSet_dPtks_AfterToday.setDrawValues(false)
        dataSet_dPtks_AfterToday.enableDashedLine(0.1f, 1f, 0f)
        dataSet_dPtks_AfterToday.fillAlpha = 0
        dataSet_wlcnf_BeforeToday.color = Color.RED
        dataSet_wlcnf_BeforeToday.lineWidth = 3f
        dataSet_wlcnf_BeforeToday.setCircleColor(Color.RED)
        dataSet_wlcnf_BeforeToday.setDrawCircleHole(false)
        dataSet_wlcnf_BeforeToday.circleRadius = 1.5f
        dataSet_wlcnf_BeforeToday.setDrawValues(false)
        dataSet_wlcnf_AfterToday.color = Color.RED
        dataSet_wlcnf_AfterToday.lineWidth = 3f
        dataSet_wlcnf_AfterToday.setCircleColor(Color.RED)
        dataSet_wlcnf_AfterToday.setDrawCircleHole(false)
        dataSet_wlcnf_AfterToday.circleRadius = 1f
        dataSet_wlcnf_AfterToday.setDrawValues(false)
        dataSet_wlcnf_AfterToday.enableDashedLine(0.1f, 1f, 0f)
        dataSet_wlcnf_AfterToday.fillAlpha = 0

//        var entries = ArrayList<Entry>()
//        entries.add(Entry(0f, 10f))
//        entries.add(Entry(1f, 20f))
//        entries.add(Entry(2f, 15f))
//        entries.add(Entry(3f, 25f))
//        entries.add(Entry(4f, 18f))
//        val dataSet = LineDataSet(entries, "지출")
//        dataSet.color  = Color.RED
//        dataSet.lineWidth = 3f
//        dataSet.setCircleColor(Color.RED)
//        dataSet.setDrawCircleHole(false)
//        dataSet.circleRadius = 1.5f
//        dataSet.setDrawValues(false)
//        val lineData = LineData(dataSet)
        var lineData = LineData()
        lineData.addDataSet(dataSet_dPtks_BeforeToday)
        lineData.addDataSet(dataSet_dPtks_AfterToday)
        lineData.addDataSet(dataSet_wlcnf_BeforeToday)
        lineData.addDataSet(dataSet_wlcnf_AfterToday)




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
        lineChart.setVisibleYRangeMinimum(0f, YAxis.AxisDependency.LEFT)
        lineChart.invalidate()
        //대충 이거 추가하면 거의 끝일듯?

        // 1. 가장 최근 예산액을 구한다
        // 2. 없거나 startday와 같은 월의 1일 이후라면 가장 오래된 날짜로부터 순차적으로 계산한다
        // 3. startday와 같은 월의 1일 이전까진 딱히 그냥 싹다 더해버려도 상관X 어차피 그거 집계 안됨
        // 4. 미래의 예산은 점선으로 plotting한다
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateBudget(date:LocalDate) : Int{
        if (date.isBefore(getOldestDate()))
            return 0
        if (date in memoizationDateBudget){
            return memoizationDateBudget[date] as Int
        }
        // 메모이제이션 이용한 재귀식 함수로 날짜별 예산을 계산

        val dateData = getDataByDate(date)
        var startIndex :Int = -1
        var index : Int = 0
        for (data in dateData){
            var apah = data["apah"] as? String ?: ""
            if (apah == "SysBudSet"){ // 메모가 SysBudSet이라면 예산 설정으로 취급
                startIndex = index
            }
            index++
        }
        var budget : Int = 0
        if (startIndex==-1){
            budget = getDateBudget(date.minusDays(1))
            startIndex = 0
        }
        for (data in dateData.drop(startIndex)){
            var apah = data["apah"] as? String ?: ""
            if (apah == "SysBudSet")
                budget = data["rmador"] as Int // SysBudSet일 때 예산을 rmador으로 초기화
            else
                budget += data["rmador"] as Int //
        }
        memoizationDateBudget[date] = budget
//        Log.d("memoization", date.format(DateTimeFormatter.ofPattern("MM월 dd일")))
//        Log.d("memoization", budget.toString())
        return budget
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDatewlcnf(date:LocalDate) : Int{
        val dateData = getDataByDate(date)
        var r: Int = 0
        for (data in dateData){
            var rmador = data["rmador"] as? Int ?: 0
            if (rmador<0){
                r += rmador
            }
        }
        return r
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun listRenderer(){
        var l : List<Any> = listOf("소비 내역")
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

    fun deleteData(id: Int): Boolean {
        val db = myHelper.writableDatabase
        return db.delete("groupTBL", "id=?", arrayOf(id.toString())) > 0
    }
    inner class MyAdapter(context: Context, private val dataList: List<Any>) : ArrayAdapter<Any>(context, 0, dataList) {
        @RequiresApi(Build.VERSION_CODES.O)
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
                    view.setOnClickListener {
                        val isDeleted = this@MainActivity.deleteData(data["id"] as? Int ?:0)
                        if (isDeleted) {
                            // 삭제 성공
                            Toast.makeText(this@MainActivity, "데이터 삭제 성공!", Toast.LENGTH_SHORT).show()
                            this@MainActivity.listRenderer()
                            this@MainActivity.getWeeksData()
                        } else {
                            // 삭제 실패
                            Toast.makeText(this@MainActivity, "데이터 삭제 실패!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    return view
                }
            }
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_layout_2, parent, false)
            val listViewDate = view.findViewById<TextView>(R.id.listViewDate)
            listViewDate?.text = ""
            return view

        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeeksData(){
        Log.d("MainActivity", "getMonthData Called!")

        monthTextView.setText(startday.plusDays(4).format(DateTimeFormatter.ofPattern("yyyy년 MM월")))
        gatsu.setText(String.format("%d월", startday.plusDays(4).format(DateTimeFormatter.ofPattern("MM")).toInt()))
        this.plotfMonthlyData(startday.plusDays(4))

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
        val dPtksrb = dialogLayout.findViewById<RadioButton>(R.id.dPtksrb)
        if (!isMirai){
            tndlqrb.setText("수입")
            wlcnfrb.setText("지출")
            dPtksrb.setText("잔고")
        }
        else {
            tndlqrb.setText("예상 수입")
            wlcnfrb.setText("예상 지출")
            dPtksrb.setText("예상 잔고")
        }

        with(builder) {
            setTitle(String.format("%d년 %d월 %d일", year, month, day))
            setPositiveButton("추가") { dialog, which ->
                //Toast.makeText(context, "Accepted!", Toast.LENGTH_SHORT).show()
                try {
                    if(dPtksrb.isChecked){
                        addNewPrediction(
                            year,
                            month,
                            day,
                            true,
                            rmadordlqfur.text.toString().toInt(),
                            "SysBudSet"
                        )
                    }
                    else {
                        addNewPrediction(
                            year,
                            month,
                            day,
                            tndlqrb.isChecked,
                            rmadordlqfur.text.toString().toInt(),
                            predictionMemo.text.toString()
                        )
                    }
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getOldestDate(): LocalDate? {
        val db = myHelper.readableDatabase
        val COLUMN_DATE = "date"
        val TABLE_NAME = "groupTBL"
        val query = "SELECT MIN($COLUMN_DATE) AS oldest_date FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        var oldestDate: String? = null
        try {
            if (cursor.moveToFirst()) {
                oldestDate = cursor.getString(cursor.getColumnIndex("oldest_date"))
            }
            cursor.close()
            db.close()
            return LocalDate.parse(oldestDate)
        }catch(e:Exception){
            return today
        }
    }

}