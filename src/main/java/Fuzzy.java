import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Fuzzy implements ActionListener {
    private JPanel rootPanel;
    private JButton browseButton1;
    private JButton browseButton;
    private JPanel Title;
    private JPanel Create_File_Panel;
    private JPanel Choose_File_2;
    private JPanel Choose_File_1;
    private JButton Start_Button;
    private JButton Undo_Button;
    private JTextField textField1;
    private JTextField textField2;
    private JList ListFile1;
    private JList ListFile2;
    private JList list3;
    private JLabel File1_Upload;
    private JLabel File2_Upload;

    private ReadExcelFile file1;
    private ReadExcelFile file2;


    private void createUIComponents() {

        // TODO: place custom component creation code here
    }

    public Fuzzy() {
        browseButton.addActionListener(this);
        browseButton1.addActionListener(this);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(Fuzzy.this.getRootPanel());
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Utilities.filePath = file.getAbsolutePath();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(Utilities.filePath));
                writer.write("Data to be saved in the file.");
                writer.close();
                JOptionPane.showMessageDialog(null, "Data saved successfully!");
                String[] path = Utilities.filePath.trim().split("\\\\");
                String fileName = path[path.length - 1];
                if (e.getActionCommand().equals("Browse First File")) {
//                    this.file1 = new ReadExcelFile(file.getAbsolutePath());
                    File1_Upload.setText(fileName);
                } else if (e.getActionCommand().equals("Browse Second File")) {
//                    this.file2= new ReadExcelFile(file.getAbsolutePath());
                    File2_Upload.setText(fileName);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while saving the data.");
            }
        }
    }
}
