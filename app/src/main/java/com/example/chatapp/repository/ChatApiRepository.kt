package com.example.chatapp.repository

import com.example.chatapp.model.ChatMessageModel
import com.example.chatapp.service.ApiService
import com.example.chatapp.service.ChatService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//class ChatApiRepository : ChatService {
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://yourapiurl.com/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    private val apiService = retrofit.create(ApiService::class.java)
//
//    override fun getMessages(chatId: String, onSuccess: (List<ChatMessageModel>) -> Unit, onFailure: (Throwable) -> Unit) {
//        apiService.getMessagesByUser(chatId).enqueue(object : Callback<List<ChatMessageModel>> {
//            override fun onResponse(call: Call<List<ChatMessageModel>>, response: Response<List<ChatMessageModel>>) {
//                if (response.isSuccessful) {
//                    onSuccess(response.body() ?: emptyList())
//                } else {
//                    onFailure(Exception("Failed to get messages"))
//                }
//            }
//
//            override fun onFailure(call: Call<List<ChatMessageModel>>, t: Throwable) {
//                onFailure(t)
//            }
//        })
//    }
//
//    override suspend fun sendMessage(chatId: String, message: ChatMessageModel, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
//        apiService.sendMessage(chatId, message).enqueue(object : Callback<Void> {
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                if (response.isSuccessful) {
//                    onSuccess()
//                } else {
//                    onFailure(Exception("Failed to send message"))
//                }
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//                onFailure(t)
//            }
//        })
//    }
//}
