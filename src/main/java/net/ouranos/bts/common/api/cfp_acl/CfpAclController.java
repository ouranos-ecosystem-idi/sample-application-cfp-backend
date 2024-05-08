package net.ouranos.bts.common.api.cfp_acl;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import net.ouranos.bts.common.common.ApiDestination;
import net.ouranos.bts.common.domain.repository.RestCallRepository;

/**
 * CFP情報開示設定取得API・CFP情報開示設定登録API
 */
@RestController
@RequestMapping("cfpAcl")
public class CfpAclController {

	@Inject
	private RestCallRepository restCallRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> get(HttpServletRequest request) {
		return restCallRepository.forwardGetApi("cfpAcl", request, ApiDestination.TRACEABILITY_SYSTEM);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> post(HttpServletRequest request) {
		return restCallRepository.forwardPostApi("cfpAcl", request, ApiDestination.TRACEABILITY_SYSTEM);
	}
}
