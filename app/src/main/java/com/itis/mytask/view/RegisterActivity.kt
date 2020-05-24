package com.itis.mytask.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.arellomobile.mvp.MvpAppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.itis.mytask.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        btn_register_reg.setOnClickListener {
            auth.createUserWithEmailAndPassword(
                et_reg_mail.text.toString(),
                et_reg_password.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    updateUI(auth.currentUser)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", it.exception);
                    updateUI(null)
                }
            }
        }
    }

    private fun updateUI(account: FirebaseUser?) {
        if (account == null) {
            Toast.makeText(this, "Everything broke", Toast.LENGTH_SHORT).show()
        } else {
            startActivity(MainActivity.createIntent(this))
        }
    }

    private lateinit var auth: FirebaseAuth

    companion object {
        fun createIntent(context: Context) = Intent(context, RegisterActivity::class.java)
    }
}
