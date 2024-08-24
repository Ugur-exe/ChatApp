package com.example.chatapp.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class UserStatus(private val statusUpdate: (String) -> Unit) : LifecycleObserver {
   private var getFormattedTime:LocalGetDateTime=LocalGetDateTime()


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
        // Uygulama ön plana çıktığında kullanıcıyı online yap
        statusUpdate("online")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        // Uygulama arka plana geçtiğinde kullanıcıyı offline yap
        statusUpdate("Last seen at ${getFormattedTime.getDateTime()}")
    }
}