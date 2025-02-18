package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import com.gabriel_barros.controle_entregua_agua.domain.entity.Endereco
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import kotlinx.serialization.Serializable

@Serializable
data class EnderecoSupabase (
    val id: Long = 0,
    val cliente_id: Long,
    val cep: String,
    val rua: String,
    val bairro: String,
    val complemento: String,
    val numero: String
){
    companion object{
        fun to(endereco: EnderecoSupabase): Endereco {
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

        fun to(endereco: Endereco): EnderecoSupabase {
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
    }
}
