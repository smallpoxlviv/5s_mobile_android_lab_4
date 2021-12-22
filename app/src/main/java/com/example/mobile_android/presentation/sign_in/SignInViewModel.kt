package com.example.mobile_android.presentation.sign_in

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_android.presentation.validators.Validator
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

private const val SUCCESS = "SUCCESS"
private const val ERROR = "something went wrong"

class SignInViewModel : ViewModel() {
    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String>
        get() = _emailError
    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String>
        get() = _passwordError
    private val _userIsAuthenticated = MutableLiveData<Boolean>()
    val userIsAuthenticated: LiveData<Boolean>
        get() = _userIsAuthenticated

    private val auth = FirebaseAuth.getInstance()

    private val validator = Validator()

    fun signIn(email: String, password: String, context: Activity) {
        if (validator.validate(email, password)) {
            authenticateUser(email, password, context)
        } else {
            _emailError.value = validator.emailError
            _passwordError.value = validator.passwordError
        }
    }

    private fun authenticateUser(email: String, password: String, context: Activity) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    Timber.d("success sign in with email")
                    _userIsAuthenticated.value = true
                    Toast.makeText(context, SUCCESS, Toast.LENGTH_SHORT).show()
                } else {
                    Timber.d(task.exception?.message)
                    _userIsAuthenticated.value = false
                    Toast.makeText(context, ERROR, Toast.LENGTH_SHORT).show()
                }
            }
    }
}
