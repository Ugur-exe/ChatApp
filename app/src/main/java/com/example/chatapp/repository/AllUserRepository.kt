package com.example.chatapp.repository

import com.example.chatapp.model.User
import com.example.chatapp.service.AllUserGetService
import com.google.firebase.firestore.FirebaseFirestore

class AllUserRepository :AllUserGetService {
    private val firestore = FirebaseFirestore.getInstance()
    override fun getUsers(
        chatId: String,
        onSuccess: (List<User>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        firestore.collection("users")
            .whereNotEqualTo("userId", chatId)
            .addSnapshotListener() { result, exception ->
                if (exception != null) {
                    // Handle any errors
                    return@addSnapshotListener
                }
                val userList = result?.map { document ->
                    document.toObject(User::class.java)
                } ?: emptyList()
                onSuccess(userList)

            }

    }
}
