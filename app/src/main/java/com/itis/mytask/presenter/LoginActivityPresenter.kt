package com.itis.mytask.presenter

import android.app.Activity
import android.content.ContentValues
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.itis.mytask.R
import com.itis.mytask.di.component.DaggerAuthRepositoryComponent
import com.itis.mytask.repository.AuthRepository
import com.itis.mytask.view.MainActivity
import com.itis.mytask.view.interfaces.LoginActivityInterface
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

@InjectViewState
class LoginActivityPresenter : MvpPresenter<LoginActivityInterface>() {

    init {
        DaggerAuthRepositoryComponent.create().inject(this)
    }

    @Inject
    lateinit var repo: AuthRepository

    private fun firebaseAuthWithGoogle(idToken: String?, activity: Activity) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        repo.auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    val user = repo.auth.currentUser
                    updateUI(user, activity)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    // ...
                    Toast.makeText(activity, "Fail!!!", Toast.LENGTH_SHORT).show()

                    updateUI(null, activity)
                }

                // ...
            }
    }

    fun signInThroughEmail(email: String, password: String, activity: Activity) {
        repo.auth.signInWithEmailAndPassword(
            email, password
        ).addOnCompleteListener(activity, OnCompleteListener<AuthResult>() {
            if (it.isSuccessful) {
                Log.d(ContentValues.TAG, "signInWithEmail:success")
                updateUI(repo.auth.currentUser, activity)
            } else {
                Log.w(ContentValues.TAG, "signInWithEmail:failure", it.exception);
                updateUI(null, activity)
            }
        }
        )
    }

    private fun updateUI(account: FirebaseUser?, activity: Activity) {
        if (account == null) {
            //lol
        } else {
            activity.startActivity(MainActivity.createIntent(activity))
        }
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>, activity: Activity) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            firebaseAuthWithGoogle(account?.idToken, activity)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null, activity)
        }
    }
}