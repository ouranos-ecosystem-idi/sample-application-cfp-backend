package net.ouranos.bts.common.domain.service;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

/**
 * 認証トークンの妥当性検証を実施するクラス。
 */
@Service
@Slf4j
@Profile("default")
public class JwtTokenValidationServiceImpl implements JwtTokenValidationService {

  @Inject
  private RestTemplate restTemplate;

  @Value("${API_KEY_INTROSPECTION_ENDPOINT}")
  private String apiKey;

  @Value("${URL_INTROSPECTION_ENDPOINT}")
  private String urlIntrospectionEndpoint;

  /**
   * 認証トークンの妥当性検証を実施する。
   *
   * <ol>
   * <li>認証トークンをユーザー認証システムのintrospection endpointにPOSTする。
   * <li>妥当な認証トークンの内部事業者識別子を取得する。
   * </ol>
   */
  @Override
  public String validateToken(@NotBlank String token) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("idToken", token);

    log.debug("リクエスト内容。url={}", urlIntrospectionEndpoint);

    RequestEntity<String> requestEntity = RequestEntity //
        .post(urlIntrospectionEndpoint) //
        .contentType(MediaType.APPLICATION_JSON) //
        .header("X-Track", MDC.get("X-Track")) //
        .header("apiKey", apiKey) //
        .header("Authorization", "Bearer " + token) //
        .body(jsonObject.toString(), String.class);

    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

    String operatorIdJson = responseEntity.getBody();

    // 4xxと5xxの以外のHttpステータスコードで、レスポンスボディが想定外の場合。4xxと5xxの場合、例外が投げられるが、それ以外の場合は正常と扱われる。
    if (operatorIdJson == null) {
      log.warn("想定外の返却結果。response={}", operatorIdJson);
      throw new AuthenticationException("認証エラー。") {
        private static final long serialVersionUID = 1L;
      };
    }

    JsonObject operatorIdJsonObject = JsonParser.parseString(operatorIdJson).getAsJsonObject();

    if (operatorIdJsonObject == null) {
      log.warn("想定外の返却結果。response={}", operatorIdJson);
      throw new AuthenticationException("認証エラー。") {
        private static final long serialVersionUID = 1L;
      };
    }

    if (operatorIdJsonObject.get("operatorId").isJsonNull()) {
      throw new AuthenticationException("認証エラー。") {
        private static final long serialVersionUID = 1L;
      };
    }

    String operatorId = operatorIdJsonObject.get("operatorId").getAsString();

    log.debug("認証トークンから取得した内部事業者識別子。operatorId={}", operatorId);

    if (operatorId == null) {
      log.warn("トークン検証エラー。");
      throw new AuthenticationException("認証エラー。") {
        private static final long serialVersionUID = 1L;
      };
    }

    return operatorId;
  }

}
