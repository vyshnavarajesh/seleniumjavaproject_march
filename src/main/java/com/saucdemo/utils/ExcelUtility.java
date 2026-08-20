package com.saucdemo.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ExcelUtility {

    private static final Logger LOGGER = Logger.getLogger(ExcelUtility.class.getName());

    private XSSFWorkbook workbook;
    private final String filePath;

    public ExcelUtility(String filePath) {
        this.filePath = filePath;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(fis);
            LOGGER.info("Workbook loaded: " + filePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to open workbook: " + filePath, e);
            throw new RuntimeException("Cannot open Excel file: " + filePath, e);
        }
    }

    /**
     * Creates a new blank .xlsx workbook at the given path.
     * Returns an ExcelUtils instance pointing at the new file.
     */
    public static ExcelUtility createWorkbook(String filePath) {
        XSSFWorkbook newWorkbook = new XSSFWorkbook();
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            newWorkbook.write(fos);
            LOGGER.info("New workbook created: " + filePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to create workbook: " + filePath, e);
            throw new RuntimeException("Cannot create Excel file: " + filePath, e);
        } finally {
            try { newWorkbook.close(); } catch (IOException ignored) {}
        }
        return new ExcelUtility(filePath);
    }

    /**
     * Checks if a sheet with the given name exists in the workbook.
     */
    public boolean isSheetExist(String sheetName) {
        return workbook.getSheet(sheetName) != null;
    }

    /**
     * Adds a new sheet to the workbook
     */
    public void addNewSheet(String sheetName) {
        if (isSheetExist(sheetName)) {
            LOGGER.warning("Sheet already exists: " + sheetName);
            return;
        }
        workbook.createSheet(sheetName);
        save();
        LOGGER.info("Sheet added: " + sheetName);
    }

    /**
     * Removes a sheet from the workbook and saves immediately.
     */
    public void removeSheet(String sheetName) {
        int sheetIndex = workbook.getSheetIndex(sheetName);
        if (sheetIndex == -1) {
            LOGGER.warning("Sheet not found, cannot remove: " + sheetName);
            return;
        }
        workbook.removeSheetAt(sheetIndex);
        save();
        LOGGER.info("Sheet removed: " + sheetName);
    }

    /**
     * Returns the number of data rows in a sheet (excludes the header row).
    */
    
    public int getRowCount(String sheetName) {
        XSSFSheet sheet = getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        return rowCount < 1 ? 0 : rowCount; // row 0 = header
    }

    /**
     * Returns the number of columns in the header row of a sheet.
     */
    public int getColumnCount(String sheetName) {
        XSSFSheet sheet = getSheet(sheetName);
        XSSFRow row = sheet.getRow(0);
        if (row == null) return 0;
        return row.getLastCellNum();
    }

    /**
     * Reads the value of a cell.
     */
    public String getCellData(String sheetName, int rowNum, int colNum) {
        XSSFSheet sheet = getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNum);
        if (row == null) return "";

        XSSFCell cell = row.getCell(colNum);
        if (cell == null) return "";

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    /**
     * Writes a string value into a cell
     */
    public void setCellData(String sheetName, int rowNum, int colNum, String value) {
        XSSFSheet sheet = getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        XSSFCell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }
        cell.setCellValue(value);
        save();
        LOGGER.fine("setCellData -> sheet=" + sheetName + " row=" + rowNum + " col=" + colNum + " value=" + value);
    }

    /**
     * Appends a new row at the end of the sheet with data values.
     */
    public void addRow(String sheetName, String[] data) {
        XSSFSheet sheet = getSheet(sheetName);
        int nextRowNum = sheet.getLastRowNum() + 1;
        XSSFRow row = sheet.createRow(nextRowNum);
        for (int i = 0; i < data.length; i++) {
            row.createCell(i).setCellValue(data[i] != null ? data[i] : "");
        }
        save();
        LOGGER.info("Row added at index " + nextRowNum + " in sheet: " + sheetName);
    }

    /**
     * Writes a value into a specific column of an existing row.
     * Creates the row if it does not exist.
     */
    public void addColumn(String sheetName, int rowNum, String value) {
        XSSFSheet sheet = getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        int nextColNum = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
        row.createCell(nextColNum).setCellValue(value != null ? value : "");
        save();
        LOGGER.info("Column added at row=" + rowNum + " col=" + nextColNum + " in sheet: " + sheetName);
    }

 
    /**
     * Highlights a cell with GREEN background (typically used for PASS status).
     */
    public void fillGreenColor(String sheetName, int rowNum, int colNum) {
        applyCellColor(sheetName, rowNum, colNum, IndexedColors.BRIGHT_GREEN);
    }

    /**
     * Highlights a cell with RED background (typically used for FAIL status).
     */
    public void fillRedColor(String sheetName, int rowNum, int colNum) {
        applyCellColor(sheetName, rowNum, colNum, IndexedColors.RED);
    }
 
    /**
     * Saves all changes to the original file path.
     */
    public void save() {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save workbook: " + filePath, e);
            throw new RuntimeException("Cannot save Excel file: " + filePath, e);
        }
    }

    /**
     * Saves to a different file path (Save As).
     */
    public void saveAs(String newFilePath) {
        try (FileOutputStream fos = new FileOutputStream(newFilePath)) {
            workbook.write(fos);
            LOGGER.info("Workbook saved as: " + newFilePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to saveAs: " + newFilePath, e);
            throw new RuntimeException("Cannot save Excel file: " + newFilePath, e);
        }
    }

    /**
     * Closes the workbook 
     */
    public void close() {
        try {
            workbook.close();
            LOGGER.info("Workbook closed: " + filePath);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to close workbook", e);
        }
    }

    private XSSFSheet getSheet(String sheetName) {
        XSSFSheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet not found: " + sheetName);
        }
        return sheet;
    }

    private XSSFCell getOrCreateCell(XSSFSheet sheet, int rowNum, int colNum) {
        XSSFRow row = sheet.getRow(rowNum);
        if (row == null) row = sheet.createRow(rowNum);
        XSSFCell cell = row.getCell(colNum);
        if (cell == null) cell = row.createCell(colNum);
        return cell;
    }

    private void applyCellColor(String sheetName, int rowNum, int colNum, IndexedColors color) {
        XSSFSheet sheet = getSheet(sheetName);
        XSSFCell cell = getOrCreateCell(sheet, rowNum, colNum);

        XSSFCellStyle style = workbook.createCellStyle();

        style.cloneStyleFrom(cell.getCellStyle());
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);
        save();
        LOGGER.fine("Color applied -> sheet=" + sheetName + " row=" + rowNum + " col=" + colNum + " color=" + color.name());
    }
}
