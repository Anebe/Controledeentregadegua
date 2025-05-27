package com.gabriel_barros.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabriel_barros.controle_entregua_agua.database.room.entity.EntregaRoom

@Dao
interface EntregaDaoRoom {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntrega(entregaRoom: EntregaRoom)

    @Query("SELECT * FROM EntregaRoom")
    suspend fun getAllEntregas(): List<EntregaRoom>

    @Delete
    suspend fun deleteEntrega(entregaRoom: EntregaRoom)
}
