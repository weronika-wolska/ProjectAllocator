package entities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import repositories.ProjectRepository;
import repositories.StaffRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// creates and returns a project repository
public class ProjectReader {
    private ProjectRepository projectRepository = new ProjectRepository();

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectRepository read(String filePath, StaffRepository staffRepository)throws FileNotFoundException, IOException{
        try{
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int numOfProjects = sheet.getLastRowNum();
            for (int i = 1; i<numOfProjects;i++){   // assuming first row is the header
                Project project = parseRowIntoProject(sheet.getRow(i), staffRepository);
                projectRepository.addProject(project);
            }
            return projectRepository;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Project parseRowIntoProject(Row row, StaffRepository staffRepository){
        Project project = new Project("");
        Cell currCell = row.getCell(0);
        String cellString;
        if(currCell==null){cellString="";}
        else{cellString=currCell.getStringCellValue();}
        if(cellString.equals("")){project.setSupervisor(null);}
        else{
            StaffMember supervisor = staffRepository.getStaffMember(cellString);
            project.setSupervisor(supervisor);
        }

        currCell = row.getCell(1);
        if(currCell==null){ cellString="";}
        else{cellString = currCell.getStringCellValue();}
        project.setProjectName(cellString);

        currCell = row.getCell(2);
        if(currCell==null){project.setStream(null);}
        else if(currCell.getStringCellValue().equals("CS")){project.setStream(Stream.CS);}
        else if(currCell.getStringCellValue().equals("DS")){project.setStream(Stream.DS);}
        else{project.setStream(Stream.CSDS);}

        return project;
    }

}
