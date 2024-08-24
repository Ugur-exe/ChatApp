package com.example.chatapp.model

data class User(
    val userId: String = "",
    val nameSurname: String = "",
    val email: String = "",
    val profileImageUrl: String = "",
    val status: String = "offline", // Kullanıcının online durumu
    val lastMessageModel: List<ChatModel>?=null
)
