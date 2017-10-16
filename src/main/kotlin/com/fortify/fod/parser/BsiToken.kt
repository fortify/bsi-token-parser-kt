package com.fortify.fod.parser

class BsiToken {
    var tenantId: Int = 0
    var tenantCode: String? = null
    var projectVersionId: Int = 0
    var payloadType: String? = null
    var assessmentTypeId: Int = 0
    var technologyStack: String? = null
    var languageLevel = ""
    var apiUri: String = "https://api.ams.fortify.com"
    var scanPreferenceId: Int = 0
    var scanPreference: String? = null
    var includeThirdParty: Boolean = false
    var auditPreference: String? = "manual"
    var auditPreferenceId: Int = 0
    var includeOpenSourceAnalysis: Boolean = false
    var portalUri: String = "https://ams.fortify.com"

    var technologyType: String? = null
    var technologyTypeId: Int = 0
    var technologyVersion: String? = null
    var technologyVersionId: Int = 0

}

