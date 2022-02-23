package com.compig.init.common.exception

import com.compig.init.common.config.logger
import com.compig.init.common.exception.dto.ErrorResponse
import com.compig.init.common.exception.dto.ErrorResponse.Companion.of
import com.compig.init.common.exception.exceptions.InvalidInputValueException
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.function.Consumer

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [GlobalException::class])
    fun globalTransHandler(e: GlobalException, er: WebRequest): ResponseEntity<ErrorResponse> {
        logPrint(e)
        return ResponseEntity<ErrorResponse>(of(e, er), e.httpStatus)
    }

    @ExceptionHandler(value = [Exception::class])
    fun globalHandler(e: Exception, er: WebRequest): ResponseEntity<ErrorResponse> {
        logPrint(e)
        return ResponseEntity<ErrorResponse>(of(e, er), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    //TODO gradle multi module
    //TODO restDocs

    private fun logPrint(e: Exception) {
        logger().error("error file : " + e.stackTrace[0].fileName)
        logger().error("error line : " + e.stackTrace[0].lineNumber)
        logger().error("error message : " + e.message)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        logPrint(ex)
        val errorResponse: ErrorResponse = of(InvalidInputValueException.EXCEPTION, request)
        ex.bindingResult.globalErrors
            .forEach(Consumer { e: ObjectError ->
                errorResponse.addDetail(e.objectName,
                    e.defaultMessage)
            })
        ex.bindingResult.fieldErrors
            .forEach(Consumer { e: FieldError ->
                errorResponse.addDetail(e.field,
                    e.defaultMessage)
            })
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request)
    }

    override fun handleBindException(
        ex: BindException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        logPrint(ex)
        val errorResponse: ErrorResponse = of(ex, request)
        ex.bindingResult.fieldErrors
            .forEach(Consumer { error: FieldError ->
                val target = error.field
                val message = error.defaultMessage
                errorResponse.addDetail(target, message)
            })

        return super.handleExceptionInternal(ex, errorResponse, headers, status, request)
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        logPrint(ex)
        val errorResponse: ErrorResponse = of(ex, request)
        val target = ex.value as String
        val message = "잘못된 형식의 값을 입력하였습니다."
        errorResponse.addDetail(target, message)
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request)
    }

}