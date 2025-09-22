package dev.hwiveloper.orbitlink.repository;

import dev.hwiveloper.orbitlink.common.constants.Provider;
import dev.hwiveloper.orbitlink.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndActiveYn(String email, String activeYn);
    Optional<Member> findByOauthIdAndProviderAndActiveYn(String oauthId, Provider provider, String activeYn);
}
