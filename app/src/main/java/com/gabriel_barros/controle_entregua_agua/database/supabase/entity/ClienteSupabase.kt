package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import androidx.compose.ui.platform.ClipEntry
import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import kotlinx.serialization.Serializable

@Serializable
data class ClienteSupabase(
    val id: Long = 0,
    val nome: String /*= ""*/,
    val credito: Double /*= 0.0*/,
    val apelidos: List<String> /*= arrayListOf()*/,
    val descricao: String /*= ""*/,
//    val enderecos: List<EnderecoSupabase> /*= arrayListOf()*/,
){
//    companion object{
//        fun to(cliente: ClienteSupabase):Cliente{
//            return Cliente(
//                id = cliente.id,
//                        nome = cliente.nome,
//                        credito = cliente.credito,
//                        apelidos = cliente.apelidos,
//                        descricao = cliente.descricao,
//                        enderecos = cliente.enderecos.map{EnderecoSupabase.to(it)},
//            )
//        }
//        fun to(cliente: Cliente): ClienteSupabase{
//            return ClienteSupabase(
//                id = cliente.id,
//                        nome = cliente.nome,
//                        credito = cliente.credito,
//                        apelidos = cliente.apelidos,
//                        descricao = cliente.descricao,
//                        enderecos = cliente.enderecos.map { EnderecoSupabase.to(it) },
//            )
//        }
//
//    }
}
