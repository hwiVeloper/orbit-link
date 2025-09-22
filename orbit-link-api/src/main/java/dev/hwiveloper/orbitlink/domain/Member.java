package dev.hwiveloper.orbitlink.domain;

import dev.hwiveloper.orbitlink.common.constants.Provider;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Data
@Entity
@Table
@Comment("멤버")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // 내부 PK

    @Column(nullable = false, unique = true)
    private String oauthId;
    // Instagram user_id, Google sub 값 등 소셜 서비스에서 유니크하게 주는 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Provider provider;
    // INSTAGRAM, GOOGLE, APPLE 등 어떤 소셜 로그인인지 구분

    @Column(nullable = true)
    private String username;
    // 인스타그램 username 같은 표시용 아이디 (필요 시 null 허용)

    @Column(nullable = true)
    private String email;
    // 인스타에서 기본 제공 안될 수도 있음

    @Column(nullable = true)
    private String profileImageUrl;
    // 프로필 이미지 (선택적)

    @Column(columnDefinition = "CHAR(1) DEFAULT 'Y'", nullable = false)
    private String activeYn;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}