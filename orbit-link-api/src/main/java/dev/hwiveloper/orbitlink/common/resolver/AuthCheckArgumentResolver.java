package dev.hwiveloper.orbitlink.common.resolver;

import dev.hwiveloper.orbitlink.common.annotation.AuthCheck;
import dev.hwiveloper.orbitlink.dto.auth.AuthCheckDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthCheckArgumentResolver implements HandlerMethodArgumentResolver {

//    private final AuthService authService;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthCheckAnnotation = parameter.hasParameterAnnotation(AuthCheck.class);
        boolean isAuthCheckType = AuthCheckDTO.class.isAssignableFrom(parameter.getParameterType());
        return hasAuthCheckAnnotation && isAuthCheckType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter
        , ModelAndViewContainer mavContainer
        , NativeWebRequest webRequest
        , WebDataBinderFactory binderFactory) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AuthCheck annotation = AnnotationUtils.findAnnotation(parameter.getParameter(), AuthCheck.class);

        // 사용자 정보 조회
//        String username = authentication.getName();
//        Member member = authService.getMemberByEmail(username);
//        log.info("AuthCheckArgumentResolver : Member" + member.toString());
//
//
//        // TODO: 자주 조회할 예정이므로 추후 캐싱 처리 필요
//        // 사용자의 그룹 조회
//        Group myGroup = member.getGroup();
//        // 사용자의 그룹의 가계부 조회
//        MoneyBook moneyBook = moneyBookRepo.findByGroup(myGroup);
//
        AuthCheckDTO authCheckDTO = AuthCheckDTO.builder()
                .build();

        log.info("AuthCheckArgumentResolver : AuthCheckDTO" + authCheckDTO.toString());

        return authCheckDTO;
    }
}
