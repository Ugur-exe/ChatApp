package com.example.chatapp.service

interface StatusUpdateListener {
    fun onStatusUpdate(status: String):String
}