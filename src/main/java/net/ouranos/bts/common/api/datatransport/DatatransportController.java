package net.ouranos.bts.common.api.datatransport;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import net.ouranos.bts.common.common.ApiDestination;
import net.ouranos.bts.common.domain.repository.RestCallRepository;

/**
 * 区分A データ流通システム
 */

@RestController
@RequestMapping("api/v1/datatransport")
public class DatatransportController {

	@Inject
	private RestCallRepository restCallRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> get(HttpServletRequest request) {
		return restCallRepository.forwardGetApi("api/v1/datatransport", request, ApiDestination.DATA_TRANSPORT_SYSTEM);
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> put(HttpServletRequest request) {
		return restCallRepository.forwardPutApi("api/v1/datatransport", request, ApiDestination.DATA_TRANSPORT_SYSTEM);
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> delete(HttpServletRequest request) {
		return restCallRepository.forwardDeleteApi("api/v1/datatransport", request, ApiDestination.DATA_TRANSPORT_SYSTEM);
	}
}
