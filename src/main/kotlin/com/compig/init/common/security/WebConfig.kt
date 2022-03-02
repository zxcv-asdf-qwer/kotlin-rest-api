package com.compig.init.common.security

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.charset.StandardCharsets
import java.util.*

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    //인터셉터 구현
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(TokenInterceptor() as HandlerInterceptor)
    }

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>?>) {
        val converter = StringHttpMessageConverter(StandardCharsets.UTF_8)
        val mediaTypes: MutableList<MediaType> = ArrayList()
        mediaTypes.add(MediaType.APPLICATION_JSON)
        //이거 설정 안하면 Accept-Charset에 대다수의 Encoding Type 리턴함
        converter.setWriteAcceptCharset(false)
        converter.supportedMediaTypes = mediaTypes
        converters.add(converter)
    }

}