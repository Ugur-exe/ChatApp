package com.example.chatapp.model

class ChatModel(
    val chatId: String = "", // Benzersiz chat ID'si
     val receiverId: String = "",
    val senderId: String = "", // Sohbete katılan kullanıcı ID'leri
    val lastMessage: String = "", // Son mesajın metni
    val lastMessageTimestamp: String="", // Son mesajın gönderim zamanı
    val isGroupChat: Boolean = false // Grup sohbeti olup olmadığı bilgisi

)