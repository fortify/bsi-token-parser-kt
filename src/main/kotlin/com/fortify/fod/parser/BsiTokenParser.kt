package com.fortify.fod.parser

import org.apache.http.client.utils.URLEncodedUtils
import java.io.UnsupportedEncodingException
import java.net.URI
import java.net.URISyntaxException
import java.net.URLEncoder

class BsiTokenParser {
    @Throws(URISyntaxException::class, UnsupportedEncodingException::class)
    fun parse(token: String): BsiToken {

        val trimmedToken = token.trim()

        if (trimmedToken.contains("/bsi2.aspx?")) {
            val uri = URI(trimmedToken)
            return parseBsiUrl(uri)
        } else {
            throw Exception("Not implemented")
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun parseBsiUrl(uri: URI): BsiToken {

        val params = URLEncodedUtils.parse(uri, "UTF-8")
        val token = BsiToken()

        if (uri.scheme != null && uri.host != null) {
            token.apiUri = "${uri.scheme}://${uri.host}"
        }

        for (param in params) {
            when (param.name) {
                "tid" -> token.tenantId = Integer.parseInt(param.value)
                "tc" -> token.tenantCode = param.value
                "pv" -> token.projectVersionId = Integer.parseInt(param.value)
                "ts" -> token.technologyStack = param.value
                "ll" -> token.languageLevel = param.value
                "astid" -> token.assessmentTypeId = Integer.parseInt(param.value)
                "payloadType" -> token.payloadType = param.value
            }
        }

        return token
    }
}
