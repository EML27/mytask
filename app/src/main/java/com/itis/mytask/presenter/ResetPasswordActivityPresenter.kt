package com.itis.mytask.presenter

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.itis.mytask.di.component.DaggerAuthRepositoryComponent
import com.itis.mytask.repository.AuthRepository
import com.itis.mytask.view.interfaces.ResetPasswordActivityInterface
import kotlinx.android.synthetic.main.activity_reset_password.*
import javax.inject.Inject

@InjectViewState
class ResetPasswordActivityPresenter: MvpPresenter<ResetPasswordActivityInterface>() {
    init {
        DaggerAuthRepositoryComponent.create().inject(this)
    }

    @Inject
    lateinit var repo: AuthRepository

    fun changePassword(mail: String, activity: Activity) {
        repo.auth.sendPasswordResetEmail(mail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "Email sent.")
                } else {
                    Toast.makeText(activity, "Try again", Toast.LENGTH_SHORT).show()
                }
            }
    }
}