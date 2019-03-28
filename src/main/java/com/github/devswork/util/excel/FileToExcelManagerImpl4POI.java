package com.github.devswork.util.excel;


import com.github.devswork.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel操作工具类（POI）
 *
 * @author linsong
 */
public class FileToExcelManagerImpl4POI implements FileToExcelManager {
    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(FileToExcelManagerImpl4POI.class);

    /*
     * (non-Javadoc)
     * @see FileToExcelManager#save(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String, DataRecord[])
     */
    public void save(HttpServletRequest request, HttpServletResponse response, String fileName, DataRecord... dataRecords) throws Exception {
        // 文件名格式化
        if (fileName.indexOf(".xls") == -1) {
            fileName += ".xls";
        }
        //创建Excel对象
        HSSFWorkbook wb = doSheet(dataRecords);


        response.setCharacterEncoding("UTF-8");
        try {
            response.setContentType("application/vnd.ms-excel; charset=" + "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes("gbk"), "iso8859-1") + "\"");
            ServletOutputStream out_ = response.getOutputStream();
            wb.write(out_);
            out_.flush();
            out_.close();
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("ClientAbortException")) {
                log.debug("用户关闭下载窗口: " + e.getMessage());
            } else {
                log.error("导出Excel文件异常", e);
            }
        } finally {
            wb = null;
        }
    }

    /**
     * 创建一个或多个工作表
     *
     * @param dataRecords 数据记录
     * @return Excel对象
     * @throws Exception
     */
    private HSSFWorkbook doSheet(DataRecord... dataRecords) throws Exception {
        // 校验数据对象
        if (dataRecords == null) {
            throw new IllegalArgumentException("不能创建工作表!");
        }
        int sheetNum = dataRecords.length;
        if (sheetNum == 0) {
            throw new IllegalArgumentException("不能创建工作表!");
        }


        HSSFSheet[] sheets = new HSSFSheet[sheetNum];
        DataRecord dataRecord = null;
        HSSFWorkbook wb = new HSSFWorkbook();

        // 初始化样式
        HSSFCellStyle styleTitle = wb.createCellStyle();
        HSSFCellStyle styleColumn = wb.createCellStyle();
        HSSFCellStyle styleContentText = wb.createCellStyle();
        HSSFCellStyle styleContentBlank = wb.createCellStyle();
        HSSFCellStyle styleContentDate = wb.createCellStyle();
        HSSFCellStyle styleContentDatetime = wb.createCellStyle();
        HSSFCellStyle styleContentNumeric = wb.createCellStyle();
        HSSFCellStyle styleContentInteger = wb.createCellStyle();

        this.initStyle(wb, styleTitle, styleColumn, styleContentText, styleContentDate, styleContentDatetime, styleContentNumeric, styleContentInteger);

        // 遍历sheet
        for (int i = 0; i < sheetNum; i++) { // 多个sheet
            dataRecord = dataRecords[i];
            if (dataRecord == null) {
                throw new Exception("DataRecord is disabled");
            }

            sheets[i] = wb.createSheet();
            HSSFSheet sheet = sheets[i];
            sheet.setGridsPrinted(true); // 打印网格线
            sheet.setHorizontallyCenter(true); // 水平居中
            sheet.setAutobreaks(true);

            // 获取工作表名称
            wb.setSheetName(i, dataRecord.getSheetName()); // 设置表的名字

            //获取列数
            String[] columnName = dataRecord.getColumnName();
            int colunmLength = 0;
            if (columnName != null) {
                colunmLength = columnName.length;
            }

            int rowNumber = 0;

            // 添加主标题
            HSSFRow row = sheet.createRow(rowNumber++);
            row.setHeightInPoints(25);
            HSSFCell cell = row.createCell(0);
            if (colunmLength > 0) {
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (colunmLength - 1)));
            }
            cell.setCellValue(dataRecord.getTitle());
            cell.setCellStyle(styleTitle);

            // 添加子标题
            if (dataRecord.getSubTitle() != null) {
                row = sheet.createRow(rowNumber++);
                row.setHeightInPoints(25);
                cell = row.createCell(0);
                if (colunmLength > 0) {
                    sheet.addMergedRegion(new CellRangeAddress(rowNumber - 1, rowNumber - 1, 0, (colunmLength - 1)));
                }
                cell.setCellValue(dataRecord.getSubTitle());
                cell.setCellStyle(styleContentText);
            }

            // 添加表头
            boolean isAllNull = true;// 标识表头是否全为NULL
            for (int c = 0; c < colunmLength; c++) {
                if (StringUtils.isNotBlank(columnName[c])) {
                    isAllNull = false;
                    break;
                }
            }
            if (!isAllNull) {// 如果表头全为NULL，则不输出表头
                row = sheet.createRow(rowNumber++);
                row.setHeightInPoints(20);
                for (int c = 0; c < colunmLength; c++) {
                    cell = row.createCell(c);
                    cell.setCellValue(columnName[c]);
                    cell.setCellStyle(styleColumn);
                    cell.setAsActiveCell();
                }
            }

            // 列宽度
            short[] columnWith = dataRecord.getColumnWith();
            if (columnWith != null) {
                for (int j = 0; j < columnWith.length; j++) {
                    sheet.setColumnWidth(j, new Integer(columnWith[j] * 200));
                }
            }
            // else{//设置自动适应宽度
            // for (int c = 0; c < colunmLength; c++) {
            // sheet.autoSizeColumn(c);
            // }
            // }

            // 添加数据
            for (int index = 0; index < dataRecord.getRow().length; index++) { // 多行数据
                row = sheet.createRow(rowNumber++);
                row.setHeightInPoints(20);
                DataCell[] dataCells = dataRecord.getRow()[index].getCell();

                for (int col = 0; col < dataCells.length; col++) { // 一行数据多单元格
                    cell = row.createCell(col);
                    DataCell dataCell = dataCells[col];
                    if (dataCell == null) {
                        continue;
                    }
                    switch (dataCell.getType()) {
                        case DataCell.DATA_TYPE_TEXT:
                            cell.setCellStyle(styleContentText);
                            cell.setCellValue(dataCell.getContent());
                            break;
                        case DataCell.DATA_TYPE_BLANK:
                            cell.setCellStyle(styleContentBlank);
                            cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
                            cell.setCellValue(dataCell.getContent());
                            break;
                        case DataCell.DATA_TYPE_DATE:
                            cell.setCellStyle(styleContentDate);
                            cell.setCellValue(dataCell.getContent());
                            break;
                        case DataCell.DATA_TYPE_DATETIME:
                            cell.setCellStyle(styleContentDatetime);
                            cell.setCellValue(dataCell.getContent());
                            break;
                        case DataCell.DATA_TYPE_NUMERIC:
                            cell.setCellStyle(styleContentNumeric);
                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                            if (StringUtils.isBlank(dataCell.getContent())) {
                                cell.setCellValue("");
                            } else {
                                try {
                                    cell.setCellValue(Double.parseDouble(dataCell.getContent()));
                                } catch (NumberFormatException e) {
                                    cell.setCellValue(dataCell.getContent());
                                }
                            }

                            break;
                        case DataCell.DATA_TYPE_INTEGER:
                            cell.setCellStyle(styleContentInteger);
                            if (StringUtils.isBlank(dataCell.getContent())) {
                                cell.setCellValue("");
                            } else {
                                try {
                                    cell.setCellValue(Long.parseLong(dataCell.getContent()));
                                } catch (NumberFormatException e) {
                                    cell.setCellValue(dataCell.getContent());
                                }
                            }
                            break;
                        default:
                            cell.setCellStyle(styleContentText);
                            cell.setCellValue(dataCell.getContent());
                            break;
                    }
                }
                // 打印属性
                HSSFPrintSetup print = sheet.getPrintSetup();
                sheet.setAutobreaks(true);
                print.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
                print.setFitHeight((short) 1);
                print.setFitWidth((short) 1);
            }
        }

        return wb;
    }

    /**
     * 初始化样式
     */
    private void initStyle(HSSFWorkbook wb, HSSFCellStyle styleTitle, HSSFCellStyle styleColumn,
                           HSSFCellStyle styleContentText, HSSFCellStyle styleContentDate, HSSFCellStyle styleContentDatetime,
                           HSSFCellStyle styleContentNumeric, HSSFCellStyle styleContentInteger) {
        HSSFDataFormat format = wb.createDataFormat();

        // 标题字体
        HSSFFont fontTitle = wb.createFont();
        fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontTitle.setFontHeightInPoints((short) 16);
        // 标题样式
        styleTitle.setFont(fontTitle);
        styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleTitle.setDataFormat(format.getFormat("text"));
        // 表头字体
        HSSFFont fontCulumn = wb.createFont();
        // fontCulumn.setFontName("宋体");
        // fontCulumn.setFontHeightInPoints( (short) 9);
        fontCulumn.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 表头样式
        styleColumn.setFont(fontCulumn);
        styleColumn.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleColumn.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleColumn.setDataFormat(format.getFormat("text"));
        // 内容字体
        HSSFFont fontContent = wb.createFont();
        // fontContent.setFontName("宋体");
        // fontContent.setFontHeightInPoints( (short) 12);
        // 内容样式
        // 文本
        styleContentText.setFont(fontContent);
        styleContentText.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleContentText.setHidden(true);
        styleContentText.setWrapText(true);
        styleContentText.setDataFormat(format.getFormat("text"));
        // 日期
        styleContentDate.setFont(fontContent);
        styleContentDate.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleContentDate.setHidden(true);
        styleContentDate.setWrapText(true);
        styleContentDate.setDataFormat(format.getFormat("yyyy-m-d"));
        // 日期+时间
        styleContentDatetime.setFont(fontContent);
        styleContentDatetime.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleContentDatetime.setHidden(true);
        styleContentDatetime.setWrapText(true);
        styleContentDatetime.setDataFormat(format.getFormat("yyyy-m-d H:mm:ss"));
        // 小数
        styleContentNumeric.setFont(fontContent);
        styleContentNumeric.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleContentNumeric.setHidden(true);
        styleContentNumeric.setWrapText(true);
        styleContentNumeric.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        // 整数
        styleContentInteger.setFont(fontContent);
        styleContentInteger.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleContentInteger.setHidden(true);
        styleContentInteger.setWrapText(true);
        styleContentInteger.setDataFormat(format.getFormat("0"));
    }

    /**
     * 读取Excel文件的内容
     *
     * @param file 待读取的文件
     * @return
     */
    public List<List<String>> readExcel(File file) throws Exception {
        List<List<List<String>>> data = readExcelBySheets(file, 0);

        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * @see FileToExcelManager#readExcel(java.io.File, java.lang.String)
     */
    public List<List<String>> readExcel(File file, String type) throws Exception {
        List<List<List<String>>> data = readExcelBySheets(file, type, 0);

        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * @see FileToExcelManager#readExcelBySheets(java.io.File, int[])
     */
    public List<List<List<String>>> readExcelBySheets(File file, int... sheetpages) throws Exception {
        return readExcelBySheets(file, null, sheetpages);
    }

    /**
     * 读取Excel文件的内容
     *
     * @param file       待读取的EXCEL文件
     * @param sheetpages 待读取的EXCEL文件中的工作表 从0开始
     * @returnz
     */
    public List<List<List<String>>> readExcelBySheets(File file, String type, int... sheetpages) throws Exception {
        if (!file.exists()) {
            log.warn("导入的Excel文件不存在!");
            throw new FileNotFoundException("文件不存在");
        }

        if (sheetpages == null || sheetpages.length < 1) {
            log.warn("导入的Excel文件没有工作表!");
            throw new FileNotFoundException("Excel文件没有工作表");
        }

        List<List<List<String>>> excellists = new ArrayList<List<List<String>>>();

        InputStream in = null;
        try {
            Workbook wb = null;
            try {
                //xlsx 再尝试一次
                in = new FileInputStream(file);//读取文件流
                wb = new XSSFWorkbook(in);
            } catch (Exception e) {
                //xls 方式尝试一次
                in = new FileInputStream(file);//读取文件流
                wb = new HSSFWorkbook(in);
            }

            for (int i = 0; i < sheetpages.length; i++) {
                List<List<String>> excellist = new ArrayList<List<String>>();

                Sheet sheet = null;

                try {
                    sheet = wb.getSheetAt(sheetpages[i]);
                } catch (Exception e) {
                    throw new Exception("");
                }

                int firstRow = sheet.getFirstRowNum();//第一行索引
                int lastRow = sheet.getLastRowNum();//最后一行索引

                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberFormat.setGroupingUsed(false);
                //遍历所有行
                for (int s = firstRow; s <= lastRow; s++) {
                    List<String> rowlist = new ArrayList<String>();
                    Row row = sheet.getRow(s);//获取当前行
                    if (row != null) {
                        short firstcell = row.getFirstCellNum();//第一个单元格索引
                        short lastcell = row.getLastCellNum();//最后一个单元格索引

                        for (int k = 0; k < firstcell; k++) {
                            rowlist.add("");
                        }

                        for (int m = firstcell; m <= lastcell; m++) {
                            Cell cell = row.getCell(m);
                            try {
                                if (cell != null && StringUtils.isNotBlank(type)) {
                                    cell.setCellType(Integer.valueOf(type));
                                }
                            } catch (Exception e) {
                                log.error("转换出现问题", e);
                            }

                            String cellValue = "";

                            if (cell != null) {
                                // 字符串型
                                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                    cellValue = cell.getStringCellValue();
                                }
                                // 日期型
                                else if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    cellValue = DateUtil.getStandard19DateAndTime(cell.getDateCellValue());
                                }
                                // 数值型
                                else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                    cellValue = numberFormat.format(cell.getNumericCellValue());
                                }
                                // 公式型
                                else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
                                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                    cellValue = String.valueOf(cell.getNumericCellValue());
                                }
                            }
                            rowlist.add(cellValue);
                        }
                    }
                    excellist.add(rowlist);
                }
                excellists.add(excellist);
            }

            wb.close();
        } catch (Exception e) {
            log.error("导入Excel数据有错:" + e.getMessage(), e);
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        return excellists;
    }
}