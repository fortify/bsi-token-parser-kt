package com.fortify.fod.parser.tests

import com.fortify.fod.parser.BsiTokenParser

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

object BsiTokenParserSpec : Spek({
    given("a BsiTokenParser") {

        val parser = BsiTokenParser()

        on("parsing /bsi2.aspx?tid=1&tc=Tenant1&pv=332&payloadType=ANALYSIS_PAYLOAD&astid=7&ts=ABAP") {
            val token = parser.parse("/bsi2.aspx?tid=1&tc=Tenant1&pv=332&payloadType=ANALYSIS_PAYLOAD&astid=7&ts=ABAP")

            it("should have a Tenant Code of 'Tenant1'") {
                assertEquals("Tenant1", token.tenantCode)
            }
        }
    }
})
