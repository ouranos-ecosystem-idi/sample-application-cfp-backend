
package net.ouranos.bts.common.api.change;

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
 * パスワード変更API
 */
@RestController
@RequestMapping("auth/change")
public class ChangeController {

	@Inject
	private RestCallRepository restCallRepository;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> post(HttpServletRequest request) {
		return restCallRepository.forwardPostApi("auth/change", request, ApiDestination.DATA_TRANSPORT_SYSTEM);
	}
}
