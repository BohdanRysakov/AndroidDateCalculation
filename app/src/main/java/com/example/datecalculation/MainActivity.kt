package com.example.datecalculation

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    // Объявляем TextView для отображения выбранных дат и TextViews для отображения разницы во времени.
    private lateinit var tvSelectedDateFrom: TextView
    private val dateValuesTextViews: Array<TextView> by lazy {
        arrayOf(findViewById(R.id.secDiff), findViewById(R.id.minDiff), findViewById(R.id.hourDiff),
            findViewById(R.id.dayDiff), findViewById(R.id.weeksDiff), findViewById(R.id.mountsDiff),
            findViewById(R.id.yearsDiff))
    }
    // SimpleDateFormat для преобразования даты в строку и обратно.
    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    // Инициализируем даты как текущую дату.
    private var dateFrom = Date()
    private var dateTo = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Находим TextViews и задаем обработчики событий клика для кнопок.
        tvSelectedDateFrom = findViewById(R.id.tvSelectedDates)
        findViewById<Button>(R.id.btnDatePickerFrom).setOnClickListener { clickDatePicker(::setDateFrom) }
        findViewById<Button>(R.id.btnDatePickerTo).setOnClickListener { clickDatePicker(::setDateTo) }
    }

    // Функция для отображения DatePickerDialog и установки выбранной даты.
    private fun clickDatePicker(dateSetter: (Date) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // Парсим выбранную дату.
                val selectedDate = sdf.parse("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
                selectedDate?.let {
                    // Устанавливаем выбранную дату.
                    dateSetter(it)
                    // Обновляем текст с выбранными датами.
                    updateDatesText()
                    // Вычисляем разницу между датами.
                    changeDifference()
                } ?: Toast.makeText(this, "Error parsing date", Toast.LENGTH_SHORT).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Устанавливаем dateFrom.
    private fun setDateFrom(date: Date) {
        dateFrom = date
    }

    // Устанавливаем dateTo.
    private fun setDateTo(date: Date) {
        dateTo = date
    }

    // Обновляем текст в TextView с выбранными датами.
    private fun updateDatesText() {
        tvSelectedDateFrom.text = "${sdf.format(dateFrom)} - ${sdf.format(dateTo)}"
    }

    // Вычисляем и отображаем разницу между датами в различных единицах времени.
    private fun changeDifference() {
        val diff = Math.abs(dateTo.time - dateFrom.time)
        val timeUnits = arrayOf(1000, 60000, 3600000, 86400000, 604800000, 2629800000, 31557600000)
        timeUnits.forEachIndexed { index, unit ->
            dateValuesTextViews[index].text = "${diff / unit.toDouble()}"
        }
    }
}






