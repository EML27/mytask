package com.itis.mytask.di.component

import com.itis.mytask.di.module.AuthRepositoryModule
import com.itis.mytask.presenter.*
import dagger.Component

@Component(modules = [AuthRepositoryModule::class])
interface AuthRepositoryComponent {
    fun inject(presenter: LoginActivityPresenter)

    fun inject(presenter: MainActivityPresenter)

    fun inject(presenter: RegisterActivityPresenter)

    fun inject(presenter: ResetPasswordActivityPresenter)

    fun inject(presenter: PhoneActivityPresenter)
}