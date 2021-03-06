
package com.github.devswork.util.excel;



import com.github.devswork.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


public class DataCell {

    public static final int DATA_TYPE_UNKNOW = -1;

    public static final int DATA_TYPE_NUMERIC = 0;

    public static final int DATA_TYPE_TEXT = 1;

    public static final int DATA_TYPE_BLANK = 3;

    public static final int DATA_TYPE_DATE = 5;

    public static final int DATA_TYPE_DATETIME = 6;

    public static final int DATA_TYPE_INTEGER = 7;

    private String content;

    private int type = DATA_TYPE_UNKNOW;

    private int dotNum = 2;

    public DataCell(String content) {
        this(content, DATA_TYPE_TEXT);
    }

    public DataCell(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public DataCell() {
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param _content the content to set
     */
    public void setContent(String _content) {
        if (_content == null) {
            _content = "";
        }
        this.content = _content;
    }

    public void setObjectContent(Object _content) {
        if (_content == null) {
            _content = "";
        }
        this.content = _content.toString();
        if (_content instanceof String) {
            this.setType(DATA_TYPE_TEXT);
        } else if (_content instanceof Number) {
            if (_content instanceof Integer || _content instanceof Long || _content instanceof BigInteger) {
                this.setType(DATA_TYPE_INTEGER);
            } else if (_content instanceof BigDecimal && BigDecimalUtil.isInteger((BigDecimal) _content)) {
                this.setType(DATA_TYPE_INTEGER);
            } else {
                this.setType(DATA_TYPE_NUMERIC);
            }
        } else if (_content instanceof Date) {
            this.setType(DATA_TYPE_DATE);
        } else if (_content instanceof Date) {
            this.setType(DATA_TYPE_TEXT);
        }
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        if (this.type == DATA_TYPE_UNKNOW) {
            this.type = type;
        }
    }

    /**
     * @return the dotNum
     */
    public int getDotNum() {
        return dotNum;
    }

    /**
     * @param dotNum the dotNum to set
     */
    public void setDotNum(int dotNum) {
        this.dotNum = dotNum;
    }
}
