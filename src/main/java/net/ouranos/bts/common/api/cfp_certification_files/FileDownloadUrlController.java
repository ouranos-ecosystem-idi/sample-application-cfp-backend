package net.ouranos.bts.common.api.cfp_certification_files;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.ouranos.bts.common.common.ApiDestination;
import net.ouranos.bts.common.domain.repository.RestCallRepository;

/**
 * ファイルダウンロード用URL取得API
 */
@RestController
@RequestMapping("fileDownloadUrl")
public class FileDownloadUrlController {

	@Inject
	private RestCallRepository restCallRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> get(HttpServletRequest request) {
		return restCallRepository.forwardGetApi("fileDownloadUrl", request, ApiDestination.TRACEABILITY_SYSTEM);
	}
}
