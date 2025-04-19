package com.gabriel_barros.controle_entregua_agua.domain.service.deprecated

import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.portout.ClientePortOut
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ClienteManager

class ClienteManagerImp(private val clienteOut: ClientePortOut): ClienteManager {

//    override suspend fun deleteCliente(id: Long): Cliente? {
//        return clienteOut.deleteCliente(id)
//    }

    override suspend fun registerCliente(cliente: Cliente): Cliente {
        return clienteOut.saveCliente(cliente)
    }

}