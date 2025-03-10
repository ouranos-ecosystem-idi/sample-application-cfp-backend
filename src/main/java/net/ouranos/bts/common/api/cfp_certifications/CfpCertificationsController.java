package net.ouranos.bts.common.api.cfp_certifications;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.ouranos.bts.common.common.ApiDestination;
import net.ouranos.bts.common.domain.repository.RestCallRepository;

/**
 * CFP証明書削除API
 */
@RestController
@RequestMapping("cfpCertifications")
public class CfpCertificationsController {

	@Inject
	private RestCallRepository restCallRepository;

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> delete(HttpServletRequest request) {
		return restCallRepository.forwardDeleteApi("cfpCertifications", request, ApiDestination.TRACEABILITY_SYSTEM);
	}
}