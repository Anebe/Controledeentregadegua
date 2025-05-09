package com.gabriel_barros.supabase

import com.gabriel_barros.domain.domain.entity.Categoria
import com.gabriel_barros.domain.domain.entity.Cliente
import com.gabriel_barros.domain.domain.entity.EmprestimoAndPosseOfProdutoRetornavel
import com.gabriel_barros.domain.domain.entity.Endereco
import com.gabriel_barros.domain.domain.entity.Entrega
import com.gabriel_barros.domain.domain.entity.ItensEntrega
import com.gabriel_barros.domain.domain.entity.ItensPedido
import com.gabriel_barros.domain.domain.entity.PagamentoEntity
import com.gabriel_barros.domain.domain.entity.PedidoEntity
import com.gabriel_barros.domain.domain.entity.ProdutoEntity
import com.gabriel_barros.supabase.entity.CategoriaSupabase
import com.gabriel_barros.supabase.entity.ClienteSupabase
import com.gabriel_barros.supabase.entity.EmprestimoPosseSupabase
import com.gabriel_barros.supabase.entity.EnderecoSupabase
import com.gabriel_barros.supabase.entity.EntregaSupabase
import com.gabriel_barros.supabase.entity.ItensEntregaSupabase
import com.gabriel_barros.supabase.entity.ItensPedidoSupabase
import com.gabriel_barros.supabase.entity.PagamentoSupabase
import com.gabriel_barros.supabase.entity.PedidoSupabase
import com.gabriel_barros.supabase.entity.ProdutoSupabase

@Deprecated("")
internal object Mapper {
    fun toClienteSupabase(cliente: Cliente): ClienteSupabase {
        return ClienteSupabase(
            id = cliente.id,
            nome = cliente.nome,
            credito = cliente.credito,
            apelidos = cliente.apelidos,
            descricao = cliente.descricao,
        )
    }

    fun toCliente(clienteSupabase: ClienteSupabase): Cliente {
        return Cliente(
            id = clienteSupabase.id,
            nome = clienteSupabase.nome,
            credito = clienteSupabase.credito,
            apelidos = clienteSupabase.apelidos,
            descricao = clienteSupabase.descricao,
            //enderecos = emptyList(),
        )
    }

    fun toEnderecoSupabase(endereco: Endereco): EnderecoSupabase {
        return EnderecoSupabase(
            id = endereco.id,
            cliente_id = endereco.cliente_id,
            cep = endereco.cep,
            rua = endereco.rua,
            bairro = endereco.bairro,
            complemento = endereco.complemento,
            numero = endereco.numero,
        )
    }

    fun toEndereco(endereco: EnderecoSupabase): Endereco {
        return Endereco(
            id = endereco.id,
            cliente_id = endereco.cliente_id,
            cep = endereco.cep,
            rua = endereco.rua,
            bairro = endereco.bairro,
            complemento = endereco.complemento,
            numero = endereco.numero,
        )
    }

    fun toPedidoSupabase(pedido: PedidoEntity): PedidoSupabase {
        return PedidoSupabase(
            id = pedido.id,
            cliente_id = pedido.cliente_id,
            data = pedido.data,
            data_entrega = pedido.data_agendada_para_entrega,
            status = pedido.status,
            valor_total = pedido.valor_total,
        )
    }

    fun toPedido(pedido: PedidoSupabase): PedidoEntity {
        return PedidoEntity(
            id = pedido.id,
            cliente_id = pedido.cliente_id,
            data = pedido.data,
            data_agendada_para_entrega = pedido.data_entrega,
            status = pedido.status,
            valor_total = pedido.valor_total,
        )
    }

    fun toItensPedidoSupabase(itemPedido: ItensPedido): ItensPedidoSupabase {
        return ItensPedidoSupabase(
            id = itemPedido.id,
            pedido_id = itemPedido.pedido_id,
            produto_id = itemPedido.produto_id,
            qtd = itemPedido.qtd,
        )
    }

    fun toItensPedido(itensPedidoSupabase: ItensPedidoSupabase): ItensPedido {
        return ItensPedido(
            id = itensPedidoSupabase.id,
            pedido_id = itensPedidoSupabase.pedido_id, // Pedido_id no lugar de um objeto completo
            produto_id = itensPedidoSupabase.produto_id, // Produto_id no lugar de um objeto completo
            qtd = itensPedidoSupabase.qtd
        )
    }

    fun toEntregaSupabase(entrega: Entrega): EntregaSupabase {
        return EntregaSupabase(
            id = entrega.id,
            data = entrega.data,
            entregador = entrega.entregador,
            status = entrega.status,
            pedido_id = entrega.pedido_id
        )
    }

    fun toEntrega(entregaSupabase: EntregaSupabase): Entrega {
        return Entrega(
            id = entregaSupabase.id,
            data = entregaSupabase.data,
            entregador = entregaSupabase.entregador,
            status = entregaSupabase.status,
            pedido_id = entregaSupabase.pedido_id
        )
    }

    fun toPagamentoSupabase(pagamento: PagamentoEntity): PagamentoSupabase {
        return PagamentoSupabase(
            id = pagamento.id,
            pedido_id = pagamento.pedido_id, // Pedido_id no lugar de um objeto completo
            data = pagamento.data,
            valor = pagamento.valor,
            pagamento = pagamento.pagamento,
        )
    }

    fun toPagamento(pagamentoSupabase: PagamentoSupabase): PagamentoEntity {
        return PagamentoEntity(
            id = pagamentoSupabase.id,
            pedido_id = pagamentoSupabase.pedido_id, // Pedido_id no lugar de um objeto completo
            data = pagamentoSupabase.data,
            valor = pagamentoSupabase.valor,
            pagamento = pagamentoSupabase.pagamento,
        )
    }
    fun toProdutoSupabase(produto: ProdutoEntity): ProdutoSupabase {
        return ProdutoSupabase(
            id = produto.id,
            preco = produto.preco,
            nome = produto.nome,
            custo = produto.custo,
            estoque = produto.estoque,
            descricao = produto.descricao,
            reservado = produto.reservado)
    }

    fun toProduto(produtoSupabase: ProdutoSupabase): ProdutoEntity {
        return ProdutoEntity(
            id = produtoSupabase.id,
            preco = produtoSupabase.preco,
            nome = produtoSupabase.nome,
            custo = produtoSupabase.custo,
            estoque = produtoSupabase.estoque,
            descricao = produtoSupabase.descricao,
            reservado = produtoSupabase.reservado
        )
    }
    fun toEmprestimoPosseSupabase(emprestimo: EmprestimoAndPosseOfProdutoRetornavel): EmprestimoPosseSupabase {
        return EmprestimoPosseSupabase(
            qtd_emprestado = emprestimo.qtd_emprestado,
            qtd_posse = emprestimo.qtd_posse,
            cliente_id = emprestimo.cliente_id, // Cliente_id no lugar de um objeto completo
            produto_id = emprestimo.produto_id // Produto_id no lugar de um objeto completo
        )
    }

    fun toEmprestimoPosse(emprestimoSupabase: EmprestimoPosseSupabase): EmprestimoAndPosseOfProdutoRetornavel {
        return EmprestimoAndPosseOfProdutoRetornavel(
            qtd_emprestado = emprestimoSupabase.qtd_emprestado,
            qtd_posse = emprestimoSupabase.qtd_posse,
            cliente_id = emprestimoSupabase.cliente_id, // O cliente é identificado pelo id
            produto_id = emprestimoSupabase.produto_id // O produto é identificado pelo id
        )
    }
    fun toCategoriaSupabase(categoria: Categoria): CategoriaSupabase {
        return CategoriaSupabase(
            id = categoria.id,
            categoria_pai = categoria.categoria_pai, // Mapeia `categoriaPai` para `categoria_pai`
            nome = categoria.nome
        )
    }
    fun toCategoria(categoriaSupabase: CategoriaSupabase): Categoria {
        return Categoria(
            id = categoriaSupabase.id,
            categoria_pai = categoriaSupabase.categoria_pai, // Mapeia `categoria_pai` para `categoriaPai`
            nome = categoriaSupabase.nome
        )
    }
    fun toItensEntregaSupabase(itensEntrega: ItensEntrega): ItensEntregaSupabase {
        return ItensEntregaSupabase(
            entrega_id = itensEntrega.entrega_id, // Mapeia `entregaId` para `entrega_id`
            itemPedido_id = itensEntrega.itemPedido_id, // Mapeia `itemPedidoId` para `ItemPedido_id`
            qtdEntregue = itensEntrega.qtdEntregue,
            qtdRetornado = itensEntrega.qtdRetornado
        )
    }
    fun toItensEntrega(itensEntregaSupabase: ItensEntregaSupabase): ItensEntrega {
        return ItensEntrega(
            entrega_id = itensEntregaSupabase.entrega_id, // Mapeia `entrega_id` para `entregaId`
            itemPedido_id = itensEntregaSupabase.itemPedido_id, // Mapeia `ItemPedido_id` para `itemPedidoId`
            qtdEntregue = itensEntregaSupabase.qtdEntregue,
            qtdRetornado = itensEntregaSupabase.qtdRetornado
        )
    }

}