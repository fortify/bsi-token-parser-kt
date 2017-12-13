/*
 * (c) Copyright 2017 EntIT Software LLC
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
            if (uri.port > 0) {
                token.apiUri = "${token.apiUri}:${uri.port}"
            }
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

        val token = BsiToken()

        token.tenantId = json.int("tenantId") ?: throw NullPointerException("Tenant Id can not be null.")
        token.tenantCode = json.string("tenantCode") ?: throw NullPointerException("Tenant Code can not be null.")
        token.projectVersionId = json.int("releaseId") ?: throw NullPointerException("Project Version Id can not be null.")
        token.payloadType = json.string("payloadType") ?: throw NullPointerException("Payload Type can not be null.")
        token.assessmentTypeId = json.int("assessmentTypeId") ?: throw NullPointerException("Assessment Type Id can not be null.")
        token.technologyType = json.string("technologyType") ?: throw NullPointerException("Technology Type can not be null.")
        token.technologyTypeId = json.int("technologyTypeId") ?: throw NullPointerException("Technology Type Id can not be null.")
        token.technologyVersion = json.string("technologyVersion")
        token.technologyVersionId = json.int("technologyVersionId")

        token.scanPreferenceId = if(json.int("scanPreferenceId") == 0) token.scanPreferenceId else json.int("scanPreferenceId")
                ?: throw NullPointerException("Scan Preference Id can not be null")

        token.scanPreference = if(json.string("scanPreference") == "0") token.scanPreference else json.string("scanPreference")
                ?: throw NullPointerException("Scan Preference Id can not be null")

        token.includeThirdParty = json.boolean("includeThirdParty") ?: throw NullPointerException("Include Third Party Flag can not be null.")
        token.includeOpenSourceAnalysis = json.boolean("includeOpenSourceAnalysis") ?: throw NullPointerException("Include Open Source Flag can not be null.")
        token.auditPreferenceId = json.int("auditPreferenceId") ?: throw NullPointerException("Audit Preference Id can not be null.")

        token.auditPreference = if (json.string("auditPreference") == "") token.auditPreference else json.string("auditPreference")
                ?: throw NullPointerException("Audit Preference can not be null.")

        token.apiUri = if (json.string("apiUri") == "") token.apiUri else json.string("apiUri")
                ?: throw NullPointerException("API URI can not be null")

        token.portalUri = if (json.string("portalUri") == "") token.portalUri else json.string("portalUri")
                ?: throw NullPointerException("Portal URI can not be null")

        return token
    }

}
