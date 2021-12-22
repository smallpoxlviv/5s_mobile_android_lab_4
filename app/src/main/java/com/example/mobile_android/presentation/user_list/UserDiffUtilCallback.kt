package com.example.mobile_android.presentation.user_list

import androidx.recyclerview.widget.DiffUtil
import com.example.mobile_android.domain.User

class UserDiffUtilCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
}
