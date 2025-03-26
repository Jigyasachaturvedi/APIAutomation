package com.api.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {
    public Workbook workbook;
    public Sheet sheet;

    // Constructor to initialize workbook and sheet
    public ExcelUtils(String filePath, String sheetName) throws IOException {
        if (filePath == null) {
            throw new IllegalArgumentException("File path is null!");
        }

        System.out.println("Opening file: " + filePath); // Debugging line

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found at: " + filePath);
        }

        FileInputStream fis = new FileInputStream(file);
        this.workbook = new XSSFWorkbook(fis);

        this.sheet = workbook.getSheet(sheetName);
        if (this.sheet == null) {
            throw new IllegalArgumentException("Sheet not found: " + sheetName);
        }
    }

    // Load test case mappings based on TCID
    public Map<String, Map<String, String>> loadTestCaseMappings() {
        Map<String, Map<String, String>> testCaseMappings = new HashMap<>();

        // Get the header row for column names
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new RuntimeException("Header row is missing in the Excel sheet");
        }

        // Map column indices to headers
        Map<Integer, String> columnHeaders = new HashMap<>();
        for (Cell cell : headerRow) {
            columnHeaders.put(cell.getColumnIndex(), cell.getStringCellValue());
        }

        // Iterate through rows to populate test case mappings
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            // Use the first column (assumed TCID) as the key
            Cell idCell = row.getCell(0);
            if (idCell == null) continue;

            String testCaseId = cellToString(idCell);
            Map<String, String> testCaseData = new HashMap<>();

            // Populate data for the test case
            for (Cell cell : row) {
                String header = columnHeaders.get(cell.getColumnIndex());
                String value = cellToString(cell);
                testCaseData.put(header, value);
            }

            testCaseMappings.put(testCaseId, testCaseData);
        }

        return testCaseMappings;
    }

    // Convert cell content to a string
    private String cellToString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    // Close the workbook
    public void close() throws IOException {
        workbook.close();
    }
}