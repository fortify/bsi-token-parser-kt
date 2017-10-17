package com.fortify.fod.parser

import org.apache.http.client.utils.URLEncodedUtils
import java.io.UnsupportedEncodingException
import java.net.URI
import java.net.URISyntaxException
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.int
import com.beust.klaxon.string
import com.beust.klaxon.boolean

class BsiTokenParser {
    @Throws(URISyntaxException::class, UnsupportedEncodingException::class)
    fun parse(token: String): BsiToken {

        val trimmedToken = token.trim()

        return if (trimmedToken.contains("/bsi2.aspx?")) {
            val uri = URI(trimmedToken)
            parseBsiUrl(uri)
        } else {
            parseBsiToken(trimmedToken)
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
                "ap" -> token.auditPreference = param.value
            }
        }

        return token
    }

    private fun parseBsiToken(codedToken: String): BsiToken {

        val bsiBytes = java.util.Base64.getDecoder().decode(codedToken)
        val decodedToken = String(bsiBytes)

        val parser = Parser()
        val stringBuilder = StringBuilder(decodedToken)
        val json = parser.parse(stringBuilder) as JsonObject

        var token = BsiToken()

        token.tenantId = json.int("tenantId") ?: throw NullPointerException()
        token.tenantCode = json.string("tenantCode")
        token.projectVersionId = json.int("releaseId") ?: throw NullPointerException()
        token.payloadType = json.string("payloadType") ?: throw NullPointerException()
        token.assessmentTypeId = json.int("assessmentTypeId") ?: throw NullPointerException()
        token.technologyType = json.string("technologyType") ?: throw NullPointerException()
        token.technologyTypeId = json.int("technologyTypeId") ?: throw NullPointerException()
        token.technologyVersion = json.string("technologyVersion")
        token.technologyVersionId = json.int("technologyVersionId") ?: throw NullPointerException()
        //Same as TechnologyVersion
        token.languageLevel = json.string("technologyVersion")
        token.scanPreferenceId = json.int("scanPreferenceId") ?: throw NullPointerException()
        token.scanPreference = json.string("scanPreference")
        token.includeThirdParty = json.boolean("includeThirdParty") ?: throw NullPointerException()
        token.auditPreferenceId = json.int("auditPreferenceId") ?: throw NullPointerException()
        token.includeOpenSourceAnalysis = json.boolean("includeOpenSourceAnalysis") ?: throw NullPointerException()

        token.auditPreference = if (json.string("auditPreference") == "") token.auditPreference else json.string("auditPreference")
        token.apiUri = (if (json.string("apiUri") == "") token.apiUri else json.string("apiUri")) ?: throw NullPointerException()
        token.portalUri = (if (json.string("portalUri") == "") token.portalUri else json.string("portalUri")) ?: throw NullPointerException()


        return token
    }

}
