package com.example.chatapp.model

class ChatModel(
    val chatId: String = "", // Benzersiz chat ID'si
    val users: List<String> = emptyList(), // Sohbete katılan kullanıcı ID'leri
    val lastMessage: String = "", // Son mesajın metni
    val lastMessageTimestamp: Long = System.currentTimeMillis(), // Son mesajın gönderim zamanı
    val isGroupChat: Boolean = false // Grup sohbeti olup olmadığı bilgisi
)