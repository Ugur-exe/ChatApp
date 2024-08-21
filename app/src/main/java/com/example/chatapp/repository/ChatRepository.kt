package com.example.chatapp.repository

import com.example.chatapp.model.ChatMessageModel
import com.google.firebase.firestore.FirebaseFirestore

class ChatRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getMessages(chatId: String, onSuccess: (List<ChatMessageModel>) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    onFailure(exception)
                    return@addSnapshotListener
                }

                val messages = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(ChatMessageModel::class.java)
                } ?: emptyList()

                onSuccess(messages)
            }
    }

    fun sendMessage(chatId: String, message: ChatMessageModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val messageRef = firestore.collection("chats").document(chatId).collection("messages").document()
        messageRef.set(message)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}
