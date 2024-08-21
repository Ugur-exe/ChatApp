package com.example.chatapp.service

import com.example.chatapp.model.ChatMessageModel
import com.example.chatapp.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>
    @GET("users/{id}")
    suspend fun getUserById(id: String): User
    @GET("users/{id}/messages")
    suspend fun getMessagesByUser(id: String): Call<List<ChatMessageModel>>


    @POST("users{id}/messages")
    suspend fun sendMessage(id: String, message: ChatMessageModel): Call<ChatMessageModel>




}