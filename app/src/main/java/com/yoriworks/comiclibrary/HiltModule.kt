package com.yoriworks.comiclibrary

import android.content.Context
import androidx.room.Room
import com.yoriworks.comiclibrary.model.api.ApiService
import com.yoriworks.comiclibrary.model.api.MarvelApiRepo
import com.yoriworks.comiclibrary.model.db.CharacterDao
import com.yoriworks.comiclibrary.model.db.CollectionDb
import com.yoriworks.comiclibrary.model.db.CollectionDbRepo
import com.yoriworks.comiclibrary.model.db.CollectionDbRepoImpl
import com.yoriworks.comiclibrary.model.db.Constants.DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApiRepo() = MarvelApiRepo(ApiService.api)

    @Provides
    fun provideCollectionDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDb::class.java, DB).build()

    @Provides
    fun provideCharacterDao(collectionDb: CollectionDb) = collectionDb.characterDao()

    @Provides
    fun provideDbRepoImpl(characterDao: CharacterDao): CollectionDbRepo =
        CollectionDbRepoImpl(characterDao)
}