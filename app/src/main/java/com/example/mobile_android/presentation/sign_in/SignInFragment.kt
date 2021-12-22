package com.example.mobile_android.presentation.sign_in

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mobile_android.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignInFragment : Fragment() {

    private var signInButton: Button? = null
    private var signUpButton: Button? = null
    private var emailTextInputLayout: TextInputLayout? = null
    private var passwordTextInputLayout: TextInputLayout? = null
    private var emailTextView: TextInputEditText? = null
    private var passwordTextView: TextInputEditText? = null

    private var signInListener: SignInFragmentListener? = null
    private val viewModel by viewModels<SignInViewModel> { SignInViewModelFactory() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignInFragmentListener) {
            signInListener = context
        } else {
            throw IllegalStateException("Activity should implement SignInFragmentListener")
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
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailTextView = view.findViewById(R.id.activity_main__email)
        passwordTextView = view.findViewById(R.id.activity_main__password)

        emailTextInputLayout = view.findViewById(R.id.activity_main__email_layout)
        passwordTextInputLayout = view.findViewById(R.id.activity_main__password_layout)

        setupSignInButton(view)
        setupSignUpButton(view)
        initViewModel()
    }

    private fun setupSignInButton(view: View) {
        signInButton = view.findViewById(R.id.activity_main__button_sign_in)
        signInButton?.setOnClickListener {
            clearInputLayoutErrors()
            viewModel.signIn(
                emailTextView?.text.toString(), passwordTextView?.text.toString(),
                requireActivity()
            )
        }
    }

    private fun setupSignUpButton(view: View) {
        signUpButton = view.findViewById(R.id.activity_main__button_sign_up)
        signUpButton?.setOnClickListener {
            signInListener?.onSingUpClickedSignInFragment()
        }
    }

    private fun clearInputLayoutErrors() {
        emailTextInputLayout?.error = null
        passwordTextInputLayout?.error = null
    }

    private fun initViewModel() {
        viewModel.emailError.observe(viewLifecycleOwner) { error ->
            emailTextInputLayout?.error = error
        }
        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            passwordTextInputLayout?.error = error
        }
        viewModel.userIsAuthenticated.observe(viewLifecycleOwner) { value ->
            if (value) {
                signInListener?.onSignInClickedSignInFragment()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SignInFragment().apply {
            }
    }

    interface SignInFragmentListener {
        fun onSingUpClickedSignInFragment()
        fun onSignInClickedSignInFragment()
    }
}
