package com.example.mobile_android.presentation.user_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_android.domain.Repository
import com.example.mobile_android.domain.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UserViewModel(private val repository: Repository) : ViewModel() {
    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private var compositeDisposable = CompositeDisposable()

    fun getUsers() {
        compositeDisposable.add(
            repository.getUsers()
                .subscribeOn(Schedulers.io())
                .map { response ->
                    val userList = mutableListOf<User>()
                    for (user in response.results) {
                        userList.add(User(user.name.first, user.name.last, user.picture.large))
                    }
                    return@map userList
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    Timber.d("UserViewModel: getUsers(): _userList.value = result")
                    _userList.value = result
                }, { error ->
                    Timber.d("UserViewModel: getUsers(): _errorMessage.value = error.message")
                    _errorMessage.value = error.message
                })
        )
    }

    fun getUser(userIndex: Int): User? {
        return _userList.value?.get(userIndex)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared")
        compositeDisposable.dispose()
    }
}
