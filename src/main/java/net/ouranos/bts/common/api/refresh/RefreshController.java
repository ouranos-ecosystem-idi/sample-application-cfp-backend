
package net.ouranos.bts.common.api.refresh;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import net.ouranos.bts.common.common.ApiDestination;
import net.ouranos.bts.common.domain.repository.RestCallRepository;

/**
 * アクセストークン情報更新API
 */
@RestController
@RequestMapping("auth/refresh")
public class RefreshController {

	@Inject
	private RestCallRepository restCallRepository;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> post(HttpServletRequest request) {
		return restCallRepository.forwardPostApi("auth/refresh", request, ApiDestination.DATA_TRANSPORT_SYSTEM);
	}
}
