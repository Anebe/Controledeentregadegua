package com.gabriel_barros.controle_entregua_agua.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabriel_barros.controle_entregua_agua.database.room.entity.Entrega

@Dao
interface EntregaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntrega(entrega: Entrega)

    @Query("SELECT * FROM Entrega")
    suspend fun getAllEntregas(): List<Entrega>

    @Delete
    suspend fun deleteEntrega(entrega: Entrega)
}
