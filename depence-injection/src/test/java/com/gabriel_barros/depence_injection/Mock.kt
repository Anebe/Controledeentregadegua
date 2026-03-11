package com.gabriel_barros.depence_injection

import com.gabriel_barros.usecase.dto.ClienteInput
import com.gabriel_barros.usecase.dto.ProdutoInput
import java.math.BigDecimal

var clienteMock = ClienteInput(
    nome = "nome",
    apelidos = listOf("n", "o"),
    descricao = "descricao",
)

var produtoMock = ProdutoInput(
    preco = BigDecimal(10.1),
    nome = "p1",
    custo = BigDecimal(5.0),
    descricao = "descricao",
)
