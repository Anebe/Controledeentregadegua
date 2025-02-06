package com.gabriel_barros.controle_entregua_agua.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gabriel_barros.controle_entregua_agua.database.room.entity.ClienteRoom
import com.gabriel_barros.controle_entregua_agua.database.room.entity.ClienteComPedidoRoom

@Dao
interface ClienteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCliente(clienteRoom: ClienteRoom)

    @Transaction
    @Query("SELECT * FROM ClienteRoom WHERE id = :clienteId")
    suspend fun getClienteComPedidos(clienteId: Long): ClienteComPedidoRoom

    @Query("SELECT * FROM ClienteRoom")
    suspend fun getAllClientes(): List<ClienteRoom>

    @Delete
    suspend fun deleteCliente(clienteRoom: ClienteRoom)
}
