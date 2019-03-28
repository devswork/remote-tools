package com.github.devswork.util.excel;


import com.github.devswork.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileToExcelManagerImpl4POI implements FileToExcelManager {


    public void save(HttpServletRequest request, HttpServletResponse response, String fileName, DataRecord... dataRecords) throws Exception {

        if (fileName.indexOf(".xls") == -1) {
            fileName += ".xls";
        }

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
                log.debug(e.getMessage());
            } else {
                log.error("output excel exception", e);
            }
        } finally {
            wb = null;
        }
    }

    private HSSFWorkbook doSheet(DataRecord... dataRecords) throws Exception {

        if (dataRecords == null) {
            throw new IllegalArgumentException("can not create sheet");
        }
        int sheetNum = dataRecords.length;
        if (sheetNum == 0) {
            throw new IllegalArgumentException("can not create sheet");
        }


        HSSFSheet[] sheets = new HSSFSheet[sheetNum];
        DataRecord dataRecord = null;
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFCellStyle styleTitle = wb.createCellStyle();
        HSSFCellStyle styleColumn = wb.createCellStyle();
        HSSFCellStyle styleContentText = wb.createCellStyle();
        HSSFCellStyle styleContentBlank = wb.createCellStyle();
        HSSFCellStyle styleContentDate = wb.createCellStyle();
        HSSFCellStyle styleContentDatetime = wb.createCellStyle();
        HSSFCellStyle styleContentNumeric = wb.createCellStyle();
        HSSFCellStyle styleContentInteger = wb.createCellStyle();

        this.initStyle(wb, styleTitle, styleColumn, styleContentText, styleContentDate, styleContentDatetime, styleContentNumeric, styleContentInteger);


        for (int i = 0; i < sheetNum; i++) {
            dataRecord = dataRecords[i];
            if (dataRecord == null) {
                throw new Exception("DataRecord is disabled");
            }

            sheets[i] = wb.createSheet();
            HSSFSheet sheet = sheets[i];
            sheet.setGridsPrinted(true);
            sheet.setHorizontallyCenter(true);
            sheet.setAutobreaks(true);

            wb.setSheetName(i, dataRecord.getSheetName());

            String[] columnName = dataRecord.getColumnName();
            int colunmLength = 0;
            if (columnName != null) {
                colunmLength = columnName.length;
            }

            int rowNumber = 0;

            HSSFRow row = sheet.createRow(rowNumber++);
            row.setHeightInPoints(25);
            HSSFCell cell = row.createCell(0);
            if (colunmLength > 0) {
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (colunmLength - 1)));
            }
            cell.setCellValue(dataRecord.getTitle());
            cell.setCellStyle(styleTitle);

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

            boolean isAllNull = true;
            for (int c = 0; c < colunmLength; c++) {
                if (StringUtils.isNotBlank(columnName[c])) {
                    isAllNull = false;
                    break;
                }
            }
            if (!isAllNull) {
                row = sheet.createRow(rowNumber++);
                row.setHeightInPoints(20);
                for (int c = 0; c < colunmLength; c++) {
                    cell = row.createCell(c);
                    cell.setCellValue(columnName[c]);
                    cell.setCellStyle(styleColumn);
                    cell.setAsActiveCell();
                }
            }

            short[] columnWith = dataRecord.getColumnWith();
            if (columnWith != null) {
                for (int j = 0; j < columnWith.length; j++) {
                    sheet.setColumnWidth(j, new Integer(columnWith[j] * 200));
                }
            }

            for (int index = 0; index < dataRecord.getRow().length; index++) {
                row = sheet.createRow(rowNumber++);
                row.setHeightInPoints(20);
                DataCell[] dataCells = dataRecord.getRow()[index].getCell();

                for (int col = 0; col < dataCells.length; col++) {
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

                HSSFPrintSetup print = sheet.getPrintSetup();
                sheet.setAutobreaks(true);
                print.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
                print.setFitHeight((short) 1);
                print.setFitWidth((short) 1);
            }
        }

        return wb;
    }

    private void initStyle(HSSFWorkbook wb, HSSFCellStyle styleTitle, HSSFCellStyle styleColumn,
                           HSSFCellStyle styleContentText, HSSFCellStyle styleContentDate, HSSFCellStyle styleContentDatetime,
                           HSSFCellStyle styleContentNumeric, HSSFCellStyle styleContentInteger) {
        HSSFDataFormat format = wb.createDataFormat();

        HSSFFont fontTitle = wb.createFont();
        fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontTitle.setFontHeightInPoints((short) 16);

        styleTitle.setFont(fontTitle);
        styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleTitle.setDataFormat(format.getFormat("text"));

        HSSFFont fontCulumn = wb.createFont();
        fontCulumn.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        styleColumn.setFont(fontCulumn);
        styleColumn.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleColumn.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleColumn.setDataFormat(format.getFormat("text"));

        HSSFFont fontContent = wb.createFont();

        styleContentText.setFont(fontContent);
        styleContentText.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleContentText.setHidden(true);
        styleContentText.setWrapText(true);
        styleContentText.setDataFormat(format.getFormat("text"));

        styleContentDate.setFont(fontContent);
        styleContentDate.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleContentDate.setHidden(true);
        styleContentDate.setWrapText(true);
        styleContentDate.setDataFormat(format.getFormat("yyyy-m-d"));

        styleContentDatetime.setFont(fontContent);
        styleContentDatetime.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleContentDatetime.setHidden(true);
        styleContentDatetime.setWrapText(true);
        styleContentDatetime.setDataFormat(format.getFormat("yyyy-m-d H:mm:ss"));

        styleContentNumeric.setFont(fontContent);
        styleContentNumeric.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleContentNumeric.setHidden(true);
        styleContentNumeric.setWrapText(true);
        styleContentNumeric.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        styleContentInteger.setFont(fontContent);
        styleContentInteger.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleContentInteger.setHidden(true);
        styleContentInteger.setWrapText(true);
        styleContentInteger.setDataFormat(format.getFormat("0"));
    }

    public List<List<String>> readExcel(File file) throws Exception {
        List<List<List<String>>> data = readExcelBySheets(file, 0);

        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }

        return null;
    }

    public List<List<String>> readExcel(File file, String type) throws Exception {
        List<List<List<String>>> data = readExcelBySheets(file, type, 0);

        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }

        return null;
    }

    public List<List<List<String>>> readExcelBySheets(File file, int... sheetpages) throws Exception {
        return readExcelBySheets(file, null, sheetpages);
    }

    public List<List<List<String>>> readExcelBySheets(File file, String type, int... sheetpages) throws Exception {
        if (!file.exists()) {
            throw new FileNotFoundException("file not existed");
        }

        if (sheetpages == null || sheetpages.length < 1) {
            throw new FileNotFoundException("have no sheet");
        }

        List<List<List<String>>> excellists = new ArrayList<List<List<String>>>();

        InputStream in = null;
        try {
            Workbook wb = null;
            try {
                in = new FileInputStream(file);
                wb = new XSSFWorkbook(in);
            } catch (Exception e) {
                in = new FileInputStream(file);
                wb = new HSSFWorkbook(in);
            }

            for (int i = 0; i < sheetpages.length; i++) {
                List<List<String>> excellist = new ArrayList<List<String>>();

                Sheet sheet = null;

                try {
                    sheet = wb.getSheetAt(sheetpages[i]);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }

                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();

                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberFormat.setGroupingUsed(false);
                for (int s = firstRow; s <= lastRow; s++) {
                    List<String> rowlist = new ArrayList<String>();
                    Row row = sheet.getRow(s);
                    if (row != null) {
                        short firstcell = row.getFirstCellNum();
                        short lastcell = row.getLastCellNum();

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
                                e.printStackTrace();
                            }

                            String cellValue = "";

                            if (cell != null) {

                                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                    cellValue = cell.getStringCellValue();
                                }

                                else if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    cellValue = DateUtil.getStandard19DateAndTime(cell.getDateCellValue());
                                }

                                else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                    cellValue = numberFormat.format(cell.getNumericCellValue());
                                }

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
           e.printStackTrace();
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