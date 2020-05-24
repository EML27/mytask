package com.itis.mytask.presenter

import android.app.Activity
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.itis.mytask.di.component.DaggerAuthRepositoryComponent
import com.itis.mytask.model.TaskDto
import com.itis.mytask.repository.AuthRepository
import com.itis.mytask.repository.RepositoryEmulator
import com.itis.mytask.view.interfaces.MainActivityInterface
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter : MvpPresenter<MainActivityInterface>() {

    @Inject
    lateinit var repo: AuthRepository

    init {
        viewState.setText(
            FirebaseAuth.getInstance().currentUser?.email ?: "Bro u are logged out"
        )
        setList()
        DaggerAuthRepositoryComponent.create().inject(this)
    }

    fun checkList() {
        setList()
    }

    private fun setList() {
        viewState.setList(RepositoryEmulator.toList())
    }

    fun logout() {
        repo.auth.signOut()
    }

    fun initializeAd(activity: Activity) {
        MobileAds.initialize(activity)
        val adRequest = AdRequest.Builder().build()
        viewState.setAd(adRequest)
    }
}