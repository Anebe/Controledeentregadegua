package com.gabriel_barros.controle_entregua_agua.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabriel_barros.controle_entregua_agua.ui.principal.PedidoItemState
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