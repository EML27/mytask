package com.itis.mytask.presenter

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.itis.mytask.di.component.DaggerAuthRepositoryComponent
import com.itis.mytask.repository.AuthRepository
import com.itis.mytask.view.MainActivity
import com.itis.mytask.view.interfaces.PhoneLoginInterface
import kotlinx.android.synthetic.main.activity_phone_login.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class PhoneActivityPresenter : MvpPresenter<PhoneLoginInterface>() {

    init {
        DaggerAuthRepositoryComponent.create().inject(this)
    }

    @Inject
    lateinit var repo: AuthRepository

    fun sendSMS(phoneNumber: String, activity: Activity) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            activity, // Activity (for callback binding)
            callbacks
        ) // OnVerificationStateChangedCallbacks
    }

    var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")

            viewState.signIn(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(ContentValues.TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            }

            // Show a message and update the UI
            // ...
        }

        lateinit var storedVerificationId: String
        lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(ContentValues.TAG, "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            resendToken = token

            // ...
        }
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, activity: Activity) {
        repo.auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    updateUI(user, activity)
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
    }

    private fun updateUI(user: FirebaseUser?, activity: Activity) {
        if (user == null) {
            Toast.makeText(activity, "something went wrong", Toast.LENGTH_SHORT).show()
        } else {
            activity.startActivity(MainActivity.createIntent(activity))
        }
    }
}