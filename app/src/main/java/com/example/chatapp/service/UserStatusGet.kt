package com.example.chatapp.service

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserStatusGet : LifecycleObserver {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val _status = MutableLiveData<String?>()
    val status: LiveData<String?> get() = _status

    fun currentUserStatus(recevierId: String) {
        val currentUser = auth.currentUser
        currentUser?.let {
            firestore.collection("users").document(recevierId).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    // Handle error
                    e.printStackTrace()
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val status = snapshot.getString("status")
                    _status.value = status // Update LiveData
                } else {
                    _status.value = null // Handle case where document does not exist
                }
            }
        }
    }
}

