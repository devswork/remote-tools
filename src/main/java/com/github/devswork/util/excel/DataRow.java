
package com.github.devswork.util.excel;

import java.util.ArrayList;
import java.util.List;


public class DataRow {
	/** 数据单元格数组 */
    private DataCell[] cell = null;
    /** 数据单元格列表 */
    private List<DataCell> list = new ArrayList<DataCell>();

    public DataRow() {
    }

    /**
     * 获取行单元格集合
     * @return
     */
    public DataCell[] getCell() {
        Object[] obj = list.toArray();
        int size = obj.length;
        cell = new DataCell[size];

        for (int i = 0; i < size; i++) {
            cell[i] = (DataCell) obj[i];
        }
        return cell;
    }
    
    /**
     * @param content 单元格内容
     * @param ctype 内容类型<br/>
     *      0: 小数<br/>
     *      1: 文本<br/>
     *      5: 日期<br/>
     *      6: 日期时间<br/>
     *      7: 整数
     */
    public DataRow addDataCell(String content, int ctype){
        addDataCell(content, ctype, 0);
        return this;
    }

    public DataRow addDataCellUnknowType(Object content){
        addDataCell(content, DataCell.DATA_TYPE_UNKNOW, 0);
        return this;
    }
    
    /**
     * @param content 单元格内容
     * @param ctype 内容类型<br/>
     *      0: 小数<br/>
     *      1: 文本<br/>
     *      5: 日期<br/>
     *      6: 日期时间<br/>
     *      7: 整数
     */
    public void addDataCell(Object content, int ctype, int dotNum){
         DataCell cell= new DataCell(); 
         cell.setObjectContent(content);
         cell.setType(ctype);
         cell.setDotNum(dotNum);
         list.add(cell);
    }
}