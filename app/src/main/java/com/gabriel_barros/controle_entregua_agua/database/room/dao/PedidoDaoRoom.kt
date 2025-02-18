package com.gabriel_barros.controle_entregua_agua.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gabriel_barros.controle_entregua_agua.database.room.entity.PedidoRoom
import com.gabriel_barros.controle_entregua_agua.database.room.entity.PedidoComEntregaRoom
import com.gabriel_barros.controle_entregua_agua.database.room.entity.PedidoComPagamentoRoom

@Dao
interface PedidoDaoRoom {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPedido(pedidoRoom: PedidoRoom)

    @Transaction
    @Query("SELECT * FROM PedidoRoom WHERE id = :pedidoId")
    suspend fun getPedidoComEntregas(pedidoId: Long): PedidoComEntregaRoom

    @Transaction
    @Query("SELECT * FROM PedidoRoom WHERE id = :pedidoId")
    suspend fun getPedidoComPagamentos(pedidoId: Long): PedidoComPagamentoRoom

    @Query("SELECT * FROM PedidoRoom")
    suspend fun getAllPedidos(): List<PedidoRoom>

    @Delete
    suspend fun deletePedido(pedidoRoom: PedidoRoom)
}
