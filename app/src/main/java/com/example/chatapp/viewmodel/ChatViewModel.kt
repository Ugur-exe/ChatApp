package com.example.chatapp.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.chatapp.model.ChatMessageModel
import com.example.chatapp.repository.ChatFirebaseRepository
import com.example.chatapp.repository.ChatRepository
import com.example.chatapp.view.ChatFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val chatRepository: ChatFirebaseRepository = ChatFirebaseRepository() // veya ChatApiRepository()

    private val _messages = MutableLiveData<List<ChatMessageModel>>()
    val messages: LiveData<List<ChatMessageModel>> get() = _messages

    fun listenForMessages(userId1: String, userId2: String) {
        val chatId = getChatId(userId1, userId2)
        chatRepository.getMessages(chatId, { messages ->
            _messages.value = messages
        }, { error ->
            error.printStackTrace()
        })
    }

    fun sendMessage(senderId: String, receiverId: String, messageText: String) {
        val chatId = getChatId(senderId, receiverId)
        val message = ChatMessageModel(
            messageId = 0,
            chatId = chatId,
            senderId = senderId,
            messageText = messageText,
            timestamp = System.currentTimeMillis().toString(),
            isSeen = false,
            receiverId = receiverId
        )

        chatRepository.sendMessage(chatId, message, {
            // Mesaj başarıyla gönderildi
        }, { error ->
            error.printStackTrace()
        })
    }

    fun getChatId(userId1: String, userId2: String): String {
        val ids = listOf(userId1, userId2).sorted()
        return "${ids[0]}_${ids[1]}"
    }

    fun backToMain(view: View) {
        val action = ChatFragmentDirections.actionChatFragmentToMainFragment()
        Navigation.findNavController(view).navigate(action)
    }
}