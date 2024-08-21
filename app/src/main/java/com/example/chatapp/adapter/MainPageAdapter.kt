package com.example.chatapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ChatItemBinding
import com.example.chatapp.databinding.RecyclerRowBinding
import com.example.chatapp.model.ChatMessageModel
import com.example.chatapp.model.User
import com.example.chatapp.view.MainFragmentDirections
import com.google.type.DateTime

class MainPageAdapter(val chatlist:List<User>):RecyclerView.Adapter<MainPageAdapter.MainPageViewHolder>() {
    class MainPageViewHolder( val binding:RecyclerRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageViewHolder {
        val recyclerRowBinding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainPageViewHolder(recyclerRowBinding)
    }

    override fun getItemCount(): Int {
       return chatlist.size
    }

    override fun onBindViewHolder(holder: MainPageViewHolder, position: Int) {
        holder.binding.cardTitle.text=chatlist[position].nameSurname
        holder.binding.cardSubTitle.text=chatlist[position].status
        holder.itemView.setOnClickListener {
            println(chatlist[position].userId)
            val action=MainFragmentDirections.actionMainFragmentToChatFragment(chatlist[position].userId
                ,chatlist[position].nameSurname,chatlist[position].status)
            Navigation.findNavController(it).navigate(action)
        }



    }
}