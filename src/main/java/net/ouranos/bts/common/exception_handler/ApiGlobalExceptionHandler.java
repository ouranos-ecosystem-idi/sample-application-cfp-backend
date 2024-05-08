package net.ouranos.bts.common.exception_handler;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import net.ouranos.bts.common.exception_handler.ApiErrorResponse.ApiError;

/**
 * 共通例外ハンドラー。
 */
@ControllerAdvice
public class ApiGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /** システムエラー エラーコード */
  private static final String SYSTEM_ERROR_CODE = "MSGAECO0013";

  /** トレーサビリティシステムリクエストパス */
  private static final List<String> TRACEABILITY_REQUEST_PATHS = List.of("cfpAcl", "cfpCertificationAcl",
      "cfpCertificationFiles", "cfpCertifications", "notifications");

  @Inject
  private MessageSource messageSource;

  @Inject
  private HttpServletRequest request;

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
      HttpStatusCode statusCode, WebRequest request) {
    return createResponseMessage();
  }

  @Override
  // springバージョンアップ時に本メソッドが廃止された場合に移植対応する。
  protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatusCode statusCode, // NOSONAR
      WebRequest request) {
    return createResponseMessage();
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    return createResponseMessage();
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
      HttpStatusCode statusCode, WebRequest request) {
    return createResponseMessage();
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
    return createResponseMessage();
  }

  /**
   * エラーメッセージの新規作成
   *
   * @return レスポンス
   */
  private ResponseEntity<Object> createResponseMessage() {
    String message = messageSource.getMessage(SYSTEM_ERROR_CODE, null, LocaleContextHolder.getLocale());

    ResponseEntity<Object> response;

    if (judgeTraceabilityRequest()) {
      ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
      apiErrorResponse.errors.add(new ApiError(SYSTEM_ERROR_CODE, message));
      response = new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    } else {
      ApiDataDistributionErrorResponse apiErrorResponse = new ApiDataDistributionErrorResponse(SYSTEM_ERROR_CODE,
          message, "");
      response = new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return response;
  }
  /**
   * トレーサビリティシステムリクエストか判定する。
   *
   * @return トレーサビリティシステムリクエストの場合 true, それ以外の場合 false
   */
  private boolean judgeTraceabilityRequest() {
    String path = request.getServletPath();
    return TRACEABILITY_REQUEST_PATHS.stream().anyMatch(path::contains);
  }
}
