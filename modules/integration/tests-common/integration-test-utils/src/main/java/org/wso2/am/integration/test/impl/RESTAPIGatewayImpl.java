/*
 * Copyright (c) 2019, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.am.integration.test.impl;

import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.wso2.am.integration.clients.gateway.ApiClient;
import org.wso2.am.integration.clients.gateway.ApiException;
import org.wso2.am.integration.clients.gateway.ApiResponse;
import org.wso2.am.integration.clients.gateway.api.DeployApiApi;
import org.wso2.am.integration.clients.gateway.api.GetApiArtifactApi;
import org.wso2.am.integration.clients.gateway.api.GetApiSequencesApi;
import org.wso2.am.integration.clients.gateway.api.UndeployApiApi;
import org.wso2.am.integration.clients.gateway.api.dto.DeployResponseDTO;
import org.wso2.am.integration.test.ClientAuthenticator;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

/**
 * This util class performs the actions related to APIDTOobjects.
 */
public class RESTAPIGatewayImpl {
    ApiClient apiStoreClient = new ApiClient();
    public static final String appName = "Integration_Test_App_Store";
    public static final String callBackURL = "test.com";
    public static final String tokenScope = "Production";
    public static final String appOwner = "admin";
    public static final String grantType = "password";
    public static final String username = "admin";
    public static final String password = "admin";
    public String storeURL;
    public String tenantDomain;
    public DeployApiApi deployApiApi = new DeployApiApi();
    public GetApiArtifactApi getApiArtifactApi = new GetApiArtifactApi();
    public GetApiSequencesApi getApiSequencesApi = new GetApiSequencesApi();
    public UndeployApiApi undeployApiApi = new UndeployApiApi();

    @Deprecated
    public RESTAPIGatewayImpl () {
        this(username, password, "", "https://localhost:9943");
    }

    public RESTAPIGatewayImpl (String username, String password, String tenantDomain, String storeURL) {
        // token/DCR of Store node itself will be used
        String tokenURL = storeURL + "oauth2/token";
        String dcrURL = storeURL + "client-registration/v0.17/register";
        String scopes = "openid apim:publish";

        String accessToken = ClientAuthenticator
                .getAccessToken(scopes, appName, callBackURL, tokenScope, appOwner, grantType, dcrURL, username,
                        password, tenantDomain, tokenURL);

        apiStoreClient.setDebugging(Boolean.valueOf(System.getProperty("okHttpLogs")));
        apiStoreClient.addDefaultHeader("Authorization", "Bearer " + accessToken);
        apiStoreClient.setBasePath(storeURL + "api/am/gateway/v1");
        apiStoreClient.setReadTimeout(600000);
        apiStoreClient.setConnectTimeout(600000);
        apiStoreClient.setWriteTimeout(600000);
        apiStoreClient.setDebugging(true);

        deployApiApi.setApiClient(apiStoreClient);
        undeployApiApi.setApiClient(apiStoreClient);
        getApiArtifactApi.setApiClient(apiStoreClient);
        getApiSequencesApi.setApiClient(apiStoreClient);
        this.storeURL = storeURL;
        this.tenantDomain = tenantDomain;
    }

    public ApiResponse<DeployResponseDTO> deployAPIInGateway(String apiName, String label, String apiId) throws
            ApiException {
        return deployApiApi.deployApiPostWithHttpInfo(apiName, label, apiId);
    }

    public ApiResponse<DeployResponseDTO> UnDeployAPIInGateway(String apiName, String label, String apiId) throws
            ApiException {
        return undeployApiApi.undeployApiPostWithHttpInfo(apiName, label, apiId);
    }

}
