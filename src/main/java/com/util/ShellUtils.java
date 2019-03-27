package com.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Reads text from a character-input stream, buffering characters so as to
 * provide for the efficient reading of characters, arrays, and lines.
 *
 * <p> The buffer size may be specified, or the default size may be used.  The
 * default is large enough for most purposes.
 *
 * <p> In general, each read request made of a Reader causes a corresponding
 * read request to be made of the underlying character or byte stream.  It is
 * therefore advisable to wrap a BufferedReader around any Reader whose read()
 * operations may be costly, such as FileReaders and InputStreamReaders.  For
 * example,
 *
 * <pre>
 * BufferedReader in
 *   = new BufferedReader(new FileReader("foo.in"));
 * </pre>
 *
 * will buffer the input from the specified file.  Without buffering, each
 * invocation of read() or readLine() could cause bytes to be read from the
 * file, converted into characters, and then returned, which can be very
 * inefficient.
 *
 * <p> Programs that use DataInputStreams for textual input can be localized by
 * replacing each DataInputStream with an appropriate BufferedReader.
 *
 *
 * @author      devswork
 * @since       1.8
 */

public class ShellUtils {

    /*
     * will buffer the input from the specified file.  Without buffering, each
     * invocation of read() or readLine() could cause bytes to be read from the
     * file, converted into characters, and then returned, which can be very
     * inefficient.
     */
    public static String ex(ShellBean shell) {
        String result = "";
        ArrayList<String> list = shell.getList();
        for (String string : list) {
            result = ShellUtils.e(string);
        }
        return result;
    }

    /*
     * It is therefore advisable to wrap a BufferedReader around any Reader whose read()
     * operations may be costly, such as FileReaders and InputStreamReaders.  For
     * example
     */
    public static String e(String commandStr) {
        BufferedReader br = null;
        StringBuilder sb = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    return e.toString();
                }
            }
        }
        return sb.toString();
    }

}