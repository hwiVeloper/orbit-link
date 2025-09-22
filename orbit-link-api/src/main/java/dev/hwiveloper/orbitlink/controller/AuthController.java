package dev.hwiveloper.orbitlink.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/instagram")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Value("${meta.app-id}")
    private String appId;

    @Value("${meta.app-secret}")
    private String appSecret;

    @Value("${meta.redirect-uri}")
    private String redirectUri;

    private final WebClient webClient;

    @GetMapping("/callback")
    public ResponseEntity<?> instagramCallback(@RequestParam("code") String code) {
        RestTemplate restTemplate = new RestTemplate();

        // 1. code -> access_token 교환
        String tokenUrl = "https://api.instagram.com/oauth/access_token"
                + "?client_id=" + appId
                + "&client_secret=" + appSecret
                + "&grant_type=authorization_code"
                + "&redirect_uri=" + redirectUri
                + "&code=" + code;

        log.info(tokenUrl);

        ResponseEntity<String> response = webClient.get()
                .uri(tokenUrl)
                .retrieve()
                .toEntity(String.class)
                .block();

        // http status
        HttpStatusCode statusCode = response.getStatusCode();
        JSONObject resBodyJson = new JSONObject(response.getBody());

        if (statusCode == HttpStatus.OK) {
            JSONArray dataArray = resBodyJson.optJSONArray("data");
            if (!dataArray.isEmpty()) {
                JSONObject firstData = dataArray.getJSONObject(0);
                String accessToken = firstData.optString("access_token");
                String userId = firstData.optString("user_id");

                log.info("Instagram Access Token: {}", accessToken);
                log.info("Instagram User ID: {}", userId);

                // long lived token 교환
                String longLivedTokenUrl = "https://graph.instagram.com/access_token"
                        + "?grant_type=ig_exchange_token"
                        + "&client_secret=" + appSecret
                        + "&access_token=" + accessToken;

                log.info(longLivedTokenUrl);

                ResponseEntity<String> longLivedResponse = webClient.get()
                        .uri(longLivedTokenUrl)
                        .retrieve()
                        .toEntity(String.class)
                        .block();

                HttpStatusCode longLivedStatus = longLivedResponse.getStatusCode();
                JSONObject longLivedBody = new JSONObject(longLivedResponse.getBody());
                if (longLivedStatus == HttpStatus.OK) {
                    String longLivedToken = longLivedBody.optString("access_token");
                    int expiresIn = longLivedBody.optInt("expires_in");

                    log.info("Long Lived Access Token: {}", longLivedToken);
                    log.info("Expires In: {} seconds", expiresIn);

                    // 👉 DB 저장 로직 필요 (유저별 accessToken, expires_in 등)
                    // userService.saveToken(userId, longLivedToken);

                    return ResponseEntity.ok(Map.of(
                            "message", "Instagram 계정 연동 성공",
                            "access_token", longLivedToken,
                            "expires_in", expiresIn
                    ));
                } else {
                    String errorMessage = longLivedBody.optString("error_message", "Unknown error");
                    return ResponseEntity.status(longLivedStatus).body(Map.of(
                            "message", "Long lived token 교환 실패",
                            "error", errorMessage
                    ));
                }
            }
        } else {
            String errorMessage = resBodyJson.optString("error_message", "Unknown error");
            return ResponseEntity.status(statusCode).body(Map.of(
                    "message", "Instagram 계정 연동 실패",
                    "error", errorMessage
            ));
        }

        // 👉 DB 저장 로직 필요 (유저별 accessToken, expires_in 등)
        // userService.saveToken(userId, accessToken);
        String errorMessage = resBodyJson.optString("error_message", "Unknown error");
        return ResponseEntity.status(statusCode).body(Map.of(
                "message", "Instagram 계정 연동 실패",
                "error", errorMessage
        ));
    }
}