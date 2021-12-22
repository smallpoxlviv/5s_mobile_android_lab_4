package com.example.mobile_android.presentation.user_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mobile_android.R
import com.example.mobile_android.domain.User

class UserAdapter(
    private val clickListener: (Int) -> Unit
) : ListAdapter<User, UserViewHolder>(UserDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(clickListener, itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }
}
