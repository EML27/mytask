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
        btn_login.setOnClickListener {
            presenter.signInThroughEmail(
                et_sign_in_login.text.toString(),
                et_password.text.toString(),
                this
            )
        }
        btn_forgot_password.setOnClickListener {
            startActivity(ResetPasswordActivity.createIntent(this))
        }
        btn_phone_login.setOnClickListener {
            startActivity(PhoneLoginActivity.createIntent(this))
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

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            presenter.handleSignInResult(task, this)
        }
    }

    private fun updateUI(account: FirebaseUser?) {
        if (account == null) {
            //lol
        } else {
            startActivity(MainActivity.createIntent(this))
        }
    }


    companion object {
        fun createIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
