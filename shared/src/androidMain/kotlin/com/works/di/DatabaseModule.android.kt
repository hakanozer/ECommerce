package com.works.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import app.cash.sqldelight.db.SqlDriver
import com.works.TokenStorage
import com.works.data.local.AppDatabase
import org.koin.dsl.module

actual fun platformDatabaseModule() = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = AppDatabase.Schema,
            context = TokenStorage.context,
            name = "ecommerce.db"
        )
    }
    single { AppDatabase(driver = get()) }
}