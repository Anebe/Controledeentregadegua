package com.gabriel_barros.controle_entregua_agua.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.ClienteDAO
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.Cliente
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PrincipalViewModel : ViewModel() {
    private val clienteRepo = ClienteDAO(SupabaseClientProvider.supabase)

    private val _clientesMap = MutableStateFlow<Map<String, Long>>(emptyMap())
    val clientesMap: StateFlow<Map<String, Long>> get() = _clientesMap

    fun carregarClientes() {
        viewModelScope.launch {
            val clientes = clienteRepo.getAllClientes()
            val clienteMap = clientes.flatMap { cliente ->
                listOf(cliente.nome to cliente.id) + cliente.apelidos.map { it to cliente.id }
            }.toMap()
            _clientesMap.value = clienteMap // Atualiza o StateFlow
        }
    }
}