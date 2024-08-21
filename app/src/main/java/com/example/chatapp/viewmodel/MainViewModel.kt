package com.example.chatapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatapp.model.User
import com.example.chatapp.repository.AllUserRepository
import com.example.chatapp.repository.ChatFirebaseRepository
import com.example.chatapp.service.AllUserGetService
import com.example.chatapp.service.ChatService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users
    private val chatService: AllUserGetService = AllUserRepository()
    fun loadUsers(chatId: String) {
        chatService.getUsers(chatId,
            onSuccess = { userList ->
                _users.value = userList
            },
            onFailure = { exception ->
                // Handle any errors
                _users.value = emptyList()
            }
        )
    }
}
