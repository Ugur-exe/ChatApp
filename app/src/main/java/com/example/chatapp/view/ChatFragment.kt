package com.example.chatapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.adapter.ChatPageAdapter
import com.example.chatapp.databinding.FragmentChatBinding
import com.example.chatapp.repository.UserStatusGet
import com.example.chatapp.viewmodel.ChatViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private val statusInfo = UserStatusGet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        lifecycle.addObserver(statusInfo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = ChatFragmentArgs.fromBundle(requireArguments())
        val receiverId = args.id
        val senderId = auth.currentUser?.uid.toString()
        val nameSurname = args.nameSurname
        statusInfo.currentUserStatus(receiverId)
        statusInfo.status.observe(viewLifecycleOwner) { status ->
            println("ChatFragment - status: $status")
            binding.chatPersonExplanation.text = status ?: "Status not available"
        }

        binding.chatPersonName.text = nameSurname


        binding.chatRecyclerView.layoutManager = LinearLayoutManager(context)
        var adapter = ChatPageAdapter(arrayListOf(), senderId)
        binding.chatRecyclerView.adapter = adapter

        chatViewModel.listenForMessages(senderId, receiverId)
        binding.imageIconBack.setOnClickListener {
            chatViewModel.backToMain(requireView())
        }
        binding.sendMessageButton.setOnClickListener {
            val messageText = binding.chatMessageText.text.toString()
            chatViewModel.sendMessage(senderId, receiverId, messageText)
            binding.chatMessageText.text.clear()
            binding.chatRecyclerView.scrollToPosition(adapter.itemCount - 1)
        }
        chatViewModel.messages.observe(viewLifecycleOwner) { messageList ->
            adapter = ChatPageAdapter(messageList, senderId)
            binding.chatRecyclerView.adapter = adapter
            binding.chatRecyclerView.scrollToPosition(adapter.itemCount - 1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

