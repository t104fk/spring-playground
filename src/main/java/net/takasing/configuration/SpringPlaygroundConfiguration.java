package net.takasing.configuration;

import jp.company.configuration.CompanyConfiguration;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author toyofuku_takashi
 */
@Configuration
@ComponentScan(basePackages = {
        "net.takasing.service",
        "net.takasing.repository"
})
@Import(CompanyConfiguration.class)
public class SpringPlaygroundConfiguration {
    @Bean
    public HttpClient httpClient() {
        return HttpClientBuilder.create()
                .setConnectionManager(connectionManager())
                .setDefaultRequestConfig(requestConfig())
                .build();
    }
    private HttpClientConnectionManager connectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(2);
        connectionManager.setDefaultMaxPerRoute(2);
        return connectionManager;
    }
    private RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(1000)
                .setSocketTimeout(1000)
                .build();
    }
}
