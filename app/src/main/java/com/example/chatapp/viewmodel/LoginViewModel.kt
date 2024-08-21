package com.example.chatapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel(aplication: Application):AndroidViewModel(aplication) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _loginStatus.value = true
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
}