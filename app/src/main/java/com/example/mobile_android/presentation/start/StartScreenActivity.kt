package com.example.mobile_android.presentation.start

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobile_android.R
import com.example.mobile_android.presentation.SettingsActivity
import com.google.firebase.auth.FirebaseAuth

class StartScreenActivity : AppCompatActivity() {

    override fun onBackPressed() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            Toast.makeText(this, "You cannot return!", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.start_screen_container, StartScreenFragment.newInstance())
                .commit()
        }
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.start_screen_container, fragment)
            .commit()
    }

    fun showSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

}
