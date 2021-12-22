package com.example.mobile_android.presentation

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.mobile_android.R
import com.example.mobile_android.presentation.start.StartScreenActivity
import com.example.mobile_android.presentation.start.StartScreenFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber
import java.util.*

private const val LANGUAGE_NAME = "language"
private const val USER_NAME = "name"
private const val PREFS_FILE_NAME = "user_data"

class MyProfileFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()
    private var sharedPreferences: SharedPreferences? = null
    private var toolbarMyProfileFragment: Toolbar? = null
    private var spinner: Spinner? = null
    private var emailInput: TextInputEditText? = null
    private var nameInput: TextInputEditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = activity?.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)

        initializeToolbar(view)
        initializeViews(view)
        setViewsValue(view)
    }

    private fun initializeToolbar(view: View) {
        toolbarMyProfileFragment = view.findViewById(R.id.fragment_my_profile__toolbar)
        toolbarMyProfileFragment?.setNavigationOnClickListener {

            sharedPreferences?.edit {
                putString(USER_NAME, nameInput?.text?.toString())
                putString(LANGUAGE_NAME, spinner?.selectedItem?.toString())
            }

            emailInput?.text?.toString()?.let {
                auth.currentUser?.updateEmail(it)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("---------updateEmail:success---------")
                    } else {
                        Timber.w("---------updateEmail:failure ${task.exception}}---------")
                    }
                }
            }

            if (sharedPreferences?.getString(LANGUAGE_NAME, "English") == "Ukrainian") {
                changeLanguage("uk")
            } else if (sharedPreferences?.getString(LANGUAGE_NAME, "English") == "Англійська") {
                changeLanguage("en")
            }

            (activity as StartScreenActivity).showFragment(StartScreenFragment.newInstance())
        }
    }

    private fun initializeViews(view: View) {
        emailInput = view.findViewById(R.id.myprofile__email)
        nameInput = view.findViewById(R.id.myprofile__name)
        spinner = view.findViewById(R.id.myprofile__dropdown)
    }

    private fun setViewsValue(view: View) {
        emailInput?.setText(auth.currentUser?.email)
        nameInput?.setText(sharedPreferences?.getString(USER_NAME, "Default"))

        val spinner: Spinner = view.findViewById(R.id.myprofile__dropdown)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            view.context,
            R.array.Languages,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter

            if (sharedPreferences?.getString(LANGUAGE_NAME, "Українська") == "English" ||
                sharedPreferences?.getString(LANGUAGE_NAME, "Українська") == "Англійська"
            ) {
                spinner.setSelection(0)
            } else {
                spinner.setSelection(1)
            }
//            val position: Int = adapter.getPosition(sharedPreferences?.getString(LANGUAGE_NAME, "Українська"))
//            spinner.setSelection(position)
        }
    }

    private fun changeLanguage(lang: String) {
        Locale.setDefault(Locale(lang))
        val config = Configuration()
        config.locale = Locale(lang)
        activity?.resources?.updateConfiguration(
            config,
            activity?.resources?.displayMetrics
        )
        activity?.recreate()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyProfileFragment()
    }
}
