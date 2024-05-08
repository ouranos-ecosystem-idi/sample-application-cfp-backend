package net.ouranos.bts.common.domain.service;

public interface JwtTokenValidationService {

  /**
   * 認証トークンの妥当性検証を実施する。
   *
   * <ol>
   * <li>認証トークンをユーザー認証システムのintrospection endpointにPOSTする。
   * <li>妥当な認証トークンの内部事業者識別子を取得する。
   * </ol>
   */
  String validateToken(String token);

}