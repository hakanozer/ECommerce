package com.works.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.works.data.local.AppDatabase
import org.koin.dsl.module
import java.io.File

actual fun platformDatabaseModule() = module {
    single<SqlDriver> {
        val databasePath = File(System.getProperty("java.io.tmpdir"), "ecommerce.db")
        val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")
        if (!databasePath.exists()) {
            AppDatabase.Schema.create(driver)
        }
        driver
    }
    single { AppDatabase(driver = get()) }
}