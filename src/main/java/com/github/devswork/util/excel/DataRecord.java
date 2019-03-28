
package com.github.devswork.util.excel;

import java.util.ArrayList;
import java.util.List;

public class DataRecord {
	/** 列名称  */
    private String[] columnName;
    /** 列宽  */
    private short[] columnWith;
    /** 工作表名  */
    private String sheetName;
    /** 标题 */
    private String title;
    /** 子标题 */
    private String subTitle;
    /** 数据行 */
    private List<DataRow> row = new ArrayList<DataRow>();

    public DataRecord() {
    }

    public String[] getColumnName() {
        return columnName;
    }

    /**
     * 设置列的名称
     * 
     * @param columnName
     */
    public void setColumnName(String[] columnName) {
        if(columnName.length > Short.MAX_VALUE){
            throw new IllegalArgumentException("columnName的长度应该小于 " + Short.MAX_VALUE);
        }

        this.columnName = columnName;
    }

    public short[] getColumnWith() {
        return columnWith;
    }

    /**
     * 设置列的宽度，默认为12
     *
     * @param columnWith
     */
    public void setColumnWith(short[] columnWith) {
        if(columnWith.length > Short.MAX_VALUE){
            throw new IllegalArgumentException("columnWith的长度应该小于 " + Short.MAX_VALUE);
        }
        this.columnWith = columnWith;
    }

    public DataRow[] getRow() {
        Object[] obj = this.row.toArray();
        int size = obj.length;

        DataRow[] row = new DataRow[size];

        for (int i = 0; i < size; i++) {
            row[i] = (DataRow) obj[i];
        }
        return row;
    }

    public void setRow(DataRow[] dataRow) {
        this.row = new ArrayList<DataRow>();
        for (DataRow row : dataRow) {
            this.row.add(row);
        }
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addDataRow(DataRow... dataRow){
        if (dataRow.length == 0) {
            return;
        }
        for (DataRow row : dataRow) {
            this.row.add(row);
        }
    }

    public void addEmptyDataRow(){
        this.row.add(new DataRow());
    }
    
    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
