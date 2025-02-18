package com.gabriel_barros.controle_entregua_agua.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gabriel_barros.controle_entregua_agua.database.room.dao.ClienteDaoRoom
import com.gabriel_barros.controle_entregua_agua.database.room.dao.EntregaDaoRoom
import com.gabriel_barros.controle_entregua_agua.database.room.dao.GalaoDaoRoom
import com.gabriel_barros.controle_entregua_agua.database.room.dao.PagamentoDaoRoom
import com.gabriel_barros.controle_entregua_agua.database.room.dao.PedidoDaoRoom
import com.gabriel_barros.controle_entregua_agua.database.room.entity.ClienteRoom
import com.gabriel_barros.controle_entregua_agua.database.room.entity.EntregaRoom
import com.gabriel_barros.controle_entregua_agua.database.room.entity.GalaoRoom
import com.gabriel_barros.controle_entregua_agua.database.room.entity.PagamentoRoom
import com.gabriel_barros.controle_entregua_agua.database.room.entity.PedidoRoom

@Database(
    entities = [
        ClienteRoom::class,
        EntregaRoom::class,
        GalaoRoom::class,
        PagamentoRoom::class,
        PedidoRoom::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDaoRoom
    abstract fun entregaDao(): EntregaDaoRoom
    abstract fun galaoDao(): GalaoDaoRoom
    abstract fun pagamentoDao(): PagamentoDaoRoom
    abstract fun pedidoDao(): PedidoDaoRoom

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
