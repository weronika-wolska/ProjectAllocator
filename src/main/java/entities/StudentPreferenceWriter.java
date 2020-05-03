package entities;

import java.util.ArrayList;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import repositories.ProjectRepository;

import java.io.File;
import java.io.FileOutputStream;


public class StudentPreferenceWriter{

    ArrayList<Project> listOfProjects = new ArrayList<Project>();
    ProjectRepository projectRepository = new ProjectRepository();
    ArrayList<Double> projectProbabilities = new ArrayList<Double>();
    int numOfStudents =1;
    Student student;

    public StudentPreferenceWriter(){

    }

    public ProjectRepository write(String filePath, ArrayList<Student> students, int numOfStudents, XSSFSheet projects) throws NullPointerException{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet preferences = workbook.createSheet("Student preferences");
        createHeader(preferences);
        //System.out.println("Header created");
        
        // getting projects into an array list

        
        int numOfProjects = projects.getLastRowNum();
        for (int i = 1; i <= numOfProjects; i++) {
            XSSFRow row = projects.getRow(i);
            String supervisor = row.getCell(0).getStringCellValue();
            String projectName = row.getCell(1).getStringCellValue();
            String stream = row.getCell(2).getStringCellValue();
            Stream projectStream;
            if (stream == "CS") {
                projectStream = Stream.CS;
            } else if (stream == "DS") {
                projectStream = Stream.DS;
            } else if (stream == "CS+DS") {
                projectStream = Stream.CSDS;
            } else {
                projectStream = null;
            }
            //final String probabilty = row.getCell(3).getStringCellValue();
            //final double projectProbability = Double.parseDouble(probabilty);
            double projectProbability = row.getCell(3).getNumericCellValue();
            StaffMember projectSupervisor = new StaffMember();
            projectSupervisor.setName(supervisor);
            Project project = new Project(projectName, projectStream, projectSupervisor);
            listOfProjects.add(i-1, project);
            projectRepository.addProject(project);
            projectProbabilities.add(projectProbability);

        }

        // Assigning projects to students
        int n=0;
        student = students.get(n);
        for (int rowNum = 1; rowNum <= numOfStudents; rowNum++) {
            if(n>=students.size()) break;
            // Student student = students.get(rowNum);
            int numOfProjectsForStudent = 0;
            ArrayList<Project> projectsForStudent = new ArrayList<>(10);
            
           // Row row = preferences.getRow(rowNum);
            while (numOfProjectsForStudent != 10) {
                double random = Math.random();
                double max = projectProbabilities.get(rowNum);
                double min = 0.0;

                for(int i =0;i<projectProbabilities.size();i++){
                    if(random>=min && random<=max){
                        projectsForStudent.add(listOfProjects.get(i));
                        numOfProjectsForStudent++;
                        
                    }
                    double tmp = max;
                    min = max;
                    max = tmp + projectProbabilities.get(i);

                }
            }
           // System.out.println("row: " + rowNum + "student:" + students.get(rowNum-1).getFirstName() + "first preference: " + preferences.getRow(rowNum).getCell(4).getStringCellValue());
            
            Row row = preferences.createRow(rowNum);
            writeRow(row, student, projectsForStudent);
            student = students.get(n);
            n++;
        }
        try {
            FileOutputStream outputFile = new FileOutputStream(new File(filePath));
            workbook.write(outputFile);
            outputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectRepository;        
    }

    // Helper Funtions

    public void createHeader(final XSSFSheet sheet) {
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("First Name");
        cell = row.createCell(1);
        cell.setCellValue("Surname");
        cell = row.createCell(2);
        cell.setCellValue("Student ID");
        cell = row.createCell(3);
        cell.setCellValue("Preference 1");
        cell = row.createCell(4);
        cell.setCellValue("Preference 2");
        cell = row.createCell(5);
        cell.setCellValue("Preference 3");
        cell = row.createCell(6);
        cell.setCellValue("Preference 4");
        cell = row.createCell(7);
        cell.setCellValue("Preference 5");
        cell = row.createCell(8);
        cell.setCellValue("Preference 6");
        cell = row.createCell(9);
        cell.setCellValue("Preference 7");
        cell = row.createCell(10);
        cell.setCellValue("Preference 8");
        cell = row.createCell(11);
        cell.setCellValue("Preference 9");
        cell = row.createCell(12);
        cell.setCellValue("Preference 10");
    }

    public void writeRow( Row row, Student student, ArrayList<Project> projects) {
        Cell cell;
        cell = row.createCell(0);
        cell.setCellValue(student.getFirstName());
        cell = row.createCell(1);
        cell.setCellValue(student.getSurname());
        cell = row.createCell(2);
        cell.setCellValue(student.getStudentId() );
        cell = row.createCell(3);
        cell.setCellValue(projects.get(0).getProjectName());
        cell = row.createCell(4);
        cell.setCellValue(projects.get(1).getProjectName());
        cell = row.createCell(5);
        cell.setCellValue(projects.get(2).getProjectName());
        cell = row.createCell(6);
        cell.setCellValue(projects.get(3).getProjectName());
        cell = row.createCell(7);
        cell.setCellValue(projects.get(4).getProjectName());
        cell = row.createCell(8);
        cell.setCellValue(projects.get(5).getProjectName());
        cell = row.createCell(9);
        cell.setCellValue(projects.get(6).getProjectName());
        cell = row.createCell(10);
        cell.setCellValue(projects.get(7).getProjectName());
        cell = row.createCell(11);
        cell.setCellValue(projects.get(8).getProjectName());
        cell = row.createCell(12);
        cell.setCellValue(projects.get(9).getProjectName());
    }
}