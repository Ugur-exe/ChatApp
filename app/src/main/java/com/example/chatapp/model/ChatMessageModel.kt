package com.example.chatapp.model

import java.util.UUID

data class ChatMessageModel(
    val messageId: Long = 0, // Default value
    val chatId: String = "", // Default value
    val senderId: String = "", // Default value
    val messageText: String = "", // Default value
    val timestamp: String = "", // No default value, should always be provided
    val isSeen: Boolean = false, // Default value
    val receiverId: String = "" // Default value
)