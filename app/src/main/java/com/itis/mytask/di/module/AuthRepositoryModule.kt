package com.itis.mytask.di.module

import com.itis.mytask.repository.AuthRepository
import dagger.Module
import dagger.Provides

@Module
class AuthRepositoryModule {
@Provides
fun getRepo(): AuthRepository = AuthRepository()
}