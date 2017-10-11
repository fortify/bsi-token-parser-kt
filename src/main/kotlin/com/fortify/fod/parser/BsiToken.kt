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
}
