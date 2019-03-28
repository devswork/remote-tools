
package com.github.devswork.util.excel;

import java.util.ArrayList;
import java.util.List;

public class DataRecord {

    private String[] columnName;

    private short[] columnWith;
    private String sheetName;
    private String title;
    private String subTitle;
    private List<DataRow> row = new ArrayList<DataRow>();

    public DataRecord() {
    }

    public String[] getColumnName() {
        return columnName;
    }

    public void setColumnName(String[] columnName) {
        if(columnName.length > Short.MAX_VALUE){
            throw new IllegalArgumentException("columnName length must be < " + Short.MAX_VALUE);
        }

        this.columnName = columnName;
    }

    public short[] getColumnWith() {
        return columnWith;
    }


    public void setColumnWith(short[] columnWith) {
        if(columnWith.length > Short.MAX_VALUE){
            throw new IllegalArgumentException("columnWith length must be <" + Short.MAX_VALUE);
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
