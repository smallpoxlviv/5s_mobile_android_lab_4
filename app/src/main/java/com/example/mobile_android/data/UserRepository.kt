package com.example.mobile_android.data

import com.example.mobile_android.data.entity.UserResponse
import com.example.mobile_android.domain.Repository
import io.reactivex.Single

class UserRepository(private val api: RandomUserAPI) : Repository {
    override fun getUsers(): Single<UserResponse> {
        return api.getUsers()
    }
}
