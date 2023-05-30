import org.apache.commons.text.similarity.JaccardSimilarity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TryThred {
    static double bestSimilarityScore;

    public static void main(String[] args) throws IOException, InterruptedException {


        String file1Path = "C:\\Users\\97254\\Desktop\\Demoxl1.xlsx";
        String file2Path = "C:\\Users\\97254\\Desktop\\Demoxl11.xlsx";
        String newFilePath = "newFile.xlsx";
        List<String[]> file1Data = readExcelFile(file1Path);
        List<String[]> file2Data = readExcelFile(file2Path);
        List<String[]> newData = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(10); // Use a thread pool with 10 threads
        for (String[] file1Record : file1Data) {
            executor.execute(() -> {
                bestSimilarityScore = 0;
                String[] bestMatch = null;
                for (String[] file2Record : file2Data) {
                    double similarityScore = calculateSimilarityScore(file1Record, file2Record);
                    if (bestMatch == null || similarityScore > bestSimilarityScore) {
                        bestSimilarityScore = similarityScore;
                        bestMatch = file2Record;
//                        file2Data.remove(file2Record);
                    }
                }
                // Add the best match to the new data
                String[] newRow = new String[file1Record.length + bestMatch.length + 1];
                System.arraycopy(file1Record, 0, newRow, 0, file1Record.length);
                System.arraycopy(bestMatch, 0, newRow, file1Record.length, bestMatch.length);
                newRow[file1Record.length + bestMatch.length] = String.valueOf(bestSimilarityScore);
//                file1Data.remove(file1Record);
                synchronized (newData) {

                    newData.add(newRow);
                }
                bestSimilarityScore = 0;
            });
        }

        executor.shutdown();
//        executor.awaitTermination(1, TimeUnit.HOURS);
        writeExcelFile(newFilePath, newData);
    }

    private synchronized static List<String[]> readExcelFile(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(filePath);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            String[] rowData = new String[row.getLastCellNum()];
            for (int i = 0; i < rowData.length; i++) {
                Cell cell = row.getCell(i);
                if (cell != null) {
                    rowData[i] = cell.toString().trim();
                }
            }
            data.add(rowData);
        }
        workbook.close();
        return data;
    }

    private synchronized static double calculateSimilarityScore(String[] record1, String[] record2) {
        // Calculate similarity score using Jaccard similarity
        JaccardSimilarity similarity = new JaccardSimilarity();
        double score = 0;
        int count = 0;
        for (int i = 0; i < record1.length; i++) {
            if (record1[i] != null && record2[i] != null) {
                score += similarity.apply(record1[i], record2[i]);
                count++;
            }
        }
        if (count > 0) {
            score /= count; // Average similarity score
        }
        return score;
    }

    private static void writeExcelFile(String filePath, List<String[]> data) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Results");
        int rowIndex = 0;
        for (String[] rowData : data) {
            Row row = sheet.createRow(rowIndex++);
            int cellIndex = 0;
            for (String cellData : rowData) {
                Cell cell = row.createCell(cellIndex++);
                cell.setCellValue(cellData);
            }
        }
        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
