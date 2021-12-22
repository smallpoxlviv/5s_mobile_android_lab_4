package com.example.mobile_android.presentation.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mobile_android.R
import com.example.mobile_android.presentation.MyProfileFragment
import com.example.mobile_android.presentation.UserFragment
import com.example.mobile_android.presentation.user_list.UserAdapter
import com.example.mobile_android.presentation.user_list.UserViewModel
import com.example.mobile_android.presentation.user_list.UserViewModelFactory
import timber.log.Timber

class StartScreenFragment : Fragment() {
    private val viewModel by viewModels<UserViewModel> { UserViewModelFactory() }
    private val adapter = UserAdapter(::onUserClick)

    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var progressBar: ProgressBar? = null
    private var myProfileButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeButton(view)
        initUI(view)
        initViewModel()
        progressBar = view.findViewById(R.id.progress_bar_start_screen_fragment)
        viewModel.getUsers()
    }

    private fun initializeButton(view: View) {
        myProfileButton = view.findViewById(R.id.my_profile_button)
        myProfileButton?.setOnClickListener {
//            (activity as StartScreenActivity).showFragment(MyProfileFragment.newInstance())
            (activity as StartScreenActivity).showFragment(MyProfileFragment.newInstance())
        }
    }

    private fun initUI(view: View) {
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh_layout)
        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.getUsers()
        }

        val photoRecyclerView: RecyclerView = view.findViewById(R.id.photo_list)
        photoRecyclerView.adapter = adapter
        photoRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    private fun initViewModel() {
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Timber.d(error)
        }
        viewModel.userList.observe(viewLifecycleOwner) {
            Timber.d(" initViewModel(): viewModel.userList.observe")
            adapter.submitList(it)
            swipeRefreshLayout?.isRefreshing = false
            progressBar?.visibility = View.GONE
        }
    }

    private fun onUserClick(userIndex: Int) {
        Timber.d(" ------------- onUserClick(): user was clicked with index $userIndex -------------------- ")
        (activity as StartScreenActivity).showFragment(UserFragment.newInstance(userIndex))
    }

    companion object {
        @JvmStatic
        fun newInstance() = StartScreenFragment()
    }
}
