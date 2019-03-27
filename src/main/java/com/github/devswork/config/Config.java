package com.github.devswork.config;
/*
 * Compacts this buffer&nbsp;&nbsp;<i>(optional operation)</i>.
 *
 * <p> The shorts between the buffer's current position and its limit,
 * if any, are copied to the beginning of the buffer.  That is, the
 * short at index <i>p</i>&nbsp;=&nbsp;<tt>position()</tt> is copied
 * to index zero, the short at index <i>p</i>&nbsp;+&nbsp;1 is copied
 * to index one, and so forth until the short at index
 * <tt>limit()</tt>&nbsp;-&nbsp;1 is copied to index
 * <i>n</i>&nbsp;=&nbsp;<tt>limit()</tt>&nbsp;-&nbsp;<tt>1</tt>&nbsp;-&nbsp;<i>p</i>.
 * The buffer's position is then set to <i>n+1</i> and its limit is set to
 * its capacity.  The mark, if defined, is discarded.
 *
 * <p> The buffer's position is set to the number of shorts copied,
 * rather than to zero, so that an invocation of this method can be
 * followed immediately by an invocation of another relative <i>put</i>
 * method. </p>
 *
 */
public interface Config {
    String em = "AES";
    String c = "AES/CBC/NoPadding";
    String e = "IKDeG9PNqtiW4y7dtGr5HmgaQMoHW0KoO7PR6KJ0PHg=";
    String i = "DMb3w3CxVMTgnRA+";
    String k = "RQFklhtY9N9cPPt3";
    String d = "tXt/FO2QiAQg7xqopr40sYGyhDJIqR36yzkZAUc7coU=";
    String b = "dFh0L0ZPMlFpQVFnN3hxb3ByNDBzWUd5aERKSXFSMzZ5emtaQVVjN2NvVT0=";
}
