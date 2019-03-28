package com.github.devswork.util.excel;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;


public interface FileToExcelManager {
    /**
     * 保存为CSV格式
     *
     * @param request     HttpServletRequest
     * @param response    HttpServletResponse
     * @param filename    文件名称
     * @param dataRecords 数据
     */
    default void saveAsCSV(HttpServletRequest request, HttpServletResponse response, String filename, DataRecord dataRecords) {
        if (dataRecords == null) {
            throw new IllegalArgumentException("不能创建工作表!");
        }
        if (filename.indexOf(".csv") == -1) {
            filename += ".csv";
        }

        StringBuilder str = new StringBuilder();
        // 添加标题
        String[] columnName = dataRecords.getColumnName();
        for (int k = 0; k < columnName.length; k++) {
            if (k > 0) {
                str.append(",");
            }
            str.append("\"");
            str.append(columnName[k]);
            str.append("\"");
        }

        // 添加数据
        DataRow[] rows = dataRecords.getRow();
        for (int k = 0; k < rows.length; k++) {
            str.append("\r\n");
            DataCell[] cells = rows[k].getCell();
            for (int i = 0; i < cells.length; i++) {
                if (i > 0) {
                    str.append(",");
                }
                str.append("\"");
                str.append(toCsvString(cells[i].getContent()));
                str.append("\"");
            }
        }

        response.setCharacterEncoding("UTF-8");
        try {
            response.setContentType("application/vnd.ms-excel; charset=" + "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=\"" + new String(filename.getBytes("gbk"), "iso8859-1") + "\"");
            ServletOutputStream out_ = response.getOutputStream();
            //IOUtils.write(str.toString(), out_);
            out_.flush();
            out_.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出csv格式时，把一个分号转换成两个分号。
     *
     * @param content
     * @return
     */
    default String toCsvString(String content) {
        if (StringUtils.isBlank(content))
            return "";
        String newContent = content.replaceAll("\"", "\"\"");
        newContent = newContent.trim();
        if ((newContent.matches("[\\d]+") || newContent.matches("[\\d.]+")) && (newContent.length() > 11 || newContent.startsWith("0")))
            return "\t" + newContent;
        return newContent;
    }

    /**
     * 保存工作表
     *
     * @param request     HttpServletRequest
     * @param response    HttpServletResponse
     * @param fileName    文件名称
     * @param dataRecords 数据集合
     * @throws Exception
     */
    public void save(HttpServletRequest request, HttpServletResponse response, String fileName, DataRecord... dataRecords) throws Exception;

    /**
     * 读取Excel文件的内容（全部工作表）
     *
     * @param file 待读取的EXCEL文件
     * @return
     */
    public List<List<String>> readExcel(File file) throws Exception;

    /**
     * 读取Excel文件的内容（全部工作表）
     *
     * @param file 待读取的EXCEL文件
     * @param type 所有字段解析的类型
     * @return
     * @throws Exception
     */
    public List<List<String>> readExcel(File file, String type) throws Exception;

    /**
     * 读取Excel文件的内容（指定工作表）
     *
     * @param file       待读取的EXCEL文件
     * @param sheetpages 待读取的EXCEL文件中的工作表 从0开始
     * @return
     */
    public List<List<List<String>>> readExcelBySheets(File file, int... sheetpages) throws Exception;
}
