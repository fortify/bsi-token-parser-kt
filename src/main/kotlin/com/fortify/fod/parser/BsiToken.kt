package com.fortify.fod.parser

class BsiToken {

    var tenantId: Int = 0
    var tenantCode: String = ""
    var projectVersionId: Int = 0
    var assessmentTypeId: Int = 0

    var payloadType: String = "ANALYSIS_PAYLOAD"

    var scanPreferenceId: Int = 1
    var scanPreference: String = "Standard"

    var auditPreferenceId: Int = 1
    var auditPreference: String = "Manual"

    var includeThirdParty: Boolean = false
    var includeOpenSourceAnalysis: Boolean = false

    var portalUri: String = "https://ams.fortify.com"
    var apiUri: String = "https://api.ams.fortify.com"

    var technologyTypeId: Int = 0
    var technologyType: String? = null

    var technologyVersion: String? = null
    var technologyVersionId: Int? = null

    // Legacy aliases
    var technologyStack: String?
        get() { return technologyType }
        set(value) { this.technologyType = value }

    var languageLevel: String?
        get() { return technologyVersion }
        set(value) { this.technologyVersion = value }
}
