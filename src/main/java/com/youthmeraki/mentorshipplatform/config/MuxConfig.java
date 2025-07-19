package com.youthmeraki.mentorshipplatform.config;

import com.mux.ApiClient;
import com.mux.sdk.AssetsApi;
import com.mux.sdk.DirectUploadsApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MuxConfig {

    @Value("${mux.token.id}")
    private String muxTokenId;

    @Value("${mux.token.secret}")
    private String muxTokenSecret;

    @Bean
    public DirectUploadsApi directUploadApi() {
        ApiClient apiClient = new ApiClient();
        apiClient.setUsername(muxTokenId);
        apiClient.setPassword(muxTokenSecret);
        return new DirectUploadsApi(apiClient);
    }

    @Bean
    public AssetsApi assetsApi() {
        ApiClient apiClient = new ApiClient();
        apiClient.setUsername(muxTokenId);
        apiClient.setPassword(muxTokenSecret);
        return new AssetsApi(apiClient);
    }
}