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
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.itis.mytask.R
import com.itis.mytask.presenter.ResetPasswordActivityPresenter
import com.itis.mytask.view.interfaces.ResetPasswordActivityInterface
import kotlinx.android.synthetic.main.activity_reset_password.*


class ResetPasswordActivity : MvpAppCompatActivity(), ResetPasswordActivityInterface {

    @InjectPresenter
    lateinit var presenter: ResetPasswordActivityPresenter

    lateinit var mFirebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        btn_change_password.setOnClickListener {
            presenter.changePassword(et_change_pass_mail.text.toString(), this)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.METHOD, "Password change")
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
        }
    }


    companion object {
        fun createIntent(context: Context) = Intent(context, ResetPasswordActivity::class.java)
    }
}
