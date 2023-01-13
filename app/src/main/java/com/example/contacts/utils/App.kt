package com.example.contacts.utils

import android.app.Application
import androidx.room.Room
import com.example.contacts.data.db.AppDatabase
import com.example.contacts.data.repository.ContactRepositoryImpl
import com.example.contacts.data.repository.ContactsRepository
import com.example.contacts.data.viewmodel.HomeViewModel
import com.example.contacts.utils.Constant.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules (main)
        }
    }

        val main = module {
            single {
                Room.databaseBuilder(this@App,
                    AppDatabase::class.java,
                    DATABASE_NAME)
                    .allowMainThreadQueries().build()
            }

            single {
                val database = get<AppDatabase>()
                database.appDAO()
            }

            factory<ContactsRepository> { ContactRepositoryImpl(get()) }
            viewModel { HomeViewModel(get()) }


    }
}