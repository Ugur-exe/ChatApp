package com.example.chatapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.model.ChatModel
import com.example.chatapp.model.User
import com.example.chatapp.repository.AllUserRepository
import com.example.chatapp.service.AllUserGetService
import com.google.firebase.auth.FirebaseAuth

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _chatModel = MutableLiveData<List<ChatModel>>()
    val chatModel: LiveData<List<ChatModel>> get() = _chatModel

    private val chatService: AllUserGetService = AllUserRepository()

    private val currentUser = FirebaseAuth.getInstance().currentUser

    fun loadUsers(chatId: String) {
        chatService.getUsers(chatId,
            onSuccess = { userList ->
                // Mevcut kullanıcı kimliğini al
                val currentUserId = currentUser?.uid

                // `userList` içindeki her `User` nesnesi için `ChatModel` listesini dönüştür
                val chatModelList = userList.flatMap { user ->
                    user.lastMessageModel?.filter { chatMap ->
                        // Sadece oturum açan kullanıcının chatId'siyle eşleşen mesajları al
                        (chatMap.receiverId == currentUserId || chatMap.senderId == currentUserId)
                    }?.map { chatMap ->
                        ChatModel(
                            chatId = chatMap.chatId,
                            receiverId = chatMap.receiverId,
                            senderId = chatMap.senderId,
                            lastMessage = chatMap.lastMessage,
                            lastMessageTimestamp = chatMap.lastMessageTimestamp,
                            isGroupChat = chatMap.isGroupChat
                        )
                    } ?: emptyList()
                }

                // Filtrelenmiş ve dönüştürülmüş `ChatModel` listesini `_chatModel` LiveData'sına ata
                _chatModel.value = chatModelList

                // Güncellenmiş `User` listesini `_users` LiveData'sına ata
                _users.value = userList
            },
            onFailure = { exception ->
                // Hataları yönet
                _users.value = emptyList()
                _chatModel.value = emptyList()
            }
        )
    }
}
