package com.example.datecalculation

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDateFrom : TextView?  = null
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    @RequiresApi(Build.VERSION_CODES.O)
    val current = LocalDateTime.now().format(formatter)
    @RequiresApi(Build.VERSION_CODES.O)
    private var dateFrom : Date? = sdf.parse(current)
    @RequiresApi(Build.VERSION_CODES.O)
    private var dateTo : Date?  = sdf.parse(current)
    private var tvDiffSec:TextView? =  null
    private var tvDiffMin:TextView? =  null
    private var tvDiffHour:TextView? =  null
    private var tvDiffDays:TextView? =  null
    private var tvDiffWeeks:TextView? =  null
    private var tvDiffMonth:TextView? = null
    private var tvDiffYear:TextView? =  null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvSelectedDateFrom = findViewById(R.id.tvSelectedDates)
         tvDiffSec =  findViewById(R.id.secDiff)
          tvDiffMin =  findViewById(R.id.minDiff)
         tvDiffHour =  findViewById(R.id.hourDiff)
          tvDiffDays =  findViewById(R.id.dayDiff)
         tvDiffWeeks =  findViewById(R.id.weeksDiff)
          tvDiffMonth =  findViewById(R.id.mountsDiff)
          tvDiffYear =  findViewById(R.id.yearsDiff)

        val btnDateFrom : Button = findViewById(R.id.btnDatePickerFrom)
        val btnDateTo : Button = findViewById(R.id.btnDatePickerTo)


        btnDateFrom.setOnClickListener {

            clickDatePickFrom()

        }
        btnDateTo.setOnClickListener {
            clickDatePickTo()

        }
    }

    @SuppressLint("SetTextI18n")

    private fun changeDifference() {
        var diff = Math.abs((dateTo?.time ?: 0) - (dateFrom?.time ?:0 ))
        tvDiffSec?.text = "${diff/1000.0} sec"
        tvDiffMin?.text = "${diff/60000.0} mins"
        val hours = diff/3600000.0
        println(hours)
        tvDiffHour?.text = "$hours hours"
        val days = hours/24.0
        println(days)
        tvDiffDays?.text = "$days days"
        val weeks = days/7.0
        println(weeks)
        tvDiffWeeks?.text = "$weeks weeks"
        val month = weeks/4.34
        println(month)
        tvDiffMonth?.text = "$month months"
        val years = days/365.0
        println(years)
        tvDiffYear?.text = "$years years"
    }

    @SuppressLint("SetTextI18n")
    fun clickDatePickFrom() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)+1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ view, selectedYear, selectedMonth, selectedDayOfMonth ->


                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                dateFrom = sdf.parse(selectedDate)
                if(tvSelectedDateFrom != null){
                    if((tvSelectedDateFrom?.text?.length)== 0) {

                        dateTo = sdf.parse("${day}/${month}/${year}")
                        val tmpNOTICEME = dateTo.toString()

                        tvSelectedDateFrom?.text = "$selectedDate - ${day}/${month}/${year}"
                    }
                    else {
                        tvSelectedDateFrom!!.text =
                            selectedDate + tvSelectedDateFrom!!.text.toString()
                    }
                }
                changeDifference();
            },
            year,
            month,
            day

            ).show()
        println("$year ; $month ; $day")
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun clickDatePickTo() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ view, selectedYear, selectedMonth, selectedDayOfMonth ->

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                dateTo = sdf.parse(selectedDate)
                if(tvSelectedDateFrom != null){
                    if((tvSelectedDateFrom?.text?.length)== 0) {
                        tvSelectedDateFrom?.text = " - $selectedDate"
                    }
                    else {
                        tvSelectedDateFrom!!.text =
                            tvSelectedDateFrom!!.text.toString() +" - $selectedDate"

                    }

                }
                changeDifference()
                test()


            },
            year,
            month,
            day

        ).show()
        println("$year ; $month ; $day")

    }

    @SuppressLint("SetTextI18n")
    private fun test() {
        tvDiffYear?.text = "dsdsd years"
    }
}