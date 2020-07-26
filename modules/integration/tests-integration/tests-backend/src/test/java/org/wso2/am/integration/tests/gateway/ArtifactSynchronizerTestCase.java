package org.wso2.am.integration.tests.gateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.am.admin.clients.mediation.SynapseConfigAdminClient;
import org.wso2.am.integration.clients.admin.api.dto.KeyManagerDTO;
import org.wso2.am.integration.clients.gateway.ApiException;
import org.wso2.am.integration.clients.gateway.ApiResponse;
import org.wso2.am.integration.clients.gateway.api.dto.DeployResponseDTO;
import org.wso2.am.integration.clients.publisher.api.v1.dto.APIDTO;
import org.wso2.am.integration.test.impl.RESTAPIGatewayImpl;
import org.wso2.am.integration.test.impl.RestAPIAdminImpl;
import org.wso2.am.integration.test.utils.APIManagerIntegrationTestException;
import org.wso2.am.integration.test.utils.base.APIMIntegrationBaseTest;
import org.wso2.am.integration.test.utils.bean.*;
import org.wso2.am.integration.test.utils.clients.APIPublisherRestClient;
import org.wso2.am.integration.test.utils.clients.APIStoreRestClient;
import org.wso2.am.integration.tests.api.lifecycle.APIManagerLifecycleBaseTest;
import org.wso2.am.integration.tests.api.lifecycle.AddNewHandlerAndInvokeAPITestCase;
import org.wso2.carbon.apimgt.api.model.APIIdentifier;
import org.wso2.carbon.automation.engine.context.TestUserMode;
import org.wso2.carbon.automation.test.utils.common.TestConfigurationProvider;
import org.wso2.carbon.automation.test.utils.http.client.HttpRequestUtil;
import org.wso2.carbon.automation.test.utils.http.client.HttpResponse;
import org.wso2.carbon.integration.common.admin.client.LogViewerClient;
import org.wso2.carbon.integration.common.utils.mgt.ServerConfigurationManager;
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
    private String tenantDomain;
    private ServerConfigurationManager serverConfigurationManager;

    @BeforeClass(alwaysRun = true)
    public void initialize() throws Exception {

        super.init();
        String synapseConfigArtifactsPath =
                TestConfigurationProvider.getResourceLocation() + File.separator + "artifacts" + File.separator +
                        "AM" + File.separator + "lifecycletest" + File.separator + "synapseconfig.xml";
        newSynapseConfig = readFile(synapseConfigArtifactsPath);

        providerName = publisherContext.getContextTenant().getContextUser().getUserName();
        tenantDomain = publisherContext.getContextTenant().getDomain();
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
        if (TestUserMode.SUPER_TENANT_ADMIN == userMode) {
            serverConfigurationManager = new ServerConfigurationManager(gatewayContextWrk);
            serverConfigurationManager.applyConfiguration(new File(
                    getAMResourceLocation() + File.separator + "configFiles" + File.separator +
                            "artifactSynchronizerTest" + File.separator + "deployment.toml"));
        }
    }
    @Test(groups = {"wso2.am"}, description = "Create an API and deploy it in the gateway")
    public void testCreateDeployAPIInGateway()
            throws MalformedURLException, APIManagerIntegrationTestException, JSONException, ApiException,
            org.wso2.am.integration.clients.publisher.api.ApiException { ;
        apiIdentifier = new APIIdentifier(providerName, API_NAME, API_VERSION_1_0_0);
        apiIdentifier.setTier(TIER_GOLD);
        String APIName = "APIGetAllSubscriptionsTestAPI";
        String tags = "youtube, video, media";
        String url = "http://gdata.youtube.com/feeds/api/standardfeeds";
        APIRequest apiRequest = new APIRequest(APIName, API_CONTEXT, new URL(url));
        apiRequest.setTags(tags);
        apiRequest.setDescription(API_DESCRIPTION);
        apiRequest.setVersion(API_VERSION_1_0_0);
        apiRequest.setVisibility("restricted");
        apiRequest.setRoles("admin");

        apiRequest.setTier("Silver");

        apiPublisherClientUser1.addAPI(apiRequest);
        APILifeCycleStateRequest updateRequest = new APILifeCycleStateRequest(APIName, providerName,
                APILifeCycleState.PUBLISHED);
        apiPublisherClientUser1.changeAPILifeCycleStatus(updateRequest);



        ApiResponse<DeployResponseDTO> deployAPIResponse = restAPIGateway.deployAPIInGateway(API_NAME,
                API_VERSION_1_0_0, tenantDomain);
        DeployResponseDTO deployResponseDTO = deployAPIResponse.getData();
        DeployResponseDTO.DeployStatusEnum s=  deployResponseDTO.getDeployStatus();
        String k = deployResponseDTO.getDeployStatus().toString();
    }

    @AfterClass(alwaysRun = true)
    public void destroy() throws Exception {
        super.cleanUp();
    }
}
