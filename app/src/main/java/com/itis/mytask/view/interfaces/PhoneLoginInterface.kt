package com.itis.mytask.view.interfaces

import com.arellomobile.mvp.MvpView
import com.google.firebase.auth.PhoneAuthCredential

interface PhoneLoginInterface: MvpView {
    fun signIn(credential: PhoneAuthCredential)
}