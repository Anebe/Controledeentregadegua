package com.gabriel_barros.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabriel_barros.data_room.entity.ClienteComPedidoRoom
import com.gabriel_barros.data_room.entity.ClienteRoom
import com.gabriel_barros.domain_portout.ClientePortOut

@Dao
interface ClienteDaoRoom: ClientePortOut {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCliente(clienteRoom: ClienteRoom)

    @Query("SELECT * FROM ClienteRoom WHERE id = :clienteId")
    suspend fun getClienteComPedidos(clienteId: Long): ClienteComPedidoRoom

    @Query("SELECT * FROM ClienteRoom")
    suspend fun getAllClientes(): List<ClienteRoom>

    @Delete
    suspend fun deleteCliente(clienteRoom: ClienteRoom)
}
