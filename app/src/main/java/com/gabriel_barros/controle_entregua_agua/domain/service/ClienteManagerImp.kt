package com.gabriel_barros.controle_entregua_agua.domain.service

import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.portout.ClientePortOut
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ClienteManager

class ClienteManagerImp(private val clienteOut: ClientePortOut): ClienteManager {
    data class ClienteInput(
        val nome: String = "",
        val apelidos: List<String> = emptyList(),
        val descricao: String = "",
    ){
        init {
            require(nome.isNotBlank())
        }
    }
//    override suspend fun deleteCliente(id: Long): Cliente? {
//        return clienteOut.deleteCliente(id)
//    }

    override suspend fun registerCliente(cliente: Cliente): Cliente {
        return clienteOut.saveCliente(cliente)
    }

}