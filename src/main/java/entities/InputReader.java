package entities;

import exceptions.InvalidArgumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class InputReader {
   /* private ArrayList<Student> students;
    private ArrayList<Project> projects;

    public void readXLSX( String filePath) {
        //System.out.println("In readXLSX");
        students = new ArrayList<>();
        projects = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            //System.out.println("File input obtained, random rows:" + Arrays.toString(relevantRowNumbers));
            for (int i = 0; i < rowCount; ++i) {
                Row row = sheet.getRow(i);
                Student newStudent = parseRowIntoStudent(row);
                if (newStudent != null) {
                    students.add(newStudent);
                }
                //System.out.println("Forming a new StaffMember from row:" + rowNumber + " " + newStaff.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StaffMember parseRowIntoStudent(Row row) throws InvalidArgumentException {
        //System.out.println("parsing row");
        Student newStudent = new Student();
        Cell currentCell;

        String cellString0;
        currentCell = row.getCell(0);
        try {
            if(currentCell == null) cellString0 = "";
            else cellString0 = currentCell.getStringCellValue();
        }
        catch (Exception e) {
            cellString0 = "";
        }
        //System.out.println(cellString0);
        newStudent.setFirstName(cellString0);

        String cellString1;
        currentCell = row.getCell(1);try {
            if(currentCell == null) cellString1 = "";
            else cellString1 = currentCell.getStringCellValue();
        }
        catch (Exception e) {
            cellString1 = "";
        }
        //System.out.println(cellString1);
        newStudent.setSurname(cellString1);

        long cellValue2;
        currentCell = row.getCell(2);
        try {
            if(currentCell == null) cellValue2 = 0;
            else cellValue2 = (long) currentCell.getNumericCellValue();
        }
        catch (Exception e) {
            cellValue2 = 0;
        }
        //System.out.println(cellValue2);
        newStudent.setStudentId(cellValue2);

        ArrayList<String> preferences = new ArrayList<>();
        String cellString;
        for(int i = 0; i < 10; ++i) {
            currentCell = row.getCell(3 + i);
            try {
                if(currentCell == null) {
                    break;
                }
                else {
                    cellString = currentCell.getStringCellValue();
                    preferences.add(cellString);
                }

            } catch (Exception e) {
                break;
            }
        }
        //System.out.println(cellString3);
        for (String projectName :
                preferences) {
            if (projectName.trim() != "") {
                Project newProject = new Project(projectName);
            }
        }
        newStudent.setPreferences(preferences);

        if(createsStaffMembersProjects(newStaff) && !staffHasNullAttributes(cellString0, cellString1, cellString2)) {
            return newStaff;
        }
        return null;
    } */
}
