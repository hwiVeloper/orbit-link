package dev.hwiveloper.orbitlink.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/meta")
public class MetaController {

    @GetMapping("/deauthorize")
    @PostMapping("/deauthorize")
    public ResponseEntity<?> deauthorize(@RequestParam("signed_request") String signedRequest) {
        // signed_request 디코딩 → user_id 추출
        // DB에서 해당 user 토큰 삭제 처리
        return ResponseEntity.ok(Map.of("status", "deauthorized"));
    }

    @GetMapping("/delete-data")
    @PostMapping("/delete-data")
    public ResponseEntity<?> deleteData(@RequestParam("signed_request") String signedRequest) {
        // user_id 기반 데이터 삭제
        // Meta가 요구하는 JSON 응답
        return ResponseEntity.ok(Map.of(
                "url", "https://yourdomain.com/delete-status/12345", // 상태 조회용 URL
                "confirmation_code", "12345"
        ));
    }
}