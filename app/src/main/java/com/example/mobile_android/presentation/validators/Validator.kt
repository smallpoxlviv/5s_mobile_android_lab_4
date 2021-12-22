package com.example.mobile_android.presentation.validators

import android.util.Patterns

const val PASSWORD_PATTERN = "[A-Za-z]{8,}\$"
private const val PASSWORD_LENGTH = 8

class Validator {

    var emailError: String? = null
    var passwordError: String? = null
    var confirmPasswordError: String? = null
    var nameError: String? = null

    fun validate(email: String, password: String): Boolean {
        if (isEmailValid(email) && isPasswordValid(password)) {
            return true
        }
        return false
    }

    fun validate(email: String, password: String, confirmPassword: String, name: String): Boolean {
        if (isNameValid(name) && isEmailValid(email) && isPasswordValid(password) &&
            isConfirmPasswordValid(password, confirmPassword)
        ) {
            return true
        }
        return false
    }

    private fun isEmailValid(email: String): Boolean {
        if (email == "null" || email.isBlank()) {
            emailError = "Email can't be blank"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Email is invalid"
            return false
        }
        emailError = null
        return true
    }

    private fun isPasswordValid(password: String): Boolean {
        if (password == "null" || password.isBlank()) {
            passwordError = "Password can't be blank"
            return false
        }
        val passwordMatcher = Regex(PASSWORD_PATTERN)
        if (passwordMatcher.find(password) == null) {
            passwordError = "Password must be at least $PASSWORD_LENGTH characters (only letters) long"
            return false
        }
        passwordError = null
        return true
    }

    private fun isConfirmPasswordValid(password: String, confirmPassword: String): Boolean {
        if (password != confirmPassword) {
            confirmPasswordError = "Passwords are different"
            return false
        }
        confirmPasswordError = null
        return true
    }

    private fun isNameValid(name: String): Boolean {
        if (name == "null" || name.isBlank()) {
            nameError = "Name can't be blank"
            return false
        }
        nameError = null
        return true
    }
}
