package com.example.chatapp.repository

import com.example.chatapp.model.ChatMessageModel
import com.example.chatapp.model.User
import com.example.chatapp.service.ChatService
import com.google.firebase.firestore.FirebaseFirestore

class ChatFirebaseRepository : ChatService {

    private val firestore = FirebaseFirestore.getInstance()

    override fun getMessages(chatId: String, onSuccess: (List<ChatMessageModel>) -> Unit, onFailure: (Throwable) -> Unit) {
        firestore.collection("chats")
            .document(chatId)
            .collection("messages")
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

    override fun sendMessage(chatId: String, message: ChatMessageModel, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        val messageRef = firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .document() // Auto-generated document ID

        messageRef.set(message)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }


    }

