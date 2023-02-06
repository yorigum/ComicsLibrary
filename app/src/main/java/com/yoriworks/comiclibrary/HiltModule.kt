package com.yoriworks.comiclibrary

import com.yoriworks.comiclibrary.model.api.ApiService
import com.yoriworks.comiclibrary.model.api.MarvelApiRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provieApiRepo() = MarvelApiRepo(ApiService.api)
}