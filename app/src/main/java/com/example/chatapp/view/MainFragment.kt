package com.example.chatapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.adapter.ChatPageAdapter
import com.example.chatapp.adapter.MainPageAdapter
import com.example.chatapp.databinding.FragmentMainBinding
import com.example.chatapp.model.ChatMessageModel
import com.example.chatapp.viewmodel.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class MainFragment : Fragment() {
    private var _binding:FragmentMainBinding ? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=Firebase.auth


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this)[MainViewModel::class.java]
        val currentUser = auth.currentUser?.uid.toString()
        viewModel.loadUsers(currentUser)
        viewModel.users.observe(viewLifecycleOwner, Observer { userList ->
            val adapter=MainPageAdapter(userList)
            binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.mainRecyclerView.adapter=adapter
        })

        binding.imageButton2.setOnClickListener{
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userRef = FirebaseFirestore.getInstance().collection("users").document(currentUser.uid.toString())
                userRef.update("status", "offline")
            }
            auth.signOut()
            val action=MainFragmentDirections.actionMainFragmentToLoginFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }




}