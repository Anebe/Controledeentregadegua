package com.gabriel_barros.domain.error

import java.security.InvalidParameterException

class InvalidDataException(message: String) : InvalidParameterException(message)
class NotFoundException(message: String) : RuntimeException(message)
class BadRequestException(message: String) : RuntimeException(message)
