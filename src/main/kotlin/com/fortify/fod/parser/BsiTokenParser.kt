package com.fortify.fod.parser

import org.apache.http.client.utils.URLEncodedUtils
import java.io.UnsupportedEncodingException
import java.net.URI
import java.net.URISyntaxException
import com.beust.klaxon.valueToString
import com.beust.klaxon.JsonObject
import com.beust.klaxon.json
import com.beust.klaxon.Parser
import com.beust.klaxon.int
import com.beust.klaxon.string
import com.beust.klaxon.boolean

class BsiTokenParser {
    @Throws(URISyntaxException::class, UnsupportedEncodingException::class)
    fun parse(token: String): BsiToken {

        val trimmedToken = token.trim()

        if (trimmedToken.contains("/bsi2.aspx?")) {
            val uri = URI(trimmedToken)
            return parseBsiUrl(uri)
        } else {
           return parseBsiToken(trimmedToken)
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

/*                if(token.technologyVersion != null){
                    "ll" -> token.technologyVersion = param.value
                }*/


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

        token.tenantId = json.int("tenantId")
        token.tenantCode = json.string("tenantCode")
        token.projectVersionId= json.int("releaseId")
        token.payloadType = json.string("payloadType")
        token.assessmentTypeId = json.int("assessmentTypeId")
        token.technologyType = json.string("technologyType")
        token.technologyTypeId = json.int("technologyTypeId")
        token.technologyVersion = json.string("technologyVersion")
        token.technologyVersionId = json.int("technologyVersionId")
        //TODO: not in the bsi token
        token.languageLevel = json.string("languageLevel")
        token.apiUri = json.string("apiUri")
        token.scanPreferenceId = json.int("scanPreferenceId")
        token.scanPreference = json.string("scanPreference")
        token.includeThirdParty = json.boolean("includeThirdParty")
        token.auditPreference = json.string("auditPreference")
        token.auditPreferenceId = json.int("auditPreferenceId")
        token.includeOpenSourceAnalysis = json.boolean("includeOpenSourceAnalysis")
        token.portalUri = json.string("portalUri")

        return token
    }
}
