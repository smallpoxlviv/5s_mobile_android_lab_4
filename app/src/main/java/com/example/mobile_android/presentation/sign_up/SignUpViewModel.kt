package com.example.mobile_android.presentation.sign_up

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_android.presentation.validators.Validator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber

private const val SUCCESS = "SUCCESS"
private const val ERROR = "something went wrong"

class SignUpViewModel : ViewModel() {
    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String>
        get() = _emailError
    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String>
        get() = _passwordError
    private val _confirmPasswordError = MutableLiveData<String>()
    val confirmPasswordError: LiveData<String>
        get() = _confirmPasswordError
    private val _nameError = MutableLiveData<String>()
    val nameError: LiveData<String>
        get() = _nameError
    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser>
        get() = _user

    private val auth = FirebaseAuth.getInstance()

    private val validator = Validator()

    fun signUp(email: String, password: String, confirmPassword: String, name: String, context: Activity) {
        if (validator.validate(email, password, confirmPassword, name)) {
            registerUser(email, password, name, context)
        } else {
            _emailError.value = validator.emailError
            _passwordError.value = validator.passwordError
            _confirmPasswordError.value = validator.confirmPasswordError
            _nameError.value = validator.nameError
        }
    }

    private fun registerUser(email: String, password: String, name: String, context: Activity) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    Timber.d("success sign in with email")
                    _user.value = auth.currentUser
                    Toast.makeText(context, SUCCESS, Toast.LENGTH_SHORT).show()
                } else {
                    Timber.d(task.exception, "failure sign in with email")
                    _user.value = null
                    Toast.makeText(context, ERROR, Toast.LENGTH_SHORT).show()
                }
            }
    }
}
