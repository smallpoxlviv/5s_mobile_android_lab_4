package com.example.mobile_android.presentation.user_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_android.R
import com.example.mobile_android.domain.User
import timber.log.Timber

class UserViewHolder(
    private val clickListener: (Int) -> Unit,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    private val firstName: TextView = itemView.findViewById(R.id.title)
    private val lastName: TextView = itemView.findViewById(R.id.tags)
    private val picture: ImageView = itemView.findViewById(R.id.picture)

    fun bind(user: User, userIndex: Int) {
        itemView.setOnClickListener {
            clickListener(userIndex)
        }
        firstName.text = user.firstName
        lastName.text = user.lastName
        Timber.d(user.picture)
        Glide.with(picture.context)
            .load(user.picture)
            .into(picture)
    }
}
