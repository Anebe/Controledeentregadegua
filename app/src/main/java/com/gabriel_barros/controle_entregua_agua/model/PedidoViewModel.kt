package com.gabriel_barros.controle_entregua_agua.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabriel_barros.controle_entregua_agua.model.Cliente
import com.gabriel_barros.controle_entregua_agua.model.Pedido
import com.gabriel_barros.controle_entregua_agua.model.SimpleRepository
import com.gabriel_barros.controle_entregua_agua.ui.pedido.PedidoItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PedidoViewModel : ViewModel() {
    private val repository: SimpleRepository = SimpleRepository()

    fun add(pedidos:List<PedidoItemState>) {
        viewModelScope.launch {
            pedidos.forEach {
                repository.adicionar(it.to())
            }
        }
    }
}