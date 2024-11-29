
package net.ouranos.bts.common.domain.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import net.ouranos.bts.common.common.ApiDestination;

/**
 * REST-API呼び出し共通処理。
 */
@Service
public interface RestCallRepository {

	public ResponseEntity<byte[]> forwardGetApi(String resourcePath, HttpServletRequest request, ApiDestination apiDestination);

	public ResponseEntity<byte[]> forwardPostApi(String resourcePath, HttpServletRequest request, ApiDestination apiDestination);

	public ResponseEntity<byte[]> forwardPutApi(String resourcePath, HttpServletRequest request, ApiDestination apiDestination);

	public ResponseEntity<byte[]> forwardDeleteApi(String resourcePath, HttpServletRequest request, ApiDestination apiDestination);
}