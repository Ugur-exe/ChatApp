package com.example.chatapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentLoginBinding
import com.example.chatapp.databinding.FragmentRegisterBinding
import com.example.chatapp.viewmodel.RegisterViewModel


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel:RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this@RegisterFragment)[RegisterViewModel::class.java]

        binding.registerButton.setOnClickListener{
            val email=binding.editTextRegisterEmailAddress.text.toString()
            val password=binding.editTextRegisterPassword.text.toString()
            val fullName=binding.editTextNameAndSurname.text.toString()
            register(email, password,fullName)
        }
    }
    private fun register(email: String, password: String,fullName:String){
        viewModel.register(email, password,fullName)
        viewModel.loginStatus.observe(requireActivity(), Observer {  isSuccess ->
            if(isSuccess){
                val action =RegisterFragmentDirections.actionRegisterFragmentToMainFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }else{
                Toast.makeText(requireContext(), "Register failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}