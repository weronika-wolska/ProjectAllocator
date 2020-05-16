package entities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;

public class CandidateSolutionWriter {

    private CandidateSolution solution;

    public void writeXLSX(String filePath, CandidateSolution solution) {
        this.solution = solution;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Candidate Solution");
        createHeader(sheet);
        int rowNum = 1;
        // writing projects without probabilities of being chosen
        for( Map.Entry entry : (Set<Map.Entry<Student, Project>>) solution.getCandidateSolution().entrySet()) {
            Student keyStudent = (Student) entry.getKey();
            Project valueProject = (Project) entry.getValue();
            Row row = sheet.createRow(rowNum);
            parseRow(row, keyStudent, valueProject);
            ++rowNum;
        }
        try {
            FileOutputStream outputFile = new FileOutputStream(new File(filePath));
            workbook.write(outputFile);
            outputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseRow(Row row, Student student, Project project) {
        Cell cell = row.createCell(0);
        cell.setCellValue(student.getStudentId());
        cell = row.createCell(1);
        cell.setCellValue(student.getFirstName());
        cell = row.createCell(2);
        cell.setCellValue(student.getSurname());
        cell = row.createCell(3);
        cell.setCellValue(student.getStream().getStreamString());
        cell = row.createCell(4);
        cell.setCellValue(project.getProjectName());
        cell = row.createCell(5);
        cell.setCellValue(project.getSupervisor().getName());
        cell = row.createCell(6);
        cell.setCellValue(solution.getStudentsSatisfaction(student));
    }

    public void createHeader(XSSFSheet sheet) {
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Student Id");
        cell = row.createCell(1);
        cell.setCellValue("First Name");
        cell = row.createCell(2);
        cell.setCellValue("Surname");
        cell = row.createCell(3);
        cell.setCellValue("Stream");
        cell = row.createCell(4);
        cell.setCellValue("Project");
        cell = row.createCell(5);
        cell.setCellValue("Supervisor");
        cell = row.createCell(6);
        cell.setCellValue("Satisfaction /10");
    }
}

