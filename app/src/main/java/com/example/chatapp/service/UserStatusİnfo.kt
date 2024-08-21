package com.example.chatapp.service

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.type.DateTime
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class UserStatus(private val statusUpdate: (String) -> Unit) : LifecycleObserver {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormattedTime24Plus(): String {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return currentTime.format(formatter)
    }
    fun getFormattedTimeBelow24(): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentDateAndTime = Date()
        return simpleDateFormat.format(currentDateAndTime)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
        // Uygulama ön plana çıktığında kullanıcıyı online yap
        statusUpdate("online")
    }
    fun getFormattedTime(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // API seviyesi 24 (Nougat) ve üzeri
            getFormattedTime24Plus()
        } else {
            // API seviyesi 24'ün altı
            getFormattedTimeBelow24()
        }
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {

        // Uygulama arka plana geçtiğinde kullanıcıyı offline yap
        statusUpdate("Last seen at ${getFormattedTime()}")
    }
}