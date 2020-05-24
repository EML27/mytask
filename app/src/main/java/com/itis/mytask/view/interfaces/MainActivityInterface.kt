package com.itis.mytask.view.interfaces

import android.content.Context
import com.arellomobile.mvp.MvpView
import com.google.android.gms.ads.AdRequest
import com.itis.mytask.model.TaskDto

interface MainActivityInterface : MvpView {
    fun setText(text: String)
    fun setList(list: List<TaskDto>)
    fun setAd(adRequest: AdRequest)
}