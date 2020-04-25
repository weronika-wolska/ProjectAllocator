package applications;

import entities.ProjectWriter;
import entities.StaffMember;
import entities.StaffReader;
import entities.StudentPreferenceWriter;
import entities.StudentReader;
import exceptions.InvalidArgumentException;
import repositories.ProjectRepository;
import repositories.StaffRepository;
import entities.*;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CreateProjectSetsApp {

    public static void main(String[] args) throws FileNotFoundException, IOException, InvalidArgumentException{
        StaffReader reader = new StaffReader();
        ProjectWriter writer = new ProjectWriter();
        ProjectReader projectReader = new ProjectReader();
        ProjectRepository projectRepository;
        StaffRepository staffRepository;
        

        reader.readXLSX(30, "src/main/resources/staff.xlsx");
        writer.write("src/main/resources/projects60.xlsx",reader.getFaculty());

        reader.readXLSX(60, "src/main/resources/staff.xlsx");
        writer.write("src/main/resources/projects120.xlsx",reader.getFaculty());

        reader.readXLSX(120, "src/main/resources/staff.xlsx");
        writer.write("src/main/resources/projects240.xlsx",reader.getFaculty());

        staffRepository= reader.readXLSX(250, "src/main/resources/staff.xlsx");
        writer.write("src/main/resources/projects500.xlsx",reader.getFaculty());

        StudentPreferenceWriter preferenceWriter = new StudentPreferenceWriter();
        projectRepository = projectReader.read("src/main/resources/projects500.xlsx", staffRepository);
        StudentReader studentReader = new StudentReader(projectRepository);
        File proj = new File("src/main/resources/projects60.xlsx");
        FileInputStream fis = new FileInputStream(proj);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        
        XSSFSheet projects = workbook.getSheetAt(0);

        studentReader.readXLSX(60, "src/main/resources/students.xlsx");
        preferenceWriter.write("src/main/resources/studentPreferences60.xlsx", studentReader.getStudents(), 60, projects );

        proj = new File("src/main/resources/projects120.xlsx");
        fis = new FileInputStream(proj);
        workbook = new XSSFWorkbook(fis);
        
        projects = workbook.getSheetAt(0);


        studentReader.readXLSX(120, "src/main/resources/students.xlsx");
        preferenceWriter.write("src/main/resources/studentPreferences120.xlsx", studentReader.getStudents(), 120, projects );

        proj = new File("src/main/resources/projects240.xlsx");
        fis = new FileInputStream(proj);
        workbook = new XSSFWorkbook(fis);
        
        projects = workbook.getSheetAt(0);


        studentReader.readXLSX(240, "src/main/resources/students.xlsx");
        preferenceWriter.write("src/main/resources/studentPreferences240.xlsx", studentReader.getStudents(), 240, projects);

        proj = new File("src/main/resources/projects500.xlsx");
        fis = new FileInputStream(proj);
        workbook = new XSSFWorkbook(fis);
        
        projects = workbook.getSheetAt(0);

        studentReader.readXLSX(500, "src/main/resources/students.xlsx");
        preferenceWriter.write("src/main/resources/studentPreferences500.xlsx", studentReader.getStudents(), 500, projects);

        
    }
}
