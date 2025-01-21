package com.gabriel_barros.controle_entregua_agua.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gabriel_barros.controle_entregua_agua.database.room.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.database.room.entity.ClienteComPedido

@Dao
interface ClienteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCliente(cliente: Cliente)

    @Transaction
    @Query("SELECT * FROM Cliente WHERE id = :clienteId")
    suspend fun getClienteComPedidos(clienteId: Long): ClienteComPedido

    @Query("SELECT * FROM Cliente")
    suspend fun getAllClientes(): List<Cliente>

    @Delete
    suspend fun deleteCliente(cliente: Cliente)
}
