package com.example.mobile_android.presentation.sign_up

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mobile_android.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

const val NAME = "NAME"
const val EMAIL = "EMAIL"
const val PASSWORD = "PASSWORD"
const val CONFIRM_PASSWORD = "CONFIRM_PASSWORD"

class SignUpFragment : Fragment() {

    private var signUpButton: Button? = null
    private var nameTextInputLayout: TextInputLayout? = null
    private var emailTextInputLayout: TextInputLayout? = null
    private var passwordTextInputLayout: TextInputLayout? = null
    private var confirmPasswordTextInputLayout: TextInputLayout? = null
    private var nameTextView: TextInputEditText? = null
    private var emailTextView: TextInputEditText? = null
    private var passwordTextView: TextInputEditText? = null
    private var confirmPasswordTextView: TextInputEditText? = null
    private var toolbar: Toolbar? = null

    private var signUpListener: SignUpFragmentListener? = null
    private val viewModel by viewModels<SignUpViewModel> { SignUpViewModelFactory() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignUpFragmentListener) {
            signUpListener = context
        } else {
            throw IllegalStateException("Activity should implement SignUpFragmentListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        toolbar = view.findViewById(R.id.activity_sign_up__toolbar)
        toolbar?.setNavigationOnClickListener {
            signUpListener?.onNavigationClickedSignUpFragment()
        }

        setupSignUpButton(view)
        initViewModel()
    }

    private fun setupSignUpButton(view: View) {
        signUpButton = view.findViewById(R.id.activity_sign_up__button_sign_up)
        signUpButton?.setOnClickListener {
            clearInputLayoutErrors()
            viewModel.signUp(
                emailTextView?.text.toString(), passwordTextView?.text.toString(),
                confirmPasswordTextView?.text.toString(), nameTextView?.text.toString(), requireActivity()
            )
        }
    }

    private fun clearInputLayoutErrors() {
        nameTextInputLayout?.error = null
        emailTextInputLayout?.error = null
        passwordTextInputLayout?.error = null
        confirmPasswordTextInputLayout?.error = null
    }

    private fun initViews(view: View) {
        nameTextView = view.findViewById(R.id.activity_sign_up__name)
        emailTextView = view.findViewById(R.id.activity_sign_up__email)
        passwordTextView = view.findViewById(R.id.activity_sign_up__password)
        confirmPasswordTextView = view.findViewById(R.id.activity_sign_up__confirm_password)

        nameTextInputLayout = view.findViewById(R.id.activity_sign_up__name_layout)
        emailTextInputLayout = view.findViewById(R.id.activity_sign_up__email_layout)
        passwordTextInputLayout = view.findViewById(R.id.activity_sign_up__password_layout)
        confirmPasswordTextInputLayout = view.findViewById(R.id.activity_sign_up__confirm_password_layout)
    }

    private fun initViewModel() {
        viewModel.nameError.observe(viewLifecycleOwner) { error ->
            nameTextInputLayout?.error = error
        }
        viewModel.emailError.observe(viewLifecycleOwner) { error ->
            emailTextInputLayout?.error = error
        }
        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            passwordTextInputLayout?.error = error
        }
        viewModel.confirmPasswordError.observe(viewLifecycleOwner) { error ->
            confirmPasswordTextInputLayout?.error = error
        }
        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                signUpListener?.onSingUpClickedSignUpFragment()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SignUpFragment().apply {
                arguments = Bundle()
            }
    }

    interface SignUpFragmentListener {
        fun onSingUpClickedSignUpFragment()
        fun onNavigationClickedSignUpFragment()
    }
}
