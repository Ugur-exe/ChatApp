package com.example.chatapp.repository

import android.util.Log
import com.example.chatapp.model.ChatMessageModel
import com.example.chatapp.model.ChatModel
import com.example.chatapp.model.User
import com.example.chatapp.service.ChatService
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ChatFirebaseRepository : ChatService {

    private val firestore = FirebaseFirestore.getInstance()

    override fun getMessages(
        chatId: String,
        onSuccess: (List<ChatMessageModel>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
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

    override fun sendMessage(
        chatId: String,
        chatModel: ChatModel,
        message: ChatMessageModel,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val messageRef = firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .document()

        val chatRef = firestore.collection("chats")
            .document(chatId)

        val user1Ref = firestore.collection("users").document(chatModel.senderId)
        val user2Ref = firestore.collection("users").document(chatModel.receiverId)

        // Kullanıcıların lastMessageModel listesini güncellemek için önce mevcut listeyi al
        firestore.runBatch { batch ->
            // Yeni mesajı ekle
            batch.set(messageRef, message)

            // Sohbetin son mesaj bilgilerini güncelle
            batch.set(chatRef, chatModel)
        }.addOnSuccessListener {
            // User1 ve User2'nin lastMessageModel listesini güncelle
            updateLastMessageModel(user1Ref, chatModel)
            updateLastMessageModel(user2Ref, chatModel)

            onSuccess() // Başarılı oldu
        }.addOnFailureListener { e ->
            onFailure(e) // Hata oldu
        }
    }

    private fun updateLastMessageModel(userRef: DocumentReference, newChatModel: ChatModel) {
        userRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val user = documentSnapshot.toObject(User::class.java)
                val currentList = user?.lastMessageModel?.toMutableList() ?: mutableListOf()

                // Aynı chatId'ye sahip bir eleman var mı kontrol et
                val existingIndex = currentList.indexOfFirst { it.chatId == newChatModel.chatId }
                if (existingIndex != -1) {
                    // Mevcut elemanı güncelle
                    currentList[existingIndex] = newChatModel
                } else {
                    // Yeni elemanı listeye ekle
                    currentList.add(newChatModel)
                }

                // Güncellenmiş listeyi Firestore'a kaydet
                userRef.update("lastMessageModel", currentList)
                    .addOnSuccessListener {
                        Log.d("Firestore Update", "lastMessageModel successfully updated.")
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore Error", "Failed to update lastMessageModel", e)
                    }
            } else {
                // Kullanıcı dokümanı mevcut değilse boş bir listeyle başlat
                val newList = mutableListOf(newChatModel)
                userRef.update("lastMessageModel", newList)
                    .addOnSuccessListener {
                        Log.d("Firestore Update", "lastMessageModel successfully initialized.")
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore Error", "Failed to initialize lastMessageModel", e)
                    }
            }
        }.addOnFailureListener { e ->
            Log.e("Firestore Error", "Failed to retrieve user document", e)
        }
    }
}
