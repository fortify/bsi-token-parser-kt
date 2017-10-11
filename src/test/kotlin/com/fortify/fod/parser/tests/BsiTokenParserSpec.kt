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

        val bsiUrl1 = "/bsi2.aspx?tid=1&tc=Tenant1&pv=332&payloadType=ANALYSIS_PAYLOAD&astid=7&ts=ABAP"

        on("parsing $bsiUrl1") {
            val token = parser.parse(bsiUrl1)

            it("should have a Tenant Code of 'Tenant1'") {
                assertEquals("Tenant1", token.tenantCode)
            }

            it("should have a Tenant ID of '1'") {
                assertEquals(1, token.tenantId)
            }

            it("should have a Tech Stack of 'ABAP'") {
                assertEquals("ABAP", token.technologyStack)
            }

            it("should have a Project Version ID of '332'") {
                assertEquals(332, token.projectVersionId)
            }

            it("should have an Assessment Type ID of '7'") {
                assertEquals(7, token.assessmentTypeId)
            }

            it("should have API URI of 'https://api.ams.fortify.com'") {
                assertEquals("https://api.ams.fortify.com", token.apiUri)
            }
        }

        val bsiUrl2 = "https://api.ams.fortify.com/bsi2.aspx?tid=1&tc=Tenant1&pv=107499&payloadType=ANALYSIS_PAYLOAD&astid=170&ts=JAVA%2fJ2EE&ll=1.8"

        on("parsing $bsiUrl2") {
            val token = parser.parse(bsiUrl2)

            it("should have a Tech Stack of 'JAVA/J2EE'") {
                assertEquals("JAVA/J2EE", token.technologyStack)
            }

            it("should have a Language Level of '1.8'") {
                assertEquals("1.8", token.languageLevel)
            }

            it("should have API URI of 'https://api.ams.fortify.com'") {
                assertEquals("https://api.ams.fortify.com", token.apiUri)
            }

        }

        val bsiUrl3 = "https://api.ams.fortify.com/bsi2.aspx?tid=1&tc=Tenant1&pv=107499&payloadType=ANALYSIS_PAYLOAD&astid=170&ts=MBS%2fC%2fC%2b%2b"

        on("parsing $bsiUrl3") {
            val token = parser.parse(bsiUrl3)

            it("should have a Tech Stack of 'MBS/C/C++'") {
                assertEquals("MBS/C/C++", token.technologyStack)
            }
        }
    }
})
