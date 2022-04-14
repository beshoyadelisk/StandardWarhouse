package com.gargour.warehouse.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gargour.warehouse.WarehouseApp
import com.gargour.warehouse.data.data_source.WarehouseDb
import com.gargour.warehouse.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesEgoDatabase(app: Application): WarehouseDb {
        return Room.databaseBuilder(
            app,
            WarehouseDb::class.java,
            WarehouseDb.DATABASE_NAME
        ).addCallback(callBack)
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }

    private val callBack = object : RoomDatabase.Callback() {
        private val USERNAME = "admin"
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            db.execSQL("INSERT INTO USER VALUES ('$USERNAME','$USERNAME')")
        }
    }

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}