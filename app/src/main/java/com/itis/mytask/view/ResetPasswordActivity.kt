package com.itis.mytask.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.arellomobile.mvp.MvpAppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.itis.mytask.R
import kotlinx.android.synthetic.main.activity_reset_password.*


class ResetPasswordActivity : MvpAppCompatActivity() {

    lateinit var mFirebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        btn_change_password.setOnClickListener {
            changePassword()
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.METHOD, "Password change")
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
        }
    }

    private fun changePassword() {
        FirebaseAuth.getInstance().sendPasswordResetEmail(et_change_pass_mail.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                } else {
                    Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, ResetPasswordActivity::class.java)
    }
}
