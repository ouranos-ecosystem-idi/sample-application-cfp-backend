package net.ouranos.bts.common.exception_handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * REST APIのエラーレスポンスのクラス。
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {
  public Collection<ApiError> errors = new ArrayList<>();

  public ApiErrorResponse(ApiError apiError) {
    this.errors.add(apiError);
  }

  public ApiErrorResponse(List<ApiError> errors) {
    this.errors = errors;
  }

  @AllArgsConstructor
  public static class ApiError {
    public String errorCode;
    public String errorDescription;
  }
}
