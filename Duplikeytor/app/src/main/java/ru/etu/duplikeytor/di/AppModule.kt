package ru.etu.duplikeytor.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.etu.duplikeytor.data.AppDatabase
import ru.etu.duplikeytor.domain.dao.KeyDao
import ru.etu.duplikeytor.domain.repository.ImageRepository
import ru.etu.duplikeytor.domain.repository.KeyRepository
import ru.etu.duplikeytor.domain.usecases.ShareUsecase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideKeyDao(database: AppDatabase): KeyDao {
        return database.keyDao()
    }

    @Provides
    @Singleton
    fun provideKeyRepository(userDao: KeyDao): KeyRepository {
        return KeyRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideShareUsecase(): ShareUsecase {
        return ShareUsecase()
    }

    @Provides
    @Singleton
    fun provideImageRepository(@ApplicationContext context: Context): ImageRepository {
        return ImageRepository(context)
    }

}
