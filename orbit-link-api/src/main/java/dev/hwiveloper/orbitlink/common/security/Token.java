package dev.hwiveloper.orbitlink.common.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Token  {
    private String email;
    private String nickname;
    private String accessToken;
    private String refreshToken;
}
