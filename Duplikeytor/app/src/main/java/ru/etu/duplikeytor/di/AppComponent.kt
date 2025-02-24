package ru.etu.duplikeytor.di

import dagger.Component
import ru.etu.duplikeytor.Application
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(application: Application)
}