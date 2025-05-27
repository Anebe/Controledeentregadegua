package com.gabriel_barros.data_supabase

import com.gabriel_barros.data_supabase.dao.query.DividaSupabase
import com.gabriel_barros.data_supabase.dao.query.FreteSupabase
import com.gabriel_barros.data_supabase.entity.ClienteSupabase
import com.gabriel_barros.data_supabase.entity.EmprestimoPosseSupabase
import com.gabriel_barros.data_supabase.entity.EnderecoSupabase
import com.gabriel_barros.data_supabase.entity.EntregaSupabase
import com.gabriel_barros.data_supabase.entity.ItensEntregaSupabase
import com.gabriel_barros.data_supabase.entity.ItensPedidoSupabase
import com.gabriel_barros.data_supabase.entity.PagamentoSupabase
import com.gabriel_barros.data_supabase.entity.PedidoSupabase
import com.gabriel_barros.data_supabase.entity.ProdutoSupabase
import com.gabriel_barros.domain.entity.Cliente
import com.gabriel_barros.domain.entity.Divida
import com.gabriel_barros.domain.entity.EmprestimoAndPosseOfProdutoRetornavelEntity
import com.gabriel_barros.domain.entity.Endereco
import com.gabriel_barros.domain.entity.Entrega
import com.gabriel_barros.domain.entity.Frete
import com.gabriel_barros.domain.entity.Id
import com.gabriel_barros.domain.entity.ItensEntrega
import com.gabriel_barros.domain.entity.ItensPedido
import com.gabriel_barros.domain.entity.Pagamento
import com.gabriel_barros.domain.entity.Pedido
import com.gabriel_barros.domain.entity.Produto
import com.gabriel_barros.domain.entity.ProdutoEntregue
import com.gabriel_barros.domain.entity.ProdutoPedido

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

    fun toPedidoSupabase(pedido: Pedido): PedidoSupabase {
        return PedidoSupabase(
            id = pedido.id.id,
            cliente_id = pedido.cliente_id.id,
            data = pedido.dataPedido,
            status = pedido.status,
        )
    }

//    fun toPedido(pedido: PedidoSupabase): PedidoEntity {
//        return PedidoEntity(
//            id = pedido.id,
//            cliente_id = pedido.cliente_id,
//            data = pedido.data,
//            data_agendada_para_entrega = pedido.data_entrega,
//            status = pedido.status,
//            valor_total = pedido.valor_total,
//        )
//    }

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
            frete_id = entrega.freteId,
        )
    }

    fun toPagamentoSupabase(pagamento: Pagamento): PagamentoSupabase {
        return PagamentoSupabase(
            id = pagamento.id,
            data = pagamento.data,
            valor = pagamento.valor,
            pagamento = pagamento.pagamento,
            divida_id = pagamento.divida_id
        )
    }

    fun toPagamento(pagamentoSupabase: PagamentoSupabase): Pagamento {
        return Pagamento(
            id = pagamentoSupabase.id,
            data = pagamentoSupabase.data,
            valor = pagamentoSupabase.valor,
            divida_id = pagamentoSupabase.divida_id,
            pagamento = pagamentoSupabase.pagamento,
        )
    }
//    fun toProdutoSupabase(produto: ProdutoEntity): ProdutoSupabase {
//        return ProdutoSupabase(
//            id = produto.id,
//            preco = produto.preco,
//            nome = produto.nome,
//            custo = produto.custo,
//            estoque = produto.estoque,
//            descricao = produto.descricao,
//            reservado = produto.reservado)
//    }

    fun toProduto(produtoSupabase: ProdutoSupabase): Produto {
        return Produto(
            id = Id(produtoSupabase.id),
            preco = produtoSupabase.preco,
            nome = produtoSupabase.nome,
            custo = produtoSupabase.custo,
            estoque = produtoSupabase.estoque,
            descricao = produtoSupabase.descricao,
            reservado = produtoSupabase.reservado
        )
    }
    fun toEmprestimoPosseSupabase(emprestimo: EmprestimoAndPosseOfProdutoRetornavelEntity): EmprestimoPosseSupabase {
        return EmprestimoPosseSupabase(
            qtd_emprestado = emprestimo.qtd_emprestado,
            qtd_posse = emprestimo.qtd_posse,
            cliente_id = emprestimo.cliente_id, // Cliente_id no lugar de um objeto completo
            produto_id = emprestimo.produto_id // Produto_id no lugar de um objeto completo
        )
    }

    fun toEmprestimoPosse(emprestimoSupabase: EmprestimoPosseSupabase): EmprestimoAndPosseOfProdutoRetornavelEntity {
        return EmprestimoAndPosseOfProdutoRetornavelEntity(
            qtd_emprestado = emprestimoSupabase.qtd_emprestado,
            qtd_posse = emprestimoSupabase.qtd_posse,
            cliente_id = emprestimoSupabase.cliente_id, // O cliente é identificado pelo id
            produto_id = emprestimoSupabase.produto_id // O produto é identificado pelo id
        )
    }
//    fun toCategoriaSupabase(categoria: Categoria): CategoriaSupabase {
//        return CategoriaSupabase(
//            id = categoria.id,
//            categoria_pai = categoria.categoria_pai, // Mapeia `categoriaPai` para `categoria_pai`
//            nome = categoria.nome
//        )
//    }
//    fun toCategoria(categoriaSupabase: CategoriaSupabase): Categoria {
//        return Categoria(
//            id = categoriaSupabase.id,
//            categoria_pai = categoriaSupabase.categoria_pai, // Mapeia `categoria_pai` para `categoriaPai`
//            nome = categoriaSupabase.nome
//        )
//    }
    fun toItensEntregaSupabase(itensEntrega: ItensEntrega): ItensEntregaSupabase {
        return ItensEntregaSupabase(
            entrega_id = itensEntrega.entrega_id, // Mapeia `entregaId` para `entrega_id`
            itemPedido_id = itensEntrega.itemPedido_id, // Mapeia `itemPedidoId` para `ItemPedido_id`
            qtdEntregue = itensEntrega.qtdEntregue,
            qtdRetornado = itensEntrega.qtdRetornado
        )
    }
//    fun toItensEntrega(itensEntregaSupabase: ItensEntregaSupabase): ItensEntrega {
//        return ItensEntrega(
//            entrega_id = itensEntregaSupabase.entrega_id, // Mapeia `entrega_id` para `entregaId`
//            itemPedido_id = itensEntregaSupabase.itemPedido_id, // Mapeia `ItemPedido_id` para `itemPedidoId`
//            qtdEntregue = itensEntregaSupabase.qtdEntregue,
//            qtdRetornado = itensEntregaSupabase.qtdRetornado
//        )
//    }

    fun toItemEntrega(iteMEntregaSupabase: ItensEntregaSupabase): ProdutoEntregue {
        return ProdutoEntregue(
            qtdEntregue = iteMEntregaSupabase.qtdEntregue,
            qtdRetornado = iteMEntregaSupabase.qtdRetornado,
            produto_pedido_id = iteMEntregaSupabase.itemPedido_id
        )
    }

    fun toItemEntregaSupabase(produtoEntregue: ProdutoEntregue, entregaId: Long): ItensEntregaSupabase{
        return ItensEntregaSupabase(
            entrega_id = entregaId,
            itemPedido_id = produtoEntregue.produto_pedido_id,
            qtdEntregue = produtoEntregue.qtdEntregue,
            qtdRetornado = produtoEntregue.qtdRetornado
        )
    }

    fun toEntrega(entregaSupabase: EntregaSupabase): Entrega {
        return Entrega(
            id = entregaSupabase.id,
            data = entregaSupabase.data,
            entregador = entregaSupabase.entregador,
            freteId = entregaSupabase.frete_id
        )
    }
    fun toFreteSupabase(frete: Frete): FreteSupabase{
        return FreteSupabase(
            id = frete.id,
            pedido_id = frete.pedido_id,
            status = frete.status()
        )
    }
    fun toFrete(freteSupabase: FreteSupabase): Frete {
        return Frete(
            id = freteSupabase.id,
            pedido_id = freteSupabase.pedido_id,
            status = freteSupabase.status
        )
    }

    fun toDivida(dividaSupabase: DividaSupabase) =
        Divida(
            id = dividaSupabase.id,
            pedido_id = dividaSupabase.pedido_id,
            valor = dividaSupabase.valor_total,
        )
    fun toDividaSupabase(divida: Divida) =
        DividaSupabase(
            id = divida.id,
            pedido_id = divida.pedido_id,
            valor_total = divida.valor,
        )

    fun toPedido(pedidoSupabase: PedidoSupabase) = Pedido(
        id = Id(pedidoSupabase.id),
        cliente_id = Id(pedidoSupabase.cliente_id),
        dataPedido = pedidoSupabase.data,
        status = pedidoSupabase.status
    )

    fun toProdutoPedido(produtoPedidosupabase: ItensPedidoSupabase) = ProdutoPedido(
        produtoId = Id(produtoPedidosupabase.produto_id),
        pedidoId = Id(produtoPedidosupabase.pedido_id),
        qtd = produtoPedidosupabase.qtd
    )

}