package com.gabriel_barros.controle_entregua_agua.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gabriel_barros.controle_entregua_agua.database.room.entity.Galao
import com.gabriel_barros.controle_entregua_agua.database.room.entity.GalaoComEntrega

@Dao
interface GalaoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGalao(galao: Galao)

    @Transaction
    @Query("SELECT * FROM Galao WHERE id = :galaoId")
    suspend fun getGalaoComEntregas(galaoId: Long): GalaoComEntrega

    @Query("SELECT * FROM Galao")
    suspend fun getAllGaloes(): List<Galao>

    @Delete
    suspend fun deleteGalao(galao: Galao)
}
