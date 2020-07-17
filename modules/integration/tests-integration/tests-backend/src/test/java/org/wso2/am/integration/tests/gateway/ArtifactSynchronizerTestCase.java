package org.wso2.am.integration.tests.gateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.am.admin.clients.mediation.SynapseConfigAdminClient;
import org.wso2.am.integration.clients.gateway.ApiException;
import org.wso2.am.integration.test.impl.RESTAPIGatewayImpl;
import org.wso2.am.integration.test.impl.RestAPIAdminImpl;
import org.wso2.am.integration.test.utils.APIManagerIntegrationTestException;
import org.wso2.am.integration.test.utils.bean.APICreationRequestBean;
import org.wso2.am.integration.test.utils.bean.APILifeCycleState;
import org.wso2.am.integration.test.utils.clients.APIPublisherRestClient;
import org.wso2.am.integration.test.utils.clients.APIStoreRestClient;
import org.wso2.am.integration.tests.api.lifecycle.APIManagerLifecycleBaseTest;
import org.wso2.am.integration.tests.api.lifecycle.AddNewHandlerAndInvokeAPITestCase;
import org.wso2.carbon.apimgt.api.model.APIIdentifier;
import org.wso2.carbon.automation.test.utils.common.TestConfigurationProvider;
import org.wso2.carbon.automation.test.utils.http.client.HttpRequestUtil;
import org.wso2.carbon.automation.test.utils.http.client.HttpResponse;
import org.wso2.carbon.integration.common.admin.client.LogViewerClient;
import org.wso2.carbon.logging.view.data.xsd.LogEvent;

import javax.ws.rs.core.Response;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ArtifactSynchronizerTestCase extends APIManagerLifecycleBaseTest {
    private static final Log log = LogFactory.getLog(AddNewHandlerAndInvokeAPITestCase.class);
    private static final String API_NAME = "AddNewHandlerAndInvokeAPITest";
    private static final String API_CONTEXT = "AddNewHandlerAndInvokeAPI";
    private static final String API_TAGS = "testTag1, testTag2, testTag3";
    private static final String API_DESCRIPTION = "This is test API create by API manager integration test";
    private static final String API_VERSION_1_0_0 = "1.0.0";
    private static final String GATEWAY_LABEL = "test";
    private static final String API_END_POINT_POSTFIX_URL = "jaxrs_basic/services/customers/customerservice/";
    private APIPublisherRestClient apiPublisherClientUser1;
    private APIStoreRestClient apiStoreClientUser1;
    private String providerName;
    private String newSynapseConfig;
    private APIIdentifier apiIdentifier;
    private SynapseConfigAdminClient synapseConfigAdminClient;
    private String gatewaySession;
    private String apiEndPointUrl;
    private RESTAPIGatewayImpl restAPIGatewayUser;

    @BeforeClass(alwaysRun = true)
    public void initialize() throws Exception {
        super.init();

        String synapseConfigArtifactsPath =
                TestConfigurationProvider.getResourceLocation() + File.separator + "artifacts" + File.separator +
                        "AM" + File.separator + "lifecycletest" + File.separator + "synapseconfig.xml";
        newSynapseConfig = readFile(synapseConfigArtifactsPath);

        providerName = publisherContext.getContextTenant().getContextUser().getUserName();
        String publisherURLHttp = publisherUrls.getWebAppURLHttp();
        apiPublisherClientUser1 = new APIPublisherRestClient(publisherURLHttp);
        //Login to API Publisher with  admin
        apiPublisherClientUser1.login(
                publisherContext.getContextTenant().getContextUser().getUserName(),
                publisherContext.getContextTenant().getContextUser().getPassword());
        gatewaySession = createSession(gatewayContextMgt);
        synapseConfigAdminClient =
                new SynapseConfigAdminClient(gatewayContextMgt.getContextUrls().getBackEndUrl(), gatewaySession);
        apiEndPointUrl = gatewayUrlsWrk.getWebAppURLHttp() + API_END_POINT_POSTFIX_URL;

        restAPIGatewayUser = new RESTAPIGatewayImpl("admin", "admin", "carbon.super",
                gatewayHTTPSURL);
    }
    @Test(groups = {"wso2.am"}, description = "Create an API and deploy it in the gateway")
    public void testCreateDeployAPIInGateway()
            throws MalformedURLException, APIManagerIntegrationTestException, JSONException, ApiException {
        apiIdentifier = new APIIdentifier(providerName, API_NAME, API_VERSION_1_0_0);
        apiIdentifier.setTier(TIER_GOLD);
        APICreationRequestBean apiCreationRequestBean = new APICreationRequestBean(API_NAME, API_CONTEXT,
                API_VERSION_1_0_0, providerName, new URL(apiEndPointUrl));
        apiCreationRequestBean.setTags(API_TAGS);
        apiCreationRequestBean.setDescription(API_DESCRIPTION);
        HttpResponse createAPIResponse = apiPublisherClientUser1.addAPI(apiCreationRequestBean);

        HttpResponse apiResponse=apiPublisher.getApi(API_NAME, providerName, API_VERSION_1_0_0);
        assertEquals(apiResponse.getResponseCode(), Response.Status.OK.getStatusCode());
        assertTrue(apiResponse.getData().contains("\"error\" : false"),"Response Data Mismatched");
        JSONObject apiCopyResponse=new JSONObject(apiResponse.getData());
        String newName=apiCopyResponse.getJSONObject("api").getString("name");
        assertEquals(newName,API_NAME,"API Name is not correct");
        String apiID=apiCopyResponse.getJSONObject("api").getString("apiID");

        restAPIGatewayUser.deployAPIInGateway(apiID, API_VERSION_1_0_0, GATEWAY_LABEL);

        if (createAPIResponse.getResponseCode() == HTTP_RESPONSE_CODE_OK &&
                getValueFromJSON(createAPIResponse, "error").equals("false")) {
            log.info("API Created :" + getAPIIdentifierString(apiIdentifier));
            //Publish the API
        } else {
            throw new APIManagerIntegrationTestException("Error in API Creation." +
                    getAPIIdentifierString(apiIdentifier) +
                    "Response Code:" + createAPIResponse.getResponseCode() +
                    " Response Data :" + createAPIResponse.getData());
        }
    }
}
