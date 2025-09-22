package dev.hwiveloper.orbitlink.common.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create(
                        // 커넥션 풀 설정
                        ConnectionProvider.builder("custom")
                                .maxConnections(10) // 최대 연결 수 설정
                                .pendingAcquireTimeout(Duration.ofSeconds(10)) // 연결 대기 시간 설정
                                .build())
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // Connection timeout 설정 (5초)
                        .responseTimeout(Duration.ofSeconds(5)) // 응답 timeout 설정 (5초)
                        .doOnConnected(conn -> conn
                                .addHandlerLast(new ReadTimeoutHandler(25)) // Read timeout 설정 (5초)
                                .addHandlerLast(new WriteTimeoutHandler(5))))) // Write timeout 설정 (5초)
                .build();
    }
}
