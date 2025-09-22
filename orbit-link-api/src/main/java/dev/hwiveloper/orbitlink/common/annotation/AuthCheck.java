package dev.hwiveloper.orbitlink.common.annotation;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 컨트롤러 공통으로 인증된 사용자에 대한 정보를 받아 체크하는 로직을 처리한다.
 *
 * 존재하는 사용자인지
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(hidden = true)
public @interface AuthCheck {
}
