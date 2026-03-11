package com.gabriel_barros.usecase.dto

import com.gabriel_barros.domain.entity.Cliente
import com.gabriel_barros.domain.entity.Endereco
import com.gabriel_barros.domain.entity.Produto
import com.gabriel_barros.domain.entity.TipoEntregador
import java.math.BigDecimal

enum class TipoEntregadorDTO {
    COMERCIO,
    CLIENTE,


}

internal fun TipoEntregadorDTO.to(): TipoEntregador = when (this) {
    TipoEntregadorDTO.COMERCIO -> TipoEntregador.COMERCIO
    TipoEntregadorDTO.CLIENTE -> TipoEntregador.CLIENTE
}

data class ClienteInput(
    val nome: String,
    val apelidos: List<String> = emptyList(),
    val descricao: String = "",
    val enderecos: List<EnderecoInput> = emptyList(),
) {
    internal fun to() = Cliente(
        nome = nome,
        apelidos = apelidos,
        descricao = descricao,
        enderecos = enderecos.map { it.to() }
    )
}

data class PedidoInput(
    val clienteId: Long,
    val produtos: Set<ProdutoPedidoInput>,
)

data class ProdutoPedidoInput(
    val qtd: Int,
    val produtoId: Long
)

data class EnderecoInput(
    val cep: String,
    val rua: String,
    val bairro: String,
    val complemento: String,
    val numero: String,
) {
    internal fun to() = Endereco(
        cep = cep,
        rua = rua,
        bairro = bairro,
        complemento = complemento,
        numero = numero
    )
}


enum class TipoPagamentoDTO(string: String) {
    DINHEIRO("DINHEIRO"),
    PIX("PIX")
}

data class PagamentoInput(
    val valor: BigDecimal,
    val tipoPagamento: TipoPagamentoDTO
)


data class EntregaInput(
    var freteId: Long,
    val produtosEntregues: MutableSet<ProdutoEntregueInput> = mutableSetOf(),
    val entregador: TipoEntregadorDTO
)

data class ProdutoEntregueInput(
    var produtoPedidoId: Long,
    var qtdEntregue: Int,
    var qtdRetornavel: Int
)

data class ProdutoInput(
    val preco: BigDecimal,
    val nome: String,
    val custo: BigDecimal,
    val descricao: String = "",
) {
    internal fun to() = Produto(
        preco = this.preco,
        nome = this.nome,
        custo = this.custo,
        estoque = 0,
        reservado = 0,
        descricao = this.descricao
    )
}

data class ProdutoOutput(
    val id: Long,
    val preco: BigDecimal,
    val nome: String,
    val descricao: String = "",
) {
    constructor(produto: Produto) : this(
        id = produto.id.id,
        preco = produto.preco,
        nome = produto.nome,
        descricao = produto.descricao,
    )
}