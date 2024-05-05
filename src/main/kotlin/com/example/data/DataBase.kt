package com.example.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DataBase {
    fun init() {
        Database.connect(hikari())
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql://dpg-coh6tsev3ddc73fip3b0-a.frankfurt-postgres.render.com:5432/film_app_150e"
        config.username = "film_app_150e_user"
        config.password = "99gpLFPfTNPB6yHVUBfvflg8Q4Zcwlyb"

        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

        config.validate() // Валидация конфигурации HikariCP
        return HikariDataSource(config) // Возвращаем HikariDataSource с настроенной конфигурацией
    }

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
        transaction {
            block() // Запускаем блок кода внутри транзакции
        }
    }
}