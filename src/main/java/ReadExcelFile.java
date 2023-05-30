import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadExcelFile {

    private List<String[]> file;

    public List<String[]> getFile() {
        return file;
    }

    public void setFile(List<String[]> file) {
        this.file = file;
    }

    public ReadExcelFile(String filePath) {
        List<String[]> data = new ArrayList<>();
        try {
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
                this.file = data;
            }
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
