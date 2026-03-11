package com.gabriel_barros.usecase.service

import com.gabriel_barros.domain_portout.ClientePortOut
import com.gabriel_barros.usecase.dto.ClienteInput
import com.gabriel_barros.usecase.dto.ClienteOutput
import com.gabriel_barros.usecase.usecase.ClienteManager

internal class ClienteManagerImp(private val clienteOut: ClientePortOut): ClienteManager {

    override suspend fun deleteCliente(id: Long): ClienteOutput {
        return ClienteOutput(clienteOut.deleteCliente(id))
    }

    override suspend fun registerCliente(cliente: ClienteInput): ClienteOutput {
        val cliente = clienteOut.saveCliente(cliente.to())
        return ClienteOutput(cliente)
    }

}