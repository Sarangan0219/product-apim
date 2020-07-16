package org.wso2.am.integration.test.impl;


//import org.wso2.am.integration.clients.gateway.ApiClient;
//import org.wso2.am.integration.clients.gateway.api.DeployApiApi;
//import org.wso2.am.integration.clients.gateway.api.GetApiArtifactApi;
//import org.wso2.am.integration.clients.gateway.api.GetApiSequencesApi;
//import org.wso2.am.integration.clients.gateway.api.UndeployApiApi;
import org.wso2.am.integration.test.ClientAuthenticator;

public class RESTAPIGatewayImpl {

    public static final String appName = "Integration_Test_App_Store";
    public static final String callBackURL = "test.com";
    public static final String tokenScope = "Production";
    public static final String appOwner = "admin";
    public static final String grantType = "password";
    public static final String username = "admin";
    public static final String password = "admin";
    public String gatewayURL;
    public String tenantDomain;
//    public DeployApiApi deployApiApi = new DeployApiApi();
//    public GetApiArtifactApi getApiArtifactApi = new GetApiArtifactApi();
//    public GetApiSequencesApi getApiSequencesApi = new GetApiSequencesApi();
//    public UndeployApiApi undeployApiApi = new UndeployApiApi();
//    public ApiClient apiGatewayClient = new ApiClient();

    @Deprecated
    public RESTAPIGatewayImpl() {
        this(username, password, "", "https://localhost:9943");
    }

    public RESTAPIGatewayImpl(String username, String password, String tenantDomain, String gatewayURL) {
        // token/DCR of gateway node itself will be used
        String tokenURL = gatewayURL + "oauth2/token";
        String dcrURL = gatewayURL + "client-registration/v0.17/register";
        String scopes = "openid apim:publish";
        String accessToken = ClientAuthenticator
                .getAccessToken(scopes, appName, callBackURL, tokenScope, appOwner, grantType, dcrURL, username,
                        password, tenantDomain, tokenURL);
        this.gatewayURL = gatewayURL;
        this.tenantDomain = tenantDomain;

//        apiGatewayClient.addDefaultHeader("Authorization", "Bearer " + accessToken);
//        apiGatewayClient.setBasePath(gatewayURL + "api/am/gateway/v1");
//        apiGatewayClient.setDebugging(true);
//        apiGatewayClient.setReadTimeout(600000);
//        apiGatewayClient.setConnectTimeout(600000);
//        apiGatewayClient.setWriteTimeout(600000);
//
//        deployApiApi.setApiClient(apiGatewayClient);
//        undeployApiApi.setApiClient(apiGatewayClient);
//        getApiArtifactApi.setApiClient(apiGatewayClient);
//        getApiSequencesApi.setApiClient(apiGatewayClient);
    }


}
