package com.itis.mytask.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.itis.mytask.R
import com.itis.mytask.presenter.RegisterActivityPresenter
import com.itis.mytask.view.interfaces.RegisterActivityInterface
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : MvpAppCompatActivity(), RegisterActivityInterface {

    @InjectPresenter
    lateinit var presenter: RegisterActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register_reg.setOnClickListener {
            presenter.createUser(
                et_reg_mail.text.toString(),
                et_reg_password.text.toString(), this
            )
        }
    }




    companion object {
        fun createIntent(context: Context) = Intent(context, RegisterActivity::class.java)
    }
}
