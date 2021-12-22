package com.example.mobile_android.data

import com.example.mobile_android.data.entity.UserResponse
import io.reactivex.Single
import retrofit2.http.GET
const val BASE_URL = "https://randomuser.me/api/"
interface RandomUserAPI {
    @GET("?results=10")
    fun getUsers(): Single<UserResponse>
}
