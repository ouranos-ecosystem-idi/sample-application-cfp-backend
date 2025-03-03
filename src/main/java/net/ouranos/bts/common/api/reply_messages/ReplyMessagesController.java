package net.ouranos.bts.common.api.reply_messages;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.ouranos.bts.common.common.ApiDestination;
import net.ouranos.bts.common.domain.repository.RestCallRepository;

/**
 * 応答メッセージ登録API
 */
@RestController
@RequestMapping("replyMessages")
public class ReplyMessagesController {

	@Inject
	private RestCallRepository restCallRepository;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<byte[]> post(HttpServletRequest request) {
		return restCallRepository.forwardPostApi("replyMessages", request, ApiDestination.TRACEABILITY_SYSTEM);
	}
}
