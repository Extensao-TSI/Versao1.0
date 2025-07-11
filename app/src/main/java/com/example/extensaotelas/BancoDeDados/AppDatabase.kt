package com.example.extensaotelas.BancoDeDados

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Horario::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun horarioDao(): HorarioDao
}