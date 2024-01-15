package com.example.formapp.di

import com.example.formapp.data.repository.ComponentRepositoryImpl
import com.example.formapp.data.repository.ComponentRepositoryImplB
import com.example.formapp.data.repository.HistorialRepositoryImpl
import com.example.formapp.data.repository.UserRepositoryImpl
import com.example.formapp.domain.repository.ComponentRepository
import com.example.formapp.domain.repository.ComponentRepositoryB
import com.example.formapp.domain.repository.HistorialRepository
import com.example.formapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindComponentRepository(impl: ComponentRepositoryImpl): ComponentRepository

    @Binds
    abstract fun bindComponentRepositoryB(impl: ComponentRepositoryImplB): ComponentRepositoryB

    @Binds
    abstract fun bindHistorialRepository(impl: HistorialRepositoryImpl): HistorialRepository
}