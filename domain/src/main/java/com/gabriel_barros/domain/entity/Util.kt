package com.gabriel_barros.domain.entity

@JvmInline
value class Id(val id: Long) {
    init {
        require(id > 0){ "Id zero ou negativo"}
    }
    operator fun compareTo(other: Long): Int = this.id.compareTo(other)

}
