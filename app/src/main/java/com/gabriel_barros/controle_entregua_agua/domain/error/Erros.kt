package com.gabriel_barros.controle_entregua_agua.domain.error

import java.security.InvalidParameterException

class InvalidDataException(message: String) : InvalidParameterException(message)
class NotFoundException(message: String) : RuntimeException(message)
class BadRequestException(message: String) : RuntimeException(message)
