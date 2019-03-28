package com.github.devswork.util.excel;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;


public interface FileToExcelManager {

    default void saveAsCSV(HttpServletRequest request, HttpServletResponse response, String filename, DataRecord dataRecords) {
        if (dataRecords == null) {
            throw new IllegalArgumentException("can not create Excel");
        }
        if (filename.indexOf(".csv") == -1) {
            filename += ".csv";
        }

        StringBuilder str = new StringBuilder();

        String[] columnName = dataRecords.getColumnName();
        for (int k = 0; k < columnName.length; k++) {
            if (k > 0) {
                str.append(",");
            }
            str.append("\"");
            str.append(columnName[k]);
            str.append("\"");
        }

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

            out_.flush();
            out_.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default String toCsvString(String content) {
        if (StringUtils.isBlank(content))
            return "";
        String newContent = content.replaceAll("\"", "\"\"");
        newContent = newContent.trim();
        if ((newContent.matches("[\\d]+") || newContent.matches("[\\d.]+")) && (newContent.length() > 11 || newContent.startsWith("0")))
            return "\t" + newContent;
        return newContent;
    }

    public void save(HttpServletRequest request, HttpServletResponse response, String fileName, DataRecord... dataRecords) throws Exception;

    public List<List<String>> readExcel(File file) throws Exception;

    public List<List<String>> readExcel(File file, String type) throws Exception;

    public List<List<List<String>>> readExcelBySheets(File file, int... sheetpages) throws Exception;
}
