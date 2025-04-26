package com.gabriel_barros.domain.domain.error

import java.security.InvalidParameterException

class InvalidDataException(message: String) : InvalidParameterException(message)
class NotFoundException(message: String) : RuntimeException(message)
class BadRequestException(message: String) : RuntimeException(message)
