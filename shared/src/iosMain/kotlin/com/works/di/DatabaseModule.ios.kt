package com.works.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.db.SqlDriver
import com.works.data.local.AppDatabase
import org.koin.dsl.module

actual fun platformDatabaseModule() = module {
    single<SqlDriver> {
        NativeSqliteDriver(
            schema = AppDatabase.Schema,
            name = "ecommerce.db"
        )
    }
    single { AppDatabase(driver = get()) }
}