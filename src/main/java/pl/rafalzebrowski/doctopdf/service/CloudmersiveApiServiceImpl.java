package pl.rafalzebrowski.doctopdf.service;

import com.cloudmersive.client.ConvertDocumentApi;
import com.cloudmersive.client.invoker.ApiClient;
import com.cloudmersive.client.invoker.ApiException;
import com.cloudmersive.client.invoker.Configuration;
import com.cloudmersive.client.invoker.auth.ApiKeyAuth;
import org.springframework.stereotype.Service;
import pl.rafalzebrowski.doctopdf.configuration.ApplicationProperties;

import java.io.File;

@Service
public class CloudmersiveApiServiceImpl implements CloudmersiveApiService {

    private final ApplicationProperties properties;
    private final ConvertDocumentApi apiInstance;

    public CloudmersiveApiServiceImpl(ApplicationProperties properties) {
        this.properties = properties;
        this.apiInstance = getApiInstance();
    }

    @Override
    public byte[] convertDocumentDocxToPdf(File file) {
        try {
            return apiInstance.convertDocumentDocxToPdf(file);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    private ConvertDocumentApi getApiInstance() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setReadTimeout(60000);
        ApiKeyAuth Apikey = (ApiKeyAuth) defaultClient.getAuthentication("Apikey");
        Apikey.setApiKey(properties.apikey());
        return new ConvertDocumentApi();
    }
}
