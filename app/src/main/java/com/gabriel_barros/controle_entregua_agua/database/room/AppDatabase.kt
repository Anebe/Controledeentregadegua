package com.gabriel_barros.controle_entregua_agua.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gabriel_barros.controle_entregua_agua.database.room.dao.ClienteDao
import com.gabriel_barros.controle_entregua_agua.database.room.dao.EntregaDao
import com.gabriel_barros.controle_entregua_agua.database.room.dao.GalaoDao
import com.gabriel_barros.controle_entregua_agua.database.room.dao.PagamentoDao
import com.gabriel_barros.controle_entregua_agua.database.room.dao.PedidoDao
import com.gabriel_barros.controle_entregua_agua.database.room.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.database.room.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.database.room.entity.Galao
import com.gabriel_barros.controle_entregua_agua.database.room.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.database.room.entity.Pedido

@Database(
    entities = [
        Cliente::class,
        Entrega::class,
        Galao::class,
        Pagamento::class,
        Pedido::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
    abstract fun entregaDao(): EntregaDao
    abstract fun galaoDao(): GalaoDao
    abstract fun pagamentoDao(): PagamentoDao
    abstract fun pedidoDao(): PedidoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
