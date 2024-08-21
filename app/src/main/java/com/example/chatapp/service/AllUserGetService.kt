package com.example.chatapp.service

import com.example.chatapp.model.User

interface AllUserGetService {
    fun getUsers(chatId: String, onSuccess: (List<User>) -> Unit, onFailure: (Throwable) -> Unit)
}