package com.example.chatapp.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatapp.service.UserStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        enableEdgeToEdge()
        setContentView(view)
        auth = FirebaseAuth.getInstance()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userStatusObserver = UserStatus { status ->
            statusUpdate(status)
        }

        // LifecycleOwner'a observer'ı ekleyin
        lifecycle.addObserver(userStatusObserver)

    }
    private fun statusUpdate(status: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userRef = FirebaseFirestore.getInstance().collection("users").document(currentUser.uid.toString())
            userRef.update("status", status)
        }
    }

    override fun onStop() {
        super.onStop()
        val userStatusObserver = UserStatus { status ->
            statusUpdate(status)
        }
        lifecycle.removeObserver(userStatusObserver)
    }
    override fun onDestroy() {
        super.onDestroy()
        val userStatusObserver = UserStatus { status ->
            statusUpdate(status)
        }
        lifecycle.removeObserver(userStatusObserver)
    }
}