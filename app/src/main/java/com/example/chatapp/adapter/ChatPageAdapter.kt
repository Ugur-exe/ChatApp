package com.example.chatapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.adapter.MainPageAdapter.MainPageViewHolder
import com.example.chatapp.databinding.ChatItemBinding
import com.example.chatapp.databinding.RecyclerRowBinding
import com.example.chatapp.model.ChatMessageModel

class ChatPageAdapter(private var list:List<ChatMessageModel>,private val currentUserId: String):RecyclerView.Adapter<ChatPageAdapter.ChatPageViewHolder>() {
    class ChatPageViewHolder(val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatPageViewHolder {
        val recyclerRowBinding=ChatItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChatPageViewHolder(recyclerRowBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChatPageViewHolder, position: Int) {
        val message = list[position]
        holder.binding.messageText.text=list[position].messageText
        if (message.senderId == currentUserId) {
            // Mesaj sağa hizalanır
            val params = holder.binding.messageText.layoutParams as ConstraintLayout.LayoutParams
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            params.startToStart = ConstraintLayout.LayoutParams.UNSET
            holder.binding.messageText.layoutParams = params
            holder.binding.messageText.setBackgroundResource(R.drawable.chat_bubble_right)
        } else {
            // Mesaj sola hizalanır
            val params = holder.binding.messageText.layoutParams as ConstraintLayout.LayoutParams
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            params.endToEnd = ConstraintLayout.LayoutParams.UNSET
            holder.binding.messageText.layoutParams = params
            holder.binding.messageText.setBackgroundResource(R.drawable.chat_bubble_left)
        }
    }
}