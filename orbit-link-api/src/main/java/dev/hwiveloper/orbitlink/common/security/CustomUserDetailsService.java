package dev.hwiveloper.orbitlink.common.security;

import dev.hwiveloper.orbitlink.domain.Member;
import dev.hwiveloper.orbitlink.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = repository.findByEmailAndActiveYn(username, "Y")
                .orElseThrow(() -> new UsernameNotFoundException("Member not found with email: " + username));

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));

        // 소셜 로그인이라 password가 null일 수 있음 → 빈 문자열로 대체
        return new User(
                member.getEmail(),
                "",
                roles
        );
    }

}
