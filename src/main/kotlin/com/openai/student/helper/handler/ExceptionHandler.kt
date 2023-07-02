package com.openai.student.helper.handler

import com.openai.student.helper.infra.exceptions.InvalidFileTypeException
import com.openai.student.helper.infra.exceptions.UnauthorizedException
import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.multipart.support.MissingServletRequestPartException
import java.time.LocalDateTime.*


@ControllerAdvice
class ExceptionHandler {

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(
        ConstraintViolationException::class,
        ValidationException::class,
        MissingServletRequestPartException::class,
        InvalidFileTypeException::class)
    fun badRequestExceptionHandler(exception: Exception): ResponseEntity<ErrorApiDTO> {
        val error = ErrorApiDTO(
            exception.toString(),
            "${now()}")

        return ResponseEntity.badRequest().body(error)
    }

    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException::class)
    fun authenticationExceptionHandler(exception: Exception): ResponseEntity<ErrorApiDTO> {
        val error = ErrorApiDTO(
            exception.toString(),
            "${now()}")

        return ResponseEntity.status(UNAUTHORIZED).body(error)
    }
}