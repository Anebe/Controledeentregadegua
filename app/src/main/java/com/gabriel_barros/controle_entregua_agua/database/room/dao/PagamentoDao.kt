package com.gabriel_barros.controle_entregua_agua.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabriel_barros.controle_entregua_agua.database.room.entity.PagamentoRoom

@Dao
interface PagamentoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagamento(pagamentoRoom: PagamentoRoom)

    @Query("SELECT * FROM PagamentoRoom")
    suspend fun getAllPagamentos(): List<PagamentoRoom>

    @Delete
    suspend fun deletePagamento(pagamentoRoom: PagamentoRoom)
}
