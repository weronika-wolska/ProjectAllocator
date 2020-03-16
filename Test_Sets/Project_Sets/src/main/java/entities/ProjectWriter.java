package entities;

import org.apache.commons.math3.distribution.NormalDistribution;
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
        int projectCountPerStaff;
        int rowNum = 1;

        // writing projects without probabilities of being chosen
        for (StaffMember staff :
                faculty) {
            projectCountPerStaff = 0;
            for (String projectTitle :
                    staff.getProjects()) {
                Row row = sheet.createRow(rowNum);
                writeRow(row, staff, projectTitle);
                ++rowNum;
                ++projectCountPerStaff;
                if (projectCountPerStaff == 3) break;
            }
        }

        // adding probability of being chosen for each project
        int lastRowNum = --rowNum;
        double[] probabilities = calculateProbabilities(lastRowNum);
        writeProbabilities(probabilities, sheet, lastRowNum);

        try {
            FileOutputStream outputFile = new FileOutputStream(new File(filePath));
            workbook.write(outputFile);
            outputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double[] calculateProbabilities(int projectCount) {
        NormalDistribution nb = new NormalDistribution();
        double[] probabilities = new double[projectCount];
        double projectSegment = 8 / (double) projectCount;
        for(int i = 0; i < projectCount; ++i) {
            double from = i * projectSegment - 4;
            double to = ( i + 1 ) * projectSegment - 4;
            if(from == 4)  from = 4.000000001;
            if(to > 4)  to = 4;
            probabilities[i] = nb.probability(from, to);
        }
        return probabilities;
    }

    private void writeProbabilities(double[] probabilities, XSSFSheet sheet, int projectNumber) {
        int probabilityColNum = 3;
        Cell probabilityCell;
        for(int i = 1; i <= projectNumber; ++i) {
            Row row = sheet.getRow(i);
            probabilityCell = row.createCell(probabilityColNum);
            probabilityCell.setCellValue(probabilities[i]);
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
        cell = row.createCell(3);
        cell.setCellValue("Probability");
    }
}
