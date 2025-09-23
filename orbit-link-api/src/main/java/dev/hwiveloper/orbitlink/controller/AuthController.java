package dev.hwiveloper.orbitlink.controller;

import dev.hwiveloper.orbitlink.common.constants.APIConst;
import dev.hwiveloper.orbitlink.dto.common.ResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
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
    public ResDTO instagramCallback(@RequestParam("code") String code) {
        ResDTO resDTO = new ResDTO();

        // 1. code -> access_token 교환
        String tokenUrl = "https://api.instagram.com/oauth/access_token";

        // form-data
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", appId);
        formData.add("client_secret", appSecret);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);

        log.info(tokenUrl);

        try {
            String tokenResponse = webClient.post()
                    .uri(tokenUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .exchangeToMono(response ->
                            response.bodyToMono(String.class)
                                    .<String>handle((body, sink) -> {
                                        if (response.statusCode().isError()) {
                                            sink.error(new RuntimeException("Error " + response.statusCode() + ": " + body));
                                            return;
                                        }
                                        sink.next(body);
                                    })
                    )
                    .block();

            JSONObject resBodyJson = new JSONObject(tokenResponse);

            log.info("Instagram Token Response: {}", resBodyJson.toString());

            String accessToken = null, userId = null;

            if (resBodyJson.has("data") && !resBodyJson.getJSONArray("data").isEmpty()) {
                JSONArray dataArray = resBodyJson.getJSONArray("data");
                JSONObject firstData = dataArray.getJSONObject(0);
                accessToken = firstData.optString("access_token");
                userId = firstData.optString("user_id");
            } else if (resBodyJson.has("error")) {
                JSONObject errorObj = resBodyJson.getJSONObject("error");
                String errorMessage = errorObj.optString("message", "Unknown error");
                log.error("Instagram API Error: {}", errorMessage);
                resDTO.setRes(APIConst.EXTERNAL_API_ERROR);
                return resDTO;
            } else if (resBodyJson.has("access_token") && resBodyJson.has("user_id")) {
                accessToken = resBodyJson.optString("access_token");
                userId = resBodyJson.optString("user_id");
            }


            log.info("Instagram Access Token: {}", accessToken);
            log.info("Instagram User ID: {}", userId);

            // 2. Long-lived Token 교환
            String longLivedTokenUrl = "https://graph.instagram.com/access_token"
                    + "?grant_type=ig_exchange_token"
                    + "&client_secret=" + appSecret
                    + "&access_token=" + accessToken;

            String longLivedResponse = webClient.get()
                    .uri(longLivedTokenUrl)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JSONObject longLivedBody = new JSONObject(longLivedResponse);
            String longLivedToken = longLivedBody.optString("access_token");
            int expiresIn = longLivedBody.optInt("expires_in");

            log.info("Long Lived Access Token: {}", longLivedToken);
            log.info("Expires In: {} seconds", expiresIn);

            // 👉 DB 저장 로직
            // userService.saveToken(userId, longLivedToken, expiresIn);

            return resDTO;

        } catch (Exception e) {
            log.error("Instagram OAuth Callback 처리 중 오류", e);
            resDTO.setRes(APIConst.SYSTEM_ERROR);
            return resDTO;
        }
    }
}