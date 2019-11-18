package com.xyleme.bravais.api;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.xyleme.bravais.api.endpointsdata.AuthenticateEndpointData;

import java.util.Map;

import static com.xyleme.bravais.web.WebPage.ENVIRONMENT;

/**
 * Implementation of CDS API helper utility.
 */
public class CDSApiHelper {
    private static String contextHeaderName;
    private static String contextHeaderValue;
    private static final String baseCDSCoreURL = ENVIRONMENT.env.get("CDS_CORE_URL");

    /**
     * Gets name and value of authorization header.
     */
    private static void getBravaisContext() {
        AuthenticateEndpointData authenticationData = new AuthenticateEndpointData();
        authenticationData.setDataForAuthenticationEndPoint(ENVIRONMENT.env.get("username"), ENVIRONMENT.env.get("password"));

        Response authResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(authenticationData)
                .post(baseCDSCoreURL + "/api/v3/authenticate");

        int authResponseStatusCode = authResponse.statusCode();

        if (authResponseStatusCode == 200) {
            contextHeaderName = authResponse.jsonPath().get("name");
            contextHeaderValue = authResponse.jsonPath().get("value").toString();
        } else {
            throw new RuntimeException("Status code of the authentication response is not 200. The method cannot proceed." +
                    "\nStatus code of the response: " + authResponseStatusCode);
        }
    }

    /**
     * Gets direct URL for downloading specified document.
     *
     * @param documentId - Specifies document id
     * @return {@code String}
     */
    public static String getDownloadingURLOfDocument(String documentId) {
        getBravaisContext();
        assert contextHeaderName != null;
        assert contextHeaderValue != null;

        Response responseA = RestAssured
                .given()
                .header(contextHeaderName, contextHeaderValue)
                .get(baseCDSCoreURL + "/api/v3/documents/" + documentId + "/versions/latest");

        int responseAStatusCode = responseA.statusCode();

        if (responseAStatusCode == 200) {
            Map<String, String> cookiesOfResponseA = responseA.getCookies();
            String versionId = responseA.jsonPath().get("id").toString();
            String fileId = responseA.jsonPath().get("fileId").toString();

            Response responseB = RestAssured
                    .given()
                    .cookies(cookiesOfResponseA)
                    .get(baseCDSCoreURL + "/api/dynamic/documentVersions/" + versionId + "/files/" + fileId + "/info");

            int responseBStatusCode = responseB.statusCode();

            if (responseBStatusCode == 200) {
                return responseB.jsonPath().get("fileUrl");
            } else {
                throw new RuntimeException("URL for downloading document with id '" + documentId + "' cannot be " +
                        "retrieved from a respective request. Status code of the response: " + responseAStatusCode);
            }
        } else {
            throw new RuntimeException("'versionId' and 'fileId' values cannot be retrieved from the respective request. " +
                    "Status code of the response: " + responseAStatusCode);
        }
    }
}