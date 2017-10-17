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


        val bsiToken1 = "eyJ0ZW5hbnRJZCI6MSwidGVuYW50Q29kZSI6IlRlbmFudDEiLCJyZWxlYXNlSWQiOjYsInBheWxvYWRUeXBlIjoiQU5BTFlTSVNfUEFZTE9BRCIsImFzc2Vzc21lbnRUeXBlSWQiOjgsInRlY2hub2xvZ3lUeXBlIjoiX05FVCIsInRlY2hub2xvZ3lUeXBlSWQiOjEsInRlY2hub2xvZ3lWZXJzaW9uIjoiXzRfMCIsInRlY2hub2xvZ3lWZXJzaW9uSWQiOjUsImF1ZGl0UHJlZmVyZW5jZSI6Ik1hbnVhbCIsImF1ZGl0UHJlZmVyZW5jZUlkIjoxLCJpbmNsdWRlVGhpcmRQYXJ0eSI6ZmFsc2UsImluY2x1ZGVPcGVuU291cmNlQW5hbHlzaXMiOmZhbHNlLCJzY2FuUHJlZmVyZW5jZSI6IlN0YW5kYXJkIiwic2NhblByZWZlcmVuY2VJZCI6MSwicG9ydGFsVXJpIjoiaHR0cDovL2ZvZC5sb2NhbGhvc3QiLCJhcGlVcmkiOiIifQ"

        on("parsing $bsiToken1") {
            val token = parser.parse(bsiToken1)

            it("should have a Tenant Code of 'Tenant1'") {
                assertEquals("Tenant1", token.tenantCode)
            }
            it("should have a Project Version ID of '6'") {
               assertEquals(6, token.projectVersionId)
            }

            it("should have a payloadType of 'ANALYSIS_PAYLOAD'") {
               assertEquals("ANALYSIS_PAYLOAD", token.payloadType)
            }

            it("should have a portalUri of 'http://fod.localhost'") {
                assertEquals("http://fod.localhost", token.portalUri)
            }

           it("should have a apiUri of 'https://api.ams.fortify.com'") {
                assertEquals("https://api.ams.fortify.com", token.apiUri)
            }

        }
        val bsiToken2 = "eyJ0ZW5hbnRJZCI6MSwidGVuYW50Q29kZSI6IlRlbmFudDEiLCJyZWxlYXNlSWQiOjYsInBheWxvYWRUeXBlIjoiQU5BTFlTSVNfUEFZTE9BRCIsImFzc2Vzc21lbnRUeXBlSWQiOjgsInRlY2hub2xvZ3lUeXBlIjoiSkFWQV9KMkVFIiwidGVjaG5vbG9neVR5cGVJZCI6NywidGVjaG5vbG9neVZlcnNpb24iOiJfMV84IiwidGVjaG5vbG9neVZlcnNpb25JZCI6MTIsImF1ZGl0UHJlZmVyZW5jZSI6Ik1hbnVhbCIsImF1ZGl0UHJlZmVyZW5jZUlkIjoxLCJpbmNsdWRlVGhpcmRQYXJ0eSI6ZmFsc2UsImluY2x1ZGVPcGVuU291cmNlQW5hbHlzaXMiOmZhbHNlLCJzY2FuUHJlZmVyZW5jZSI6IlN0YW5kYXJkIiwic2NhblByZWZlcmVuY2VJZCI6MSwicG9ydGFsVXJpIjoiaHR0cDovL2ZvZC5sb2NhbGhvc3QiLCJhcGlVcmkiOiIifQ"

        on("parsing $bsiToken2") {
            val token = parser.parse(bsiToken2)

            it("should have an Assessment Type Id of "){
                assertEquals(8, token.assessmentTypeId)
            }

            it("should have a Technology Type of 'JAVA_J2EE'") {
                assertEquals("JAVA_J2EE", token.technologyType)
            }
            it("should have a Technology Version of '_1_8'") {
                assertEquals("_1_8", token.technologyVersion)
            }

            it("should have includeThirdParty be 'false'") {
                assertEquals(false, token.includeThirdParty)
            }

            it("should have a Scan Preference of 'Standard'") {
                assertEquals("Standard", token.scanPreference)
            }
        }

        val bsiToken3 = "eyJ0ZW5hbnRJZCI6MSwidGVuYW50Q29kZSI6IlRlbmFudDEiLCJyZWxlYXNlSWQiOjYsInBheWxvYWRUeXBlIjoiQU5BTFlTSVNfUEFZTE9BRCIsImFzc2Vzc21lbnRUeXBlSWQiOjgsInRlY2hub2xvZ3lUeXBlIjoiSkFWQV9KMkVFIiwidGVjaG5vbG9neVR5cGVJZCI6NywidGVjaG5vbG9neVZlcnNpb24iOiJfMV84IiwidGVjaG5vbG9neVZlcnNpb25JZCI6MTIsImF1ZGl0UHJlZmVyZW5jZSI6IiIsImF1ZGl0UHJlZmVyZW5jZUlkIjoxLCJpbmNsdWRlVGhpcmRQYXJ0eSI6ZmFsc2UsImluY2x1ZGVPcGVuU291cmNlQW5hbHlzaXMiOmZhbHNlLCJzY2FuUHJlZmVyZW5jZSI6IlN0YW5kYXJkIiwic2NhblByZWZlcmVuY2VJZCI6MSwicG9ydGFsVXJpIjoiIiwiYXBpVXJpIjoiIn0="

        on("parsing $bsiToken3") {
            val token = parser.parse(bsiToken3)

            it("should have an Audit Preference of 'manual'") {
                assertEquals("manual", token.auditPreference)
            }

            it("should have a portalUri of 'http://fod.localhost'") {
                assertEquals("https://ams.fortify.com", token.portalUri)
            }

            it("should have a apiUri of 'https://api.ams.fortify.com'") {
                assertEquals("https://api.ams.fortify.com", token.apiUri)
            }
        }
    }
})

