package com.gabriel_barros.usecase.service

import com.gabriel_barros.domain.domain.error.InvalidDataException
import com.gabriel_barros.domain.domain.usecase.PedidoManager
import com.gabriel_barros.domain.domain.usecase.Validate

object ValidatePedidoInput: Validate<PedidoManager.PedidoInput> {
    override fun validateOrThrow(data: PedidoManager.PedidoInput) {
        val msgList = mutableListOf<String>()

        if(data.clienteId > 0) msgList.add("cliente com id ${data.clienteId} é inválido")
        data.itensDoPedido.forEach { produto ->
            if(produto.qtd > 0) msgList.add("quantidade ${produto.qtd} é inválida para produto de id ${produto.produtoId} ")
            if(produto.produtoId > 0) msgList.add("produto com id ${produto.produtoId} é inválido")
        }

        if(msgList.isNotEmpty()){
            throw InvalidDataException(msgList.joinToString(","))
        }
    }
}