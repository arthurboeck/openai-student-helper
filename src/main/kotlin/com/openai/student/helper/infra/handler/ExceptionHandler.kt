package com.openai.student.helper.infra.handler

import com.openai.student.helper.infra.exceptions.InvalidFileTypeException
import com.openai.student.helper.infra.exceptions.NotFoundException
import com.openai.student.helper.infra.exceptions.UnauthorizedException
import feign.FeignException
import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.support.MissingServletRequestPartException
import java.time.LocalDateTime.*
import javax.naming.ServiceUnavailableException


@ControllerAdvice
class ExceptionHandler {

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(
            ConstraintViolationException::class,
            ValidationException::class,
            MissingServletRequestPartException::class,
            InvalidFileTypeException::class
    )
    fun badRequestExceptionHandler(exception: Exception): ErrorApiDTO {
        return ErrorApiDTO(
                exception.toString(),
                "${now()}"
        )
    }

    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(
            UnauthorizedException::class,
            FeignException.Unauthorized::class
    )
    fun authenticationExceptionHandler(exception: Exception): ErrorApiDTO {
        return ErrorApiDTO(
                exception.toString(),
                "${now()}"
        )
    }

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun notFoundExceptionHandler(exception: Exception): ErrorApiDTO {
        return ErrorApiDTO(
                exception.toString(),
                "${now()}"
        )
    }

    @ResponseBody
    @ResponseStatus(SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceUnavailableException::class)
    fun serviceUnavailableExceptionHandler(exception: Exception): ErrorApiDTO {
        return ErrorApiDTO(
                exception.message.toString(),
                "${now()}"
        )
    }
}