package com.itis.mytask.view


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.itis.mytask.R
import com.itis.mytask.presenter.LoginActivityPresenter
import com.itis.mytask.view.interfaces.LoginActivityInterface
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : MvpAppCompatActivity(), LoginActivityInterface {

    @InjectPresenter
    lateinit var presenter: LoginActivityPresenter

    private lateinit var auth: FirebaseAuth

    lateinit var gso: GoogleSignInOptions

    // Build a GoogleSignInClient with the options specified by gso.
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val RC_SIGN_IN = 27

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()
        defineListeners()

    }

    private fun defineListeners() {
        sign_in_button.setOnClickListener { signIn() }
        btn_login.setOnClickListener { signInThroughEmail() }
        btn_forgot_password.setOnClickListener {
            startActivity(
                ResetPasswordActivity.createIntent(
                    this
                )
            )
        }
        btn_phone_login.setOnClickListener {
            startActivity(
                PhoneLoginActivity.createIntent(this)
            )
        }
        btn_register.setOnClickListener { startActivity(RegisterActivity.createIntent(this)) }
    }


    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = auth.currentUser
        updateUI(account)
    }

    private fun signInThroughEmail() {
        auth.signInWithEmailAndPassword(
            et_sign_in_login.text.toString(),
            et_password.text.toString()
        ).addOnCompleteListener(this, OnCompleteListener<AuthResult>() {
            if (it.isSuccessful) {
                Log.d(TAG, "signInWithEmail:success")
                updateUI(auth.currentUser)
            } else {
                Log.w(TAG, "signInWithEmail:failure", it.exception);
                updateUI(null)
            }
        }
        )
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            firebaseAuthWithGoogle(account?.idToken)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: FirebaseUser?) {
        if (account == null) {
            //lol
        } else {
            startActivity(MainActivity.createIntent(this))
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
                    Toast.makeText(this, "Fail!!!", Toast.LENGTH_SHORT).show()

                    updateUI(null)
                }

                // ...
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        auth.signOut()
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
