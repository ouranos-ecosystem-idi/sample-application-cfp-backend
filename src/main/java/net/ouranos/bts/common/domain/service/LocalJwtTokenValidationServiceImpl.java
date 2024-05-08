package net.ouranos.bts.common.domain.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * ローカル実行用の実装クラス。
 */
@Service
@Slf4j
@Profile("local")
public class LocalJwtTokenValidationServiceImpl implements JwtTokenValidationService {

  /**
   * tokenをそのままトークン検証されたユーザ情報として返す。
   */
  @Override
  public String validateToken(String token) {
    return token;
  }
}
