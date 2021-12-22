package com.example.mobile_android.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.mobile_android.R
import com.example.mobile_android.domain.User
import com.example.mobile_android.presentation.start.StartScreenActivity
import com.example.mobile_android.presentation.start.StartScreenFragment
import com.example.mobile_android.presentation.user_list.UserViewModel
import com.example.mobile_android.presentation.user_list.UserViewModelFactory
import timber.log.Timber

private const val ARG_PARAM_USER_INDEX = "user_index"

class UserFragment : Fragment() {
    private val viewModel by viewModels<UserViewModel> { UserViewModelFactory() }
    private var toolbarUserFragment: Toolbar? = null
    private var imageView: ImageView? = null
    private var firstnameView: TextView? = null
    private var lastnameView: TextView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    private var userIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userIndex = it.getInt(ARG_PARAM_USER_INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI(view)
        initializeToolbar(view)
        initializeView(view)
        initViewModel()
    }

    private fun initializeToolbar(view: View) {
        toolbarUserFragment = view.findViewById(R.id.fragment_user__toolbar)
        toolbarUserFragment?.setNavigationOnClickListener {
            (activity as StartScreenActivity).showFragment(StartScreenFragment.newInstance())
        }
    }

    private fun initUI(view: View) {
        swipeRefreshLayout = view.findViewById(R.id.fragment_user_swiperefresh_layout)
        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.getUsers()
        }
    }

    private fun initializeView(view: View) {
        imageView = view.findViewById(R.id.fragment_user__image)
        firstnameView = view.findViewById(R.id.fragment_user__firstname)
        lastnameView = view.findViewById(R.id.fragment_user__lastname)
    }

    private fun initViewModel() {
        viewModel.getUsers()
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Timber.d(error)
        }
        viewModel.userList.observe(viewLifecycleOwner) {
            swipeRefreshLayout?.isRefreshing = false
            setViewValue()
        }
    }

    private fun setViewValue() {
        val user: User? = userIndex?.let {
            viewModel.getUser(it)
        }
        toolbarUserFragment?.title = user?.firstName
        firstnameView?.text = user?.firstName
        lastnameView?.text = user?.lastName
        Timber.d("-------------------------name: ${user?.firstName}, lastname: ${user?.lastName}-------------------------")
        imageView?.let {
            Glide.with(it.context)
                .load(user?.picture)
                .into(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(userIndex: Int) = UserFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM_USER_INDEX, userIndex)
            }
        }
    }
}
