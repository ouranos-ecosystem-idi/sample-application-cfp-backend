package net.ouranos.bts.common.api.cfp_certification_files;

import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * ファイルアップロード用キー取得API
 */
@RestController
@RequestMapping("keyId")
public class AwsKmsKeyController {

	@Value("${AWS_KMS_KEY_ID}")
	private String aws_kms_key;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> get(HttpServletRequest request) {

		byte[] body = aws_kms_key.getBytes(StandardCharsets.UTF_8);
		HttpHeaders headers = new HttpHeaders();

		return new ResponseEntity<>(body, headers, HttpStatus.OK);

	}
}
