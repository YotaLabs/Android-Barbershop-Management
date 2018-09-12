package com.sashakhyzhun.androidbarbershopmanagementprototype.ui.monthly

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.marcohc.robotocalendar.RobotoCalendarView
import com.sashakhyzhun.androidbarbershopmanagementprototype.R
import java.util.*
import android.widget.Toast
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import kotlin.collections.ArrayList

class MonthlyActivity : AppCompatActivity(), RobotoCalendarView.RobotoCalendarListener {

    companion object {
        const val oneDay: Long = 24 * 60 * 60 * 1000
    }

    private lateinit var calendarView: RobotoCalendarView
    private var monthIndex: Int = 0
    private val calendar = Calendar.getInstance()
    private val now = System.currentTimeMillis()
    private val today = Date(now)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly)

//        currentMonth = getCurrentMonth()
//        previousMonth = currentMonth?.minus(1) ?: getCurrentMonth(-1)
//        nextMonth = currentMonth?.plus(1) ?: getCurrentMonth(+1)

        calendarView = findViewById(R.id.calendarView)
        calendarView.setDate(today)
        calendarView.setRobotoCalendarListener(this)

        monthIndex = getCurrentMonth()
        val currentMonthData = loadMonthlyData()
        fillCalendar(currentMonthData)
    }


    private fun loadMonthlyData(): ArrayList<Long> {
        val year = getCurrentYear()
        //val month = getCurrentMonth(monthOffset)
        val daysInMonth = getDaysInMonth(year, monthIndex)
        val firstDayMillis = getFirstDayOfMonthInMillis(year, monthIndex)

        var additionalDay: Long = 0
        val timeStamps: ArrayList<Long> = arrayListOf()
        for (i in 0 until daysInMonth) {
            val timestamp = firstDayMillis + additionalDay
            additionalDay += oneDay
            timeStamps.add(timestamp)
        }

        return timeStamps
    }


    private fun fillCalendar(monthlyData: ArrayList<Long>) {
        for (i in 0 until monthlyData.size) {
            if (i == 9 || i == 14 || i == 19) { continue }
            calendarView.markCircleImage1(Date(monthlyData[i]))
        }
    }

    private fun getCurrentYear(): Int = calendar.get(Calendar.YEAR)

    /**
     * Hows it work:
     * we get current month.
     * if we need next one we need to put positive number
     * if we need previous one we need to put negative number
     */
    private fun getCurrentMonth(offset: Int = 0): Int = calendar.get(Calendar.MONTH) + 1 + offset

    private fun getDaysInMonth(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 12)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    private fun getFirstDayOfMonthInMillis(year: Int, month: Int): Long {
        val cal = GregorianCalendar(year, month - 1, 1)
        cal.timeZone = TimeZone.getDefault()
        return cal.time.time
    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    override fun onDayClick(date: Date) {
        Toast.makeText(this, "onDayClick: $date", Toast.LENGTH_SHORT).show()
    }

    override fun onDayLongClick(date: Date) {
        Toast.makeText(this, "onDayLongClick: $date", Toast.LENGTH_SHORT).show()
    }

    override fun onLeftButtonClick() {
        Toast.makeText(this, "onLeftButtonClick!", Toast.LENGTH_SHORT).show()
        monthIndex -= 1
        val previousMonthData = loadMonthlyData()
        fillCalendar(previousMonthData)

    }

    override fun onRightButtonClick() {
        Toast.makeText(this, "onRightButtonClick!", Toast.LENGTH_SHORT).show()
        monthIndex += 1
        val nextMonthData = loadMonthlyData()
        fillCalendar(nextMonthData)
    }




}