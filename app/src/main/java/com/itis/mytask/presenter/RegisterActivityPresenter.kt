package com.itis.mytask.presenter

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseUser
import com.itis.mytask.di.component.DaggerAuthRepositoryComponent
import com.itis.mytask.repository.AuthRepository
import com.itis.mytask.view.MainActivity
import com.itis.mytask.view.interfaces.RegisterActivityInterface
import javax.inject.Inject

@InjectViewState
class RegisterActivityPresenter : MvpPresenter<RegisterActivityInterface>() {

    init {
        DaggerAuthRepositoryComponent.create().inject(this)
    }

    @Inject
    lateinit var repo: AuthRepository

    fun createUser(mail: String, password: String, activity: Activity) {
        repo.auth.createUserWithEmailAndPassword(
            mail, password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(ContentValues.TAG, "createUserWithEmail:success")
                updateUI(repo.auth.currentUser, activity)
            } else {
                Log.w(ContentValues.TAG, "createUserWithEmail:failure", it.exception);
                updateUI(null, activity)
            }
        }
    }

    private fun updateUI(account: FirebaseUser?, activity: Activity) {
        if (account == null) {
            Toast.makeText(activity, "Everything broke", Toast.LENGTH_SHORT).show()
        } else {
            activity.startActivity(MainActivity.createIntent(activity))
        }
    }
}