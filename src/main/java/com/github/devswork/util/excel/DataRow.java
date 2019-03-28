
package com.github.devswork.util.excel;

import java.util.ArrayList;
import java.util.List;


public class DataRow {
    private DataCell[] cell = null;
    private List<DataCell> list = new ArrayList<DataCell>();

    public DataRow() {
    }

    public DataCell[] getCell() {
        Object[] obj = list.toArray();
        int size = obj.length;
        cell = new DataCell[size];

        for (int i = 0; i < size; i++) {
            cell[i] = (DataCell) obj[i];
        }
        return cell;
    }

    public DataRow addDataCell(String content, int ctype){
        addDataCell(content, ctype, 0);
        return this;
    }

    public DataRow addDataCellUnknowType(Object content){
        addDataCell(content, DataCell.DATA_TYPE_UNKNOW, 0);
        return this;
    }

    public void addDataCell(Object content, int ctype, int dotNum){
         DataCell cell= new DataCell(); 
         cell.setObjectContent(content);
         cell.setType(ctype);
         cell.setDotNum(dotNum);
         list.add(cell);
    }
}