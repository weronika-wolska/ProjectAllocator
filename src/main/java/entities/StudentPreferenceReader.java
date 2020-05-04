package entities;

import exceptions.InvalidArgumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import repositories.ProjectRepository;


import exceptions.InvalidArgumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import repositories.ProjectRepository;
import repositories.StudentRepository;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class StudentPreferenceReader {
    private StudentRepository students;
    private ProjectRepository projects;

    public StudentPreferenceReader(StudentRepository students, ProjectRepository projects) {
        this.students = students;
        this.projects = projects;
    }

    public StudentRepository getStudents() {
        return students;
    }

    public ProjectRepository getProjects() {
        return projects;
    }

    public void readXLSX(String filePath) throws InvalidArgumentException {
        //System.out.println("In readXLSX");
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum(); // + 1;
            //System.out.println("File input obtained, row count:" + rowCount);
            for (int i = 0; i < students.getSize(); i++) {
                Row row = sheet.getRow(i);
                Student newStudent;
                try {
                    newStudent = parseRowIntoStudent(row);
                    //transferPreferencesBetweenStudents(newStudent, students.getStudent(newStudent));
                    transferPreferencesBetweenStudents(newStudent, students.getStudentById(newStudent.getStudentId()));
                    //System.out.println("Just added prefs for:" + newStudent.getFirstName());
                }
                catch (IllegalArgumentException e) {
                    //System.out.println("Illegal data in StudentPreferenceReader for row:" + i + ", skipping the addition of these preferences to student in question.");
                    //e.printStackTrace();
                }
                //System.out.println("Forming a new StaffMember from row:" + rowNumber + " " + newStaff.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //setPreferencesOfUnpreferringStudents();
    }

    private void transferPreferencesBetweenStudents(Student from, Student to) throws InvalidArgumentException {
        to.setPreferences(from.getPreferences());
    }

    private Student parseRowIntoStudent(Row row) throws InvalidArgumentException {
        //System.out.println("parsing row");
        Student newStudent = new Student(projects);
        Cell currentCell;

        String cellString0;
        try {
            currentCell = row.getCell(0);
            cellString0 = currentCell.getStringCellValue();
        }
        catch (NullPointerException npe) {
            cellString0 = "";
        }
        //System.out.println(cellString0);
        newStudent.setFirstName(cellString0);

        String cellString1;
        try {
            currentCell = row.getCell(1);
            cellString1 = currentCell.getStringCellValue();
        }
        catch (NullPointerException npe) {
            cellString1 = "";
        }
        //System.out.println(cellString1);
        newStudent.setSurname(cellString1);

        long cellValue2;
        try {
            currentCell = row.getCell(2);
            cellValue2 = (long) currentCell.getNumericCellValue();
        }
        catch (Exception e) {
            cellValue2 = 0;
        }
        //System.out.println(cellValue2);
        newStudent.setStudentId(cellValue2);

        ArrayList<Project> preferences = new ArrayList<>();
        ArrayList<String> preferenceNames = new ArrayList<>();
        String cellString;
        for(int i = 0; i < 10; ++i) {
            try {
                currentCell = row.getCell(3 + i);
                cellString = currentCell.getStringCellValue();

            } catch (NullPointerException npe) {
                cellString = "";
            }
            preferenceNames.add(cellString);
        }
        //System.out.println(cellString3);
        for (String projectName :
                preferenceNames) {
            if (projects.hasProjectByName(projectName)) {
                preferences.add(projects.getProjectByName(projectName));
            }
        }
        newStudent.setPreferences(preferences); // should randomize leftover slots

        if(students.hasStudentById(newStudent.getStudentId())) {
            return newStudent;
        }
        else throw new IllegalArgumentException();
    }
/*
    private void setPreferencesOfUnpreferringStudents() throws InvalidArgumentException {
        ArrayList<Student> boringStudents = students.getStudentsWithoutPreferences();
        for (Student student :
                boringStudents) {
            student.setPreferences(new ArrayList<>());
        }
    } */
}
