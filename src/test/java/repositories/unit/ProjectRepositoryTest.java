package repositories.unit;

import entities.Project;
import entities.StaffMember;
import entities.Stream;
import repositories.ProjectRepository;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;


public class ProjectRepositoryTest {
    //ProjectRepository projectRepository = new ProjectRepository();
/*
    @Test
    public void addProjectTest(){
        ProjectRepository projectRepository = new ProjectRepository();
        ArrayList<String> researchActivity = new ArrayList<>();
        researchActivity.add("petting dogs");
        ArrayList<String> researchAreas = new ArrayList<>();
        researchAreas.add("AI");
        StaffMember supervisor = new StaffMember("Greg Burke", researchActivity, researchAreas, Stream.CS );
        Stream stream = Stream.CS;
        Project project = new Project("test", stream, supervisor);
        projectRepository.addProject(project);
        Assert.assertEquals("Project name is incorrect", "test", projectRepository.getProject(0).getProjectName());
        Assert.assertEquals("Stream incorrect", Stream.CS, projectRepository.getProject(0).getStream());
    }
*/
    @Test
    public void addProjectFromFileTest(){
        ProjectRepository projectRepository = new ProjectRepository();
        try{
            FileInputStream file = new FileInputStream("src/main/resources/projects60.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            
            for(int i=1;i<6;i++){
                Row row = sheet.getRow(i);
                String cellString;
                Cell currentCell = row.getCell(0);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                String projectSupervisor = cellString;
                StaffMember supervisor = new StaffMember();
                supervisor.setName(projectSupervisor);
                currentCell = row.getCell(1);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                String projectName = cellString;
                currentCell = row.getCell(2);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                Stream stream;
                if(cellString=="CS"){ stream = Stream.CS;}
                else if(cellString=="Dagon Studies" || cellString=="DS"){ stream = Stream.DS;}
                else if(cellString=="CSDS"){  stream = Stream.CSDS;}
                else { stream = null;}
                Project project = new Project(projectName, stream, supervisor);
                projectRepository.addProject(project);
                
            }
        
            Assert.assertEquals("projects not added correctly", projectRepository.getSize(), 5);
            
            Assert.assertEquals("projects not added correctly", projectRepository.getProject(0).getProjectName(), "Reality TV and the Cthulu-Dagon mythos");
            
            Assert.assertEquals("projects not added correctly", projectRepository.getProject(1).getProjectName(), "monetizing celebrity status in Cthulhu-worshipping societies vs in Dagon-worshipping societies");
            
            Assert.assertEquals("projects not added correctly", projectRepository.getProject(2).getProjectName(), "making unauthorized sex tapes in Cthulhu-worshipping societies vs in Dagon-worshipping societies");
            
            Assert.assertEquals("projects not added correctly", projectRepository.getProject(3).getProjectName(), "Sherlock Holmes and the Dagon mythos");
            
            Assert.assertEquals("projects not added correctly", projectRepository.getProject(4).getProjectName(), " Detective fiction and the Dagon mythos");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test 
    public void testRemoveProjectFromFile(){
        ProjectRepository projectRepository = new ProjectRepository();
        try{
            FileInputStream file = new FileInputStream("src/main/resources/projects60.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            
            for(int i=1;i<6;i++){
                Row row = sheet.getRow(i);
                String cellString;
                Cell currentCell = row.getCell(0);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                String projectSupervisor = cellString;
                StaffMember supervisor = new StaffMember();
                supervisor.setName(projectSupervisor);
                currentCell = row.getCell(1);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                String projectName = cellString;
                currentCell = row.getCell(2);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                Stream stream;
                if(cellString=="CS"){ stream = Stream.CS;}
                else if(cellString=="Dagon Studies" || cellString=="DS"){ stream = Stream.DS;}
                else if(cellString=="CSDS"){  stream = Stream.CSDS;}
                else { stream = null;}
                Project project = new Project(projectName, stream, supervisor);
                projectRepository.addProject(project);
                
            }

            projectRepository.removeProject(projectRepository.getProject(4));
        
            Assert.assertEquals("projects not removed correctly", projectRepository.getSize(), 4);
            
            Assert.assertEquals("projects not removed correctly", projectRepository.getProject(0).getProjectName(), "Reality TV and the Cthulu-Dagon mythos");
            
            Assert.assertEquals("projects not removed correctly", projectRepository.getProject(1).getProjectName(), "monetizing celebrity status in Cthulhu-worshipping societies vs in Dagon-worshipping societies");
            
            Assert.assertEquals("projects not removed correctly", projectRepository.getProject(2).getProjectName(), "making unauthorized sex tapes in Cthulhu-worshipping societies vs in Dagon-worshipping societies");
            
            Assert.assertEquals("projects not removed correctly", projectRepository.getProject(3).getProjectName(), "Sherlock Holmes and the Dagon mythos");

            Assert.assertEquals("project not removed correctly", projectRepository.getProject(4), null);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}