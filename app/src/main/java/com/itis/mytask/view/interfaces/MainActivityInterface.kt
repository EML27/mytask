package com.itis.mytask.view.interfaces

import com.arellomobile.mvp.MvpView
import com.itis.mytask.model.TaskDto

interface MainActivityInterface : MvpView {
    fun setText(text: String)
    fun setList(list: List<TaskDto>)
}