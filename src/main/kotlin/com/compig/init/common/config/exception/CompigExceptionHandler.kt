package com.compig.init.common.config.exception

import com.compig.init.common.config.logger
import com.compig.init.common.entity.ErrorResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import java.time.LocalDateTime

@Slf4j
@RestControllerAdvice
class CompigExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    fun coldbrewHandler(e: Exception, er: WebRequest): ResponseEntity<ErrorResponse> {
        logPrint(e)
        val httpStatus = httpStatus(e)
        return ResponseEntity<ErrorResponse>(
            ErrorResponse(code = "COLD-EXC-" + httpStatus.value(),
                message = httpStatus.reasonPhrase,
                timestamp = LocalDateTime.now(),
                path = (er as ServletWebRequest).request.requestURI),
            httpStatus)
    }
    //TODO Error enum 추가하기
    //TODO gradle multi module
    //TODO ummmmmmmmmmmmmmm..

    private fun logPrint(e: Exception) {
        logger().error("error file : " + e.stackTrace[0].fileName)
        logger().error("error line : " + e.stackTrace[0].lineNumber)
        logger().error("error message : " + e.message)
    }

    private fun httpStatus(e: Exception) = when (e) {
        is HttpRequestMethodNotSupportedException -> {
            HttpStatus.METHOD_NOT_ALLOWED
        }
        is HttpMediaTypeNotSupportedException -> {
            HttpStatus.UNSUPPORTED_MEDIA_TYPE
        }
        is HttpMediaTypeNotAcceptableException -> {
            HttpStatus.NOT_ACCEPTABLE
        }
        is MissingPathVariableException -> {
            HttpStatus.INTERNAL_SERVER_ERROR
        }
        is MissingServletRequestParameterException -> {
            HttpStatus.BAD_REQUEST
        }
        is ServletRequestBindingException -> {
            HttpStatus.BAD_REQUEST
        }
        is ConversionNotSupportedException -> {
            HttpStatus.INTERNAL_SERVER_ERROR
        }
        is TypeMismatchException -> {
            HttpStatus.BAD_REQUEST
        }
        is HttpMessageNotReadableException -> {
            HttpStatus.BAD_REQUEST
        }
        is HttpMessageNotWritableException -> {
            HttpStatus.INTERNAL_SERVER_ERROR
        }
        is MethodArgumentNotValidException -> {
            HttpStatus.BAD_REQUEST
        }
        is MissingServletRequestPartException -> {
            HttpStatus.BAD_REQUEST
        }
        is BindException -> {
            HttpStatus.BAD_REQUEST
        }
        is NoHandlerFoundException -> {
            HttpStatus.NOT_FOUND
        }
        is AsyncRequestTimeoutException -> {
            HttpStatus.SERVICE_UNAVAILABLE
        }
        else -> {
            // Unknown exception, typically a wrapper with a common MVC exception as cause
            // (since @ExceptionHandler type declarations also match first-level causes):
            // We only deal with top-level MVC exceptions here, so let's rethrow the given
            // exception for further processing through the HandlerExceptionResolver chain.
            HttpStatus.INTERNAL_SERVER_ERROR
        }
    }

}