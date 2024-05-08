package net.ouranos.bts.common.api.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.ouranos.bts.common.domain.model.Health;

/**
 * 死活監視用のAPI。
 */
@RestController
@RequestMapping("health")
public class HealthController {

  /**
   * 下記情報を返却する。
   *
   * <ol>
   * <li>環境変数GIT_HASHの値。
   * </ol>
   *
   * @param queryParameter
   *          クエリパラメータ
   * @return 検索結果
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Health get() {
    String gitHash = System.getenv("GIT_HASH");

    return new Health(gitHash);
  }
}
