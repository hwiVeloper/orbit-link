package dev.hwiveloper.orbitlink.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook/instagram")
@Slf4j
public class InstagramWebhookController {


    // .env 또는 application.yml에 설정해둔 검증 토큰
    private static final String VERIFY_TOKEN = "my_verify_token";

    /**
     * 구독 검증용 (Meta에서 GET 요청)
     * 예: GET /webhook/instagram?hub.mode=subscribe&hub.verify_token=xxx&hub.challenge=12345
     */
    @GetMapping
    public ResponseEntity<String> verifySubscription(
            @RequestParam(name = "hub.mode", required = false) String mode,
            @RequestParam(name = "hub.verify_token", required = false) String token,
            @RequestParam(name = "hub.challenge", required = false) String challenge
    ) {
        log.info("Webhook verification request - mode: {}, token: {}, challenge: {}", mode, token, challenge);

        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
            return ResponseEntity.ok(challenge);
        } else {
            return ResponseEntity.status(403).body("Verification failed");
        }
    }

    /**
     * 이벤트 수신용 (Meta에서 POST 요청)
     * body에는 JSON payload가 들어옴
     */
    @PostMapping
    public ResponseEntity<String> receiveWebhook(@RequestBody Map<String, Object> payload) {
        log.info("📩 Received Instagram webhook: {}", payload);

        // object / entry / changes 구조
        // 예: {"object":"instagram", "entry":[{"id":"...","changes":[{"field":"messages","value":{...}}]}]}
        String objectType = (String) payload.get("object");

        if ("instagram".equals(objectType)) {
            // entry 배열 안에서 이벤트 파싱
            var entries = (Iterable<Map<String, Object>>) payload.get("entry");
            for (Map<String, Object> entry : entries) {
                var changes = (Iterable<Map<String, Object>>) entry.get("changes");
                for (Map<String, Object> change : changes) {
                    String field = (String) change.get("field");
                    Map<String, Object> value = (Map<String, Object>) change.get("value");

                    // 이벤트 유형별 분기 처리
                    switch (field) {
                        case "messages" -> handleMessageEvent(value);
                        case "comments" -> handleCommentEvent(value);
                        case "mentions" -> handleMentionEvent(value);
                        default -> log.warn("Unhandled event field: {}", field);
                    }
                }
            }
        }

        return ResponseEntity.ok("EVENT_RECEIVED");
    }

    private void handleMessageEvent(Map<String, Object> value) {
        log.info("💬 New DM Event: {}", value);
        // TODO: DM 자동화 처리 로직
    }

    private void handleCommentEvent(Map<String, Object> value) {
        log.info("📝 New Comment Event: {}", value);
        // TODO: 댓글 이벤트 처리 로직
    }

    private void handleMentionEvent(Map<String, Object> value) {
        log.info("🔔 New Mention Event: {}", value);
        // TODO: 멘션 이벤트 처리 로직
    }
}