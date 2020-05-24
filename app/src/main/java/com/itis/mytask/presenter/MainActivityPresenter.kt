package com.itis.mytask.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.itis.mytask.model.TaskDto
import com.itis.mytask.repository.RepositoryEmulator
import com.itis.mytask.view.interfaces.MainActivityInterface

@InjectViewState
class MainActivityPresenter : MvpPresenter<MainActivityInterface>() {
    init {
        viewState.setText(
            FirebaseAuth.getInstance().currentUser?.email ?: "Bro u are logged out"
        )
        setList()

    }

    fun checkList() {
        setList()
    }

    private fun setList() {
        viewState.setList(RepositoryEmulator.toList())
    }
}