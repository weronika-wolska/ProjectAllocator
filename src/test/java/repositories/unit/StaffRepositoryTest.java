package repositories.unit;

import java.io.FileInputStream;
import java.util.ArrayList;

import entities.StaffMember;
import entities.Stream;

import repositories.StaffRepository;
import org.junit.Assert;
import org.junit.Test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;

public class StaffRepositoryTest{

    @Test 
    public void addStaffMemberWithFileTest(){
        StaffRepository staffRepository = new StaffRepository();
        try {
            FileInputStream file = new FileInputStream("src/main/resources/staff.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            
            // starts at 1 because row 0 consists of column headers
            for (int i = 1; i < 3;i++) {
                StaffMember newStaff = new StaffMember();
                Row row = sheet.getRow(i);
                String cellString;
                Cell currentCell = row.getCell(0);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                newStaff.setName(cellString);     
                currentCell = row.getCell(1);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                ArrayList<String> projects = new ArrayList<>();
                projects.add(cellString);
                newStaff.setResearchActivities(projects);
                currentCell = row.getCell(2);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                ArrayList<String> researchAreas = new ArrayList<>();
                researchAreas.add(cellString);
                newStaff.setResearchAreas(researchAreas);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                Stream stream;
                if(cellString=="CS"){ stream = Stream.CS;}
                else if(cellString=="Dagon Studies" || cellString=="DS"){ stream = Stream.DS;}
                else if(cellString=="CSDS"){  stream = Stream.CSDS;}
                else { stream = null;}
                newStaff.setSpecialFocus(stream);
                staffRepository.addStaffMember(newStaff);
            }
            Assert.assertEquals("staff not added correctly", staffRepository.getStaffMember(0).getName(), "Daniel Day-Lewis");
            Assert.assertEquals("staff not added correctly", staffRepository.getStaffMember(1).getName(), "Tina Fey");
            Assert.assertEquals("staff not added correctly", staffRepository.getSize(), 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test 
    public void removeStaffFromFileTest(){
        StaffRepository staffRepository = new StaffRepository();
        try {
            FileInputStream file = new FileInputStream("src/main/resources/staff.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            
            // starts at 1 because row 0 consists of column headers
            for (int i = 1; i < 3;i++) {
                StaffMember newStaff = new StaffMember();
                Row row = sheet.getRow(i);
                String cellString;
                Cell currentCell = row.getCell(0);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                newStaff.setName(cellString);     
                currentCell = row.getCell(1);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                ArrayList<String> projects = new ArrayList<>();
                projects.add(cellString);
                newStaff.setResearchActivities(projects);
                currentCell = row.getCell(2);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                ArrayList<String> researchAreas = new ArrayList<>();
                researchAreas.add(cellString);
                newStaff.setResearchAreas(researchAreas);
                if(currentCell == null) cellString = "";
                else cellString = currentCell.getStringCellValue();
                Stream stream;
                if(cellString=="CS"){ stream = Stream.CS;}
                else if(cellString=="Dagon Studies" || cellString=="DS"){ stream = Stream.DS;}
                else if(cellString=="CSDS"){  stream = Stream.CSDS;}
                else { stream = null;}
                newStaff.setSpecialFocus(stream);
                staffRepository.addStaffMember(newStaff);
            }
            staffRepository.removeStaffMember(staffRepository.getStaffMember(1));
            Assert.assertEquals("staff not removed correctly", staffRepository.getStaffMember(0).getName(), "Daniel Day-Lewis");
            Assert.assertEquals("staff not removed correctly", staffRepository.getStaffMember(1), null);
            Assert.assertEquals("staff not removed correctly", staffRepository.getSize(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}