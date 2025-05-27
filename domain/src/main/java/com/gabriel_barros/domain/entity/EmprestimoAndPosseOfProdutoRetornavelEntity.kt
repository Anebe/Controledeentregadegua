package com.gabriel_barros.domain.entity

import kotlinx.serialization.Serializable


@Serializable
data class EmprestimoAndPosseOfProdutoRetornavelEntity(
    val qtd_emprestado: Int,
    val qtd_posse: Int,
    val cliente_id: Long,
    val produto_id: Long
) {
    companion object {
        fun emptyEmprestimoPosse(): EmprestimoAndPosseOfProdutoRetornavelEntity {
            return EmprestimoAndPosseOfProdutoRetornavelEntity(
                qtd_emprestado = 0,
                qtd_posse = 0,
                cliente_id = 0,
                produto_id = 0,
            )
        }
    }
}

typealias ProdutoId = Long
typealias Quantidade = Int

//class EmprestimoService(
//    private val emprestimoRepository: EmprestimoRepository
//) {
//    fun atualizarQuantidades(clienteId: Long, produtos: Map<ProdutoId, Quantidade>) {
//        val registros: List<EmprestimoAndPosseOfProdutoRetornavel> =
//            emprestimoRepository.findByClienteAndProduto(clienteId)
//
//        registros.forEach { emprestimo ->
//            produtos[emprestimo.produto_id]?.let { qtd->
//                val isNeedUpdate = emprestimo.atualizaEmprestimo(qtd)
//                if(isNeedUpdate) emprestimoRepository.save(emprestimo)
//            }
//        }
//    }
//}

class EmprestimoProdutoRetornavel(
    qtd_emprestado: Int,
    val cliente_id: Long,
    val produto_id: Long
) {
    private var qtd_emprestado: Int = 0

    init {
        this.qtd_emprestado = qtd_emprestado
    }

    fun emprestar(quantidade: Int) {
        require(quantidade > 0) { "Quantidade deve ser positiva" }
        qtd_emprestado += quantidade
    }

    fun devolver(quantidade: Int) {
        require(quantidade > 0 && quantidade <= qtd_emprestado) {
            "Quantidade inválida para devolução"
        }
        qtd_emprestado -= quantidade
    }
}