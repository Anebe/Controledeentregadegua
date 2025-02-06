package com.gabriel_barros.controle_entregua_agua.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gabriel_barros.controle_entregua_agua.database.room.entity.GalaoRoom
import com.gabriel_barros.controle_entregua_agua.database.room.entity.GalaoComEntregaRoom

@Dao
interface GalaoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGalao(galaoRoom: GalaoRoom)

    @Transaction
    @Query("SELECT * FROM GalaoRoom WHERE id = :galaoId")
    suspend fun getGalaoComEntregas(galaoId: Long): GalaoComEntregaRoom

    @Query("SELECT * FROM GalaoRoom")
    suspend fun getAllGaloes(): List<GalaoRoom>

    @Delete
    suspend fun deleteGalao(galaoRoom: GalaoRoom)
}
