package repositories.unit;

import java.io.FileInputStream;
import org.junit.Assert;
import org.junit.Test;

import entities.Project;
import entities.StaffMember;
import entities.Stream;
import entities.Student;
import repositories.ProjectRepository;
import repositories.StudentRepository;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;

public class StudentRepositoryTest{

    public ProjectRepository setupRepository(){
        ProjectRepository projectRepository = new ProjectRepository();
        StaffMember supervisor = new StaffMember();
        Project project = new Project("projectName", Stream.CSDS, supervisor);
        Project project2 = new Project("projectName", Stream.CS, supervisor);
        Project project3 = new Project("project", Stream.DS, supervisor);
        Project project4 = new Project("project", Stream.DS, supervisor);
        Project project5 = new Project("project", Stream.DS, supervisor);
        Project project6 = new Project("projectName", Stream.CS, supervisor);
        Project project7 = new Project("projectName", Stream.CS, supervisor);
        Project project8 = new Project("projectName", Stream.CS, supervisor);
        Project project9 = new Project("projectName", Stream.CSDS, supervisor);
        Project project10 = new Project("projectName", Stream.CSDS, supervisor);
        Project project11 = new Project("projectName", Stream.CSDS, supervisor);
        Project project12 = new Project("projectName", Stream.CSDS, supervisor);
        Project project13 = new Project("projectName", Stream.CS, supervisor);
        Project project14 = new Project("project", Stream.DS, supervisor);
        Project project15 = new Project("project", Stream.DS, supervisor);
        projectRepository.addProject(project);
        projectRepository.addProject(project2);
        projectRepository.addProject(project3);
        projectRepository.addProject(project4);
        projectRepository.addProject(project5);
        projectRepository.addProject(project6);
        projectRepository.addProject(project7);
        projectRepository.addProject(project8);
        projectRepository.addProject(project9);
        projectRepository.addProject(project10);
        projectRepository.addProject(project11);
        projectRepository.addProject(project12);
        projectRepository.addProject(project13);
        projectRepository.addProject(project14);
        projectRepository.addProject(project15);
        return projectRepository;
    }

    @Test 
    public void addStudentFromFileTest(){
        StudentRepository studentRepository = new StudentRepository();
        ProjectRepository projectRepository = setupRepository();
        try {
            FileInputStream file = new FileInputStream("src/main/resources/students.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            // start at 1 because row 0 contains the headers
            for(int i=1;i<4;i++){
                Row row = sheet.getRow(i);
                String cellString;
                Cell currentCell = row.getCell(0);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                String firstName = cellString;

                currentCell = row.getCell(1);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                String surname = cellString;

                currentCell = row.getCell(2);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                Long studentId = Long.parseLong(cellString);

                currentCell = row.getCell(3);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                Stream stream;
                if(cellString=="CS") stream=Stream.CS;
                else if(cellString=="DS") stream=Stream.DS;
                else stream = null;

                Student newStudent = new Student(firstName, surname, studentId, stream, projectRepository);
                studentRepository.addStudent(newStudent);
            }

            Assert.assertEquals("student not added correctly", studentRepository.getSize(), 3);
            
            Assert.assertEquals("student not added correctly", studentRepository.getStudent(0).getFirstName(), "Dudley" );
            Assert.assertEquals("student not added correctly", studentRepository.getStudent(0).getSurname(), "Hackly");

            Assert.assertEquals("student not added correctly", studentRepository.getStudent(1).getFirstName(), "Chaim");
            Assert.assertEquals("student not added correctly", studentRepository.getStudent(1).getSurname(), "MacIver");
        
            Assert.assertEquals("student not added correctly", studentRepository.getStudent(2).getFirstName(), "Adorne");
            Assert.assertEquals("student not added correctly", studentRepository.getStudent(2).getSurname(), "Masedon");
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test 
    public void removeStudentFromFileTest(){
        StudentRepository studentRepository = new StudentRepository();
        ProjectRepository projectRepository = setupRepository();
        try {
            FileInputStream file = new FileInputStream("src/main/resources/students.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            // start at 1 because row 0 contains the headers
            for(int i=1;i<4;i++){
                Row row = sheet.getRow(i);
                String cellString;
                Cell currentCell = row.getCell(0);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                String firstName = cellString;

                currentCell = row.getCell(1);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                String surname = cellString;

                currentCell = row.getCell(2);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                Long studentId = Long.parseLong(cellString);

                currentCell = row.getCell(3);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                Stream stream;
                if(cellString=="CS") stream=Stream.CS;
                else if(cellString=="DS") stream=Stream.DS;
                else stream = null;

                Student newStudent = new Student(firstName, surname, studentId, stream, projectRepository);
                studentRepository.addStudent(newStudent);
            }

            studentRepository.removeStudent(studentRepository.getStudent(2));

            Assert.assertEquals("student not removed correctly", studentRepository.getSize(), 2);
            
            Assert.assertEquals("student not removed correctly", studentRepository.getStudent(0).getFirstName(), "Dudley" );
            Assert.assertEquals("student not removed correctly", studentRepository.getStudent(0).getSurname(), "Hackly");

            Assert.assertEquals("student not removed correctly", studentRepository.getStudent(1).getFirstName(), "Chaim");
            Assert.assertEquals("student not removed correctly", studentRepository.getStudent(1).getSurname(), "MacIver");

            Assert.assertEquals("student not removed correctly", studentRepository.getStudent(2), null);
        
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}