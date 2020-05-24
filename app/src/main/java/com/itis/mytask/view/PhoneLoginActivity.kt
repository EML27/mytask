package com.itis.mytask.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.itis.mytask.R
import com.itis.mytask.presenter.PhoneActivityPresenter
import com.itis.mytask.view.interfaces.PhoneLoginInterface
import kotlinx.android.synthetic.main.activity_phone_login.*
import java.util.concurrent.TimeUnit

class PhoneLoginActivity : MvpAppCompatActivity(), PhoneLoginInterface {

    @InjectPresenter
    lateinit var presenter: PhoneActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_login)

        btn_send.setOnClickListener {
            presenter.sendSMS(et_phone.text.toString(), this)
            btn_send_code.visibility = View.VISIBLE
            ti_code.visibility = View.VISIBLE
        }
    }

    override fun signIn(credential: PhoneAuthCredential) {
        presenter.signInWithPhoneAuthCredential(credential, this)
    }



    companion object {
        fun createIntent(context: Context) = Intent(context, PhoneLoginActivity::class.java)
    }
}
