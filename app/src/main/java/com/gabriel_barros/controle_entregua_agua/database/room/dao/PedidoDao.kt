package com.gabriel_barros.controle_entregua_agua.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gabriel_barros.controle_entregua_agua.database.room.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.database.room.entity.PedidoComEntrega
import com.gabriel_barros.controle_entregua_agua.database.room.entity.PedidoComPagamento

@Dao
interface PedidoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPedido(pedido: Pedido)

    @Transaction
    @Query("SELECT * FROM Pedido WHERE id = :pedidoId")
    suspend fun getPedidoComEntregas(pedidoId: Long): PedidoComEntrega

    @Transaction
    @Query("SELECT * FROM Pedido WHERE id = :pedidoId")
    suspend fun getPedidoComPagamentos(pedidoId: Long): PedidoComPagamento

    @Query("SELECT * FROM Pedido")
    suspend fun getAllPedidos(): List<Pedido>

    @Delete
    suspend fun deletePedido(pedido: Pedido)
}
