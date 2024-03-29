package com.whisper.transmit.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(TException::class)
    fun handleTException(ex: TException): ResponseEntity<*> {
        return ResponseEntity.status(ex.status).body(mapOf("error" to ex.message))
    }

    // Handle MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(ex: MethodArgumentNotValidException): ResponseEntity<*>? {
        val errorsWrapper: MutableMap<String, Any> = HashMap()
        val errors: MutableMap<String, String?> = HashMap()
        val bindingResult = ex.bindingResult
        if (bindingResult.hasErrors()) {
            val fieldErrorList = bindingResult.fieldErrors
            for (fieldError in fieldErrorList) {
                errors[fieldError.field] = fieldError.defaultMessage
            }
        }
        errorsWrapper["errors"] = errors
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body<Map<String, Any>>(errorsWrapper)
    }
}