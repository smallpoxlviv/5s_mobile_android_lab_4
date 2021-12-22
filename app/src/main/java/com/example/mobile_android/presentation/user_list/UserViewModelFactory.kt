package com.example.mobile_android.presentation.user_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_android.data.RetrofitBuilder
import com.example.mobile_android.data.UserRepository

class UserViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(UserRepository(RetrofitBuilder.api)) as T
    }
}
