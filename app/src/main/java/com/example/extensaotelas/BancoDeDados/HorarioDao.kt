package com.example.extensaotelas.BancoDeDados

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface HorarioDao {

    @Insert
    fun insertHorario(vararg horario: Horario)

    @Delete
    fun deleteHorario(vararg horario: Horario)
}