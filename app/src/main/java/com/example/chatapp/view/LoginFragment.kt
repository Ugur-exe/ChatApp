package com.example.chatapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentLoginBinding
import com.example.chatapp.viewmodel.LoginViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel:LoginViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this)[LoginViewModel::class.java]

        val currentUser=auth.currentUser
        if (currentUser!=null){
            val action=LoginFragmentDirections.actionLoginFragmentToMainFragment()
            Navigation.findNavController(view).navigate(action)
        }
        binding.textSingUp.setOnClickListener { signUp(it) }

        binding.loginButton.setOnClickListener{
            val email=binding.editTextTextEmailAddress.text.toString()
            val password=binding.editTextTextPassword.text.toString()
            signIn(email,password)
        }


    }
    private fun signUp(view: View){
        val action=LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        Navigation.findNavController(view).navigate(action)
    }
    private fun signIn(email: String, password: String) {
        viewModel.login(email,password)

        viewModel.loginStatus.observe(requireActivity(), Observer { isSuccess ->
            if (isSuccess) {
                val action=LoginFragmentDirections.actionLoginFragmentToMainFragment()
                Navigation.findNavController(requireView()).navigate(action)

            } else {
                Toast.makeText(requireActivity(), "Login failed!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}