package entities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ProjectWriter {

    public void write(String filePath, ArrayList<StaffMember> faculty) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Projects and Supervisors");
        createHeader(sheet);
        int rowNum = 1;
        for (StaffMember staff :
                faculty) {
            for (String projectTitle :
                    staff.getProjects()) {
                Row row = sheet.createRow(rowNum);
                writeRow(row, staff, projectTitle);
                ++rowNum;
            }
        }
        try {
            FileOutputStream outputFile = new FileOutputStream(new File(filePath));
            workbook.write(outputFile);
            outputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeRow(Row row, StaffMember staff, String project) {
        Cell cell = row.createCell(0);
        cell.setCellValue(staff.getName());
        cell = row.createCell(1);
        cell.setCellValue(project);
        cell = row.createCell(2);
        writeStreamCell(staff, cell);
    }

    private void writeStreamCell(StaffMember staff, Cell cell) {
        if(staff.getSpecialFocus() == Stream.DS) {
            cell.setCellValue("DS");
        }
        else if(staff.getSpecialFocus() == Stream.CSDS) {
            cell.setCellValue("CS+DS");
        }
        else {
            cell.setCellValue("CS");
        }
    }

    private void createHeader(XSSFSheet sheet) {
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Supervisor");
        cell = row.createCell(1);
        cell.setCellValue("Project");
        cell = row.createCell(2);
        cell.setCellValue("Special Focus");
    }
}
