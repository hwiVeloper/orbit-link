package dev.hwiveloper.orbitlink.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/instagram")
public class AuthController {

    @Value("${meta.app-id}")
    private String appId;

    @Value("${meta.app-secret}")
    private String appSecret;

    @Value("${meta.redirect-uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public ResponseEntity<?> instagramCallback(@RequestParam("code") String code) {
        RestTemplate restTemplate = new RestTemplate();

        // 1. code -> access_token 교환
        String tokenUrl = "https://graph.facebook.com/v21.0/oauth/access_token"
                + "?client_id=" + appId
                + "&redirect_uri=" + redirectUri
                + "&client_secret=" + appSecret
                + "&code=" + code;

        Map<String, Object> tokenResponse = restTemplate.getForObject(tokenUrl, Map.class);

        if (tokenResponse == null || !tokenResponse.containsKey("access_token")) {
            return ResponseEntity.badRequest().body("Failed to retrieve access token");
        }

        String accessToken = (String) tokenResponse.get("access_token");

        // 👉 DB 저장 로직 필요 (유저별 accessToken, expires_in 등)
        // userService.saveToken(userId, accessToken);

        return ResponseEntity.ok(Map.of(
                "message", "Instagram 계정 연동 성공",
                "access_token", accessToken
        ));
    }
}