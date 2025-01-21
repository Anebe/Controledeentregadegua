package com.gabriel_barros.controle_entregua_agua.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabriel_barros.controle_entregua_agua.database.room.entity.Pagamento

@Dao
interface PagamentoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagamento(pagamento: Pagamento)

    @Query("SELECT * FROM Pagamento")
    suspend fun getAllPagamentos(): List<Pagamento>

    @Delete
    suspend fun deletePagamento(pagamento: Pagamento)
}
