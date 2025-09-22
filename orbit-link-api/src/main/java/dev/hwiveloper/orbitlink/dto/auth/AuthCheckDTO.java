package dev.hwiveloper.orbitlink.dto.auth;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthCheckDTO {
    private Long memberId;

    private String memberEmail;

    private String memberNickname;
}
