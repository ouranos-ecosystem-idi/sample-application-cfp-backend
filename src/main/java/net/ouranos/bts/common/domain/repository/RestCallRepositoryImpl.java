package net.ouranos.bts.common.domain.repository;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

import org.apache.commons.io.IOUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.ouranos.bts.common.common.ApiDestination;

/**
 * RestApiRepository実装クラス。
 */
@Repository
@Slf4j
public class RestCallRepositoryImpl implements RestCallRepository {

	@Value("${URL_DATA_TRANSPORT_SYSTEM}")
	private String transportBasePath;

	@Value("${URL_TRACEABILITY_SYSTEM}")
	private String traceabilityBasePath;

	@Value("${API_KEY_DATA_TRANSPORT_SYSTEM}")
	private String api_Key_data_transport_system;

	@Value("${API_KEY_TRACEABILITY_SYSTEM}")
	private String api_Key_traceability_system;

	@Inject
	private RestTemplate restTemplate;

	private String X_TRACK_NAME = "X-Track";

	@Override
	public ResponseEntity<byte[]> forwardGetApi(String resourcePath, HttpServletRequest request,
			ApiDestination apiDestination) {
		RequestEntity<Void> requestEntity = createGetRequestEntity(resourcePath, request, apiDestination);
		log.info("トレサビAPのGET REST API呼び出し開始。URL={}", requestEntity.getUrl());

		ResponseEntity<byte[]> responseEntity = null;
		try {
			// HTTPステータスコード200の場合の処理。
			responseEntity = restTemplate.exchange(requestEntity, byte[].class);
			log.info("トレサビAPのGET REST API呼び出しで200のレスポンス。URL={}", requestEntity.getUrl());
		} catch (HttpStatusCodeException e) {
			// HTTPステータスコードが200以外の場合の処理。
			log.warn("トレサビAPのGET REST API呼び出しで200以外のレスポンス。", e);

			responseEntity = ResponseEntity//
					.status(e.getStatusCode())//
					.headers(copyHeaders(e.getResponseHeaders()))//
					.body(e.getResponseBodyAsByteArray());
		}
		log.debug("レスポンス。HTTPステータスコード={},ヘッダー={},ボディ={}", //
				responseEntity.getStatusCode(), //
				responseEntity.getHeaders().toString(), //
				new String(responseEntity.getBody(), StandardCharsets.UTF_8));

		responseEntity = ResponseEntity//
				.status(responseEntity.getStatusCode())//
				.headers(copyHeaders(responseEntity.getHeaders()))//
				.body(responseEntity.getBody());

		log.info("トレサビAPのGET REST API呼び出し正常終了。URL={}", requestEntity.getUrl());
		return responseEntity;
	}

	@Override
	public ResponseEntity<byte[]> forwardPostApi(String resourcePath, HttpServletRequest request,
			ApiDestination apiDestination) {
		RequestEntity<byte[]> requestEntity = createPostRequestEntity(resourcePath, request, apiDestination);

		log.info("トレサビAPのPOST REST API呼び出し開始。URL={}", requestEntity.getUrl());

		ResponseEntity<byte[]> responseEntity;
		try {
			// HTTPステータスコード200の場合の処理。
			responseEntity = restTemplate.exchange(requestEntity, byte[].class);
			log.info("トレサビAPのPOST REST API呼び出しで200のレスポンス。URL={}", requestEntity.getUrl());
		} catch (HttpStatusCodeException e) {
			// HTTPステータスコードが200以外の場合の処理。
			log.warn("トレサビAPのPOST REST API呼び出しで200以外のレスポンス。", e);

			responseEntity = ResponseEntity//
					.status(e.getStatusCode())//
					.headers(copyHeaders(e.getResponseHeaders()))//
					.body(e.getResponseBodyAsByteArray());
		}

		responseEntity = ResponseEntity//
				.status(responseEntity.getStatusCode())//
				.headers(copyHeaders(responseEntity.getHeaders()))//
				.body(responseEntity.getBody());

		log.info("トレサビAPのPOST REST API呼び出し終了。URL={}", requestEntity.getUrl());

		return responseEntity;
	}

	@Override
	public ResponseEntity<byte[]> forwardPutApi(String resourcePath, HttpServletRequest request,
			ApiDestination apiDestination) {
		RequestEntity<byte[]> requestEntity = createPutRequestEntity(resourcePath, request, apiDestination);

		log.info("トレサビAPのPUT REST API呼び出し開始。URL={}", requestEntity.getUrl());
		ResponseEntity<byte[]> responseEntity;

		try {
			// HTTPステータスコード200の場合の処理。
			responseEntity = restTemplate.exchange(requestEntity, byte[].class);
			log.info("トレサビAPのPUT REST API呼び出しで200のレスポンス。URL={}", requestEntity.getUrl());
		} catch (HttpStatusCodeException e) {
			// HTTPステータスコードが200以外の場合の処理。
			log.warn("トレサビAPのPUT REST API呼び出しで200以外のレスポンス。", e);

			responseEntity = ResponseEntity//
					.status(e.getStatusCode())//
					.headers(copyHeaders(e.getResponseHeaders()))//
					.body(e.getResponseBodyAsByteArray());
		}

		responseEntity = ResponseEntity//
				.status(responseEntity.getStatusCode())//
				.headers(copyHeaders(responseEntity.getHeaders()))//
				.body(responseEntity.getBody());

		log.info("トレサビAPのPUT REST API呼び出し終了。URL={}", requestEntity.getUrl());

		return responseEntity;
	}

	@Override
	public ResponseEntity<byte[]> forwardDeleteApi(String resourcePath, HttpServletRequest request,
			ApiDestination apiDestination) {
		RequestEntity<Void> requestEntity = createDeleteRequestEntity(resourcePath, request, apiDestination);
		log.info("トレサビAPのDELETE REST API呼び出し開始。URL={}", requestEntity.getUrl());

		ResponseEntity<byte[]> responseEntity = null;
		try {
			// HTTPステータスコード204の場合の処理。
			responseEntity = restTemplate.exchange(requestEntity, byte[].class);
			log.info("トレサビAPのDELETE REST API呼び出しで204のレスポンス。URL={}", requestEntity.getUrl());
		} catch (HttpStatusCodeException e) {
			// HTTPステータスコードが204以外の場合の処理。
			log.warn("トレサビAPのDELETE REST API呼び出しで204以外のレスポンス。URL={}", requestEntity.getUrl(), e);

			responseEntity = ResponseEntity
					.status(e.getStatusCode())
					.headers(copyHeaders(e.getResponseHeaders()))
					.body(e.getResponseBodyAsByteArray());

			return responseEntity;
		}

		responseEntity = ResponseEntity
				.status(responseEntity.getStatusCode())
				.headers(copyHeaders(responseEntity.getHeaders()))
				.body(null);// レスポンスがないため、nullを返却する

		log.info("トレサビAPのDELETE REST API呼び出し正常終了。URL={}", requestEntity.getUrl());
		return responseEntity;
	}

	private RequestEntity<Void> createGetRequestEntity(String resourcePath, HttpServletRequest request,
			ApiDestination apiDestination) {

		// 元のリクエストのパスを抽出し、転送先のURLに追加する。
		UriComponentsBuilder builder = UriComponentsBuilder//
				.fromHttpUrl(getBasePath(apiDestination))//
				.path(resourcePath);

		URI uri = builder//
				.query(request.getQueryString())//
				.build(true)//
				.toUri();

		HttpHeaders headers = createHeader(request, apiDestination);

		return RequestEntity//
				.get(uri)//
				.headers(headers)//
				.build();
	}

	private RequestEntity<byte[]> createPostRequestEntity(String resourcePath, HttpServletRequest request,
			ApiDestination apiDestination) {

		// 元のリクエストのパスを抽出し、転送先のURLに追加する。
		UriComponentsBuilder builder = UriComponentsBuilder//
				.fromHttpUrl(getBasePath(apiDestination))//
				.path(resourcePath);

		URI uri = builder//
				.query(request.getQueryString())//
				.build(true)//
				.toUri();

		HttpHeaders headers = createHeader(request, apiDestination);

		RequestEntity<byte[]> requestEntity = null;

		try (var in = request.getInputStream()) {
			byte[] bytes = IOUtils.toByteArray(in);
			requestEntity = RequestEntity//
					.post(uri)//
					.headers(headers)//
					.body(bytes);
		} catch (IOException e) {
			// ApiGlobalExceptionHandlerクラスで明示的にRuntimeExceptionを処理している。
			throw new RuntimeException(e);// NOSONAR
		}

		return requestEntity;
	}

	private RequestEntity<byte[]> createPutRequestEntity(String resourcePath, HttpServletRequest request,
			ApiDestination apiDestination) {

		// 元のリクエストのパスを抽出し、転送先のURLに追加する。
		UriComponentsBuilder builder = UriComponentsBuilder//
				.fromHttpUrl(getBasePath(apiDestination))//
				.path(resourcePath);

		URI uri = builder//
				.query(request.getQueryString())//
				.build(true)//
				.toUri();

		HttpHeaders headers = createHeader(request, apiDestination);

		RequestEntity<byte[]> requestEntity = null;

		try (var in = request.getInputStream()) {
			byte[] bytes = IOUtils.toByteArray(in);
			requestEntity = RequestEntity//
					.put(uri)//
					.headers(headers)//
					.body(bytes);
		} catch (IOException e) {
			// ApiGlobalExceptionHandlerクラスで明示的にRuntimeExceptionを処理している。
			throw new RuntimeException(e);// NOSONAR
		}

		return requestEntity;
	}

	private RequestEntity<Void> createDeleteRequestEntity(String resourcePath, HttpServletRequest request,
			ApiDestination apiDestination) {

		// 元のリクエストのパスを抽出し、転送先のURLに追加する。
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(getBasePath(apiDestination))
				.path(resourcePath);

		URI uri = builder
				.query(request.getQueryString())
				.build(true)
				.toUri();

		HttpHeaders headers = createHeader(request, apiDestination);

		RequestEntity<Void> requestEntity = RequestEntity
				.delete(uri)
				.headers(headers)
				.build();

		return requestEntity;
	}

	private HttpHeaders createHeader(HttpServletRequest request, ApiDestination apiDestination) {
		HttpHeaders headers = new HttpHeaders();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			headers.set(headerName, request.getHeader(headerName));
		}

		// X-Trackを設定
		headers.set(X_TRACK_NAME, MDC.get(X_TRACK_NAME));

		// APIキーを付与
		if (apiDestination == ApiDestination.DATA_TRANSPORT_SYSTEM) {
			headers.set("apiKey", api_Key_data_transport_system);
		} else {
			headers.set("x-api-key", api_Key_traceability_system);
		}

		log.debug("HTTP Headers={}", headers);

		return headers;
	}

	private String getBasePath(ApiDestination apiDestination) {
		if (apiDestination == ApiDestination.DATA_TRANSPORT_SYSTEM) {
			return transportBasePath;
		} else {
			return traceabilityBasePath;
		}
	}

	/**
	 *
	 * @param headers
	 * @return
	 */
	private HttpHeaders copyHeaders(HttpHeaders headers) {
		HttpHeaders filteredHeaders = new HttpHeaders();
		if (headers != null) {
			headers.forEach((key, value) -> {
				if (!key.equals("Transfer-Encoding")) {
					filteredHeaders.addAll(key, value);
				}

			});
		}
		return filteredHeaders;
	}
}