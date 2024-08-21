package com.example.chatapp.service

import com.example.chatapp.model.ChatMessageModel
import com.example.chatapp.model.User

interface ChatService {
    fun getMessages(chatId: String, onSuccess: (List<ChatMessageModel>) -> Unit, onFailure: (Throwable) -> Unit)
    fun sendMessage(chatId: String, message: ChatMessageModel, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit)

}