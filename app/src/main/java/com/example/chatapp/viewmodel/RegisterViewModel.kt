package com.example.chatapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application):AndroidViewModel(application) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore:FirebaseFirestore=FirebaseFirestore.getInstance()
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    fun register(email: String, password: String, fullName: String) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _loginStatus.value = true
                            saveUserToFirestore(email,fullName)
                        } else {
                            _loginStatus.value = false

                        }
                    }
            } catch (e: Exception) {
                _loginStatus.value = false
                Toast.makeText(getApplication() , "Login failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveUserToFirestore(email: String, fullName: String) {
        val userId = auth.currentUser?.uid
        val userMap = userId?.let {
            User(
                userId = it,
                email = email,
                nameSurname = fullName,
                profileImageUrl = "", // Varsayılan olarak boş bırakabilirsiniz ya da bir URL belirleyebilirsiniz
                status = "offline", // Varsayılan olarak 'offline' belirleyebilirsiniz
                lastMessageModel = emptyList() // Varsayılan olarak boş liste belirleyebilirsiniz
            )
        }

        userId?.let {
            if (userMap != null) {
                firestore.collection("users").document(it)
                    .set(userMap)
                    .addOnSuccessListener {
                        Toast.makeText(getApplication(), "User saved to Firestore successfully!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        // Kayıt başarısız, hata mesajı göster
                        Toast.makeText(getApplication(), "Failed to save user: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }


}