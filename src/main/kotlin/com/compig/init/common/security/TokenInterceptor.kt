package com.compig.init.common.security

import com.compig.init.common.config.logger
import lombok.extern.slf4j.Slf4j
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.springframework.security.jwt.JwtHelper
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
class TokenInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val authorization = request.getHeader("Authorization")
        if (authorization != null && authorization.contains("Bearer")) {
            val jwttoken = authorization.split("Bearer".toRegex()).toTypedArray()[1]
            val jwt = JwtHelper.decode(jwttoken.trim { it <= ' ' })
            val claims = jwt.claims
            val json = strToJson(claims) as JSONObject
            request.session.setAttribute("userSeqId", json["userSeqId"])
            request.session.setAttribute("userId", json["userId"])
            request.session.setAttribute("roleName", json["roleName"])
        }
        return true
    }

    /*
   * 문자을 json으로 변환
   * */
    fun strToJson(str: String?): JSONObject {
        val parser = JSONParser()
        var obj = Any()
        try {
            obj = parser.parse(str)
        } catch (e: ParseException) {
            logger().error(e.toString())
        }
        return obj as JSONObject
    }
}