package net.ouranos.bts.common.api.cfp_certification_files;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import net.ouranos.bts.common.common.ApiDestination;
import net.ouranos.bts.common.domain.repository.RestCallRepository;

/**
 * CFP証明書ファイルダウンロードAPI
 */
@RestController
@RequestMapping("cfpCertificationFiles")
public class CfpCertificationFilesController {

	@Inject
	private RestCallRepository restCallRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> get(HttpServletRequest request) {
		return restCallRepository.forwardGetApi("cfpCertificationFiles", request, ApiDestination.TRACEABILITY_SYSTEM);
	}
}
