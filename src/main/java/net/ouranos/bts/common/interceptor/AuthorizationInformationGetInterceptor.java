package net.ouranos.bts.common.interceptor;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.ouranos.bts.common.domain.service.JwtTokenValidationService;

/**
 * ユーザー認証システムから下記ユーザー情報を取得する。
 *
 * <ol>
 * <li>内部事業者識別子
 * <ol>
 */
@Slf4j
public class AuthorizationInformationGetInterceptor implements HandlerInterceptor {
  @Inject
  private JwtTokenValidationService jwtTokenValidationService;

  @Value("${xtrackname:X-Track}")
  private String xTrackName;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String authorizationValue = request.getHeader("Authorization");

    if (authorizationValue == null) {
      throw new AuthenticationException("認証トークンが設定されていません。") {
        private static final long serialVersionUID = 1L;
      };
    }

    String token = authorizationValue.replaceAll("Bearer\\s*", ""); // AuthorizationヘッダーからaccessTokeのみを取得する。
    if (token.isBlank()) {
      throw new AuthenticationException("認証トークンが設定されていません。") {
        private static final long serialVersionUID = 1L;
      };
    }

    // ログ出力のためにMDCに追加。
    MDC.put("operatorId", jwtTokenValidationService.validateToken(token));

    return HandlerInterceptor.super.preHandle(request, response, handler);
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    // 何も実施しない。
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    // 何も実施しない。
    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }
}
