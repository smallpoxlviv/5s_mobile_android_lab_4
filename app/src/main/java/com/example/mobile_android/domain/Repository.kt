package com.example.mobile_android.domain

import com.example.mobile_android.data.entity.UserResponse
import io.reactivex.Single

interface Repository {
    fun getUsers(): Single<UserResponse>
}
