//
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class ExcelWriterReader {
//
//    private String filepath;
//
//    public ExcelWriterReader(String fileName) {
//        filepath = initializeFile(fileName);
//    }
//
//    private String initializeFile(String fileName) {
//        String currentUsersHomeDir = System.getProperty("user.home");
//        return currentUsersHomeDir + File.separator + fileName;
//    }
//
//
//    public  void writeToExcelFile(Map<Position, String> newData) {
//        List<List<String>> currentData = readFromExcelFile();
//        List<List<String>> data = addNewDataToCurrent(currentData,newData);
//        try (FileOutputStream outputStream = new FileOutputStream(filepath)) {
//            XSSFWorkbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("Sheet1");
//            for (int i = 0; i < data.size(); i++) {
//                Row row = sheet.createRow(i);
//                List<String> rowData = data.get(i);
//                for (int j = 0; j < rowData.size(); j++) {
//                    Cell cell = row.createCell(j);
//                    cell.setCellValue(rowData.get(j));
//                }
//            }
//            workbook.write(outputStream);
//            workbook.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    public List<List<String>> readFromExcelFile() {
//        List<List<String>> excelData = new ArrayList<>();
//        try (FileInputStream inputStream = new FileInputStream(filepath)) {
//            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(0);
//            int numberOfRows = sheet.getLastRowNum();
//            int numberOfColumns = sheet.getRow(1).getLastCellNum();
//            for (int r = 0; r <= numberOfRows; r++) {
//                Row row = sheet.getRow(r);
//                if (row == null)
//                    row = sheet.createRow(r);
//                List<String> currentRow = new ArrayList<>();
//                for (int c = 0; c < numberOfColumns; c++) {
//                    Cell cell = row.getCell(c);
//                    if (cell == null || cell.getCellType() == CellType.BLANK) {
//                        currentRow.add("");
//                    } else {
//                        cell.setCellType(CellType.STRING);
//                        currentRow.add(cell.getStringCellValue());
//                    }
//
//                }
//                excelData.add(currentRow);
//            }
//            workbook.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return excelData;
//    }
//
//
//
//    private List<List<String>> addNewDataToCurrent(List<List<String>> currentData ,Map<Position, String> newData){
//        for (Map.Entry<Position, String> entry : newData.entrySet()) {
//            int currentRow = entry.getKey().getRow();
//            int currentColumn = entry.getKey().getColumn();
//            String currentValue  = entry.getValue();
//            currentData.get(currentRow).set(currentColumn,currentValue);
//        }
//        return currentData;
//    }
//
//
//}
//
//
//
//
//
//
//
//
