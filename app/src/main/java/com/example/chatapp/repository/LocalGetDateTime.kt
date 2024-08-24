package com.example.chatapp.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.chatapp.service.LocalDatetimeService
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class LocalGetDateTime :LocalDatetimeService {
    override fun getDateTime():String {
        return getFormattedTime()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFormattedTime24Plus(): String {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return currentTime.format(formatter)
    }
    private fun getFormattedTimeBelow24(): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentDateAndTime = Date()
        return simpleDateFormat.format(currentDateAndTime)
    }
    private fun getFormattedTime(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // API seviyesi 24 (Nougat) ve üzeri
            getFormattedTime24Plus()
        } else {
            // API seviyesi 24'ün altı
            getFormattedTimeBelow24()
        }
    }

}