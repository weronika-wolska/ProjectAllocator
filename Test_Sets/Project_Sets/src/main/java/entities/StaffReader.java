package entities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

public class StaffReader {

    private ArrayList<StaffMember> faculty;

    public void readXLSX(int staffCount, String filePath) {
        System.out.println("In readXLSX");
        faculty = new ArrayList<>(staffCount);
        try {
            FileInputStream file = new FileInputStream(new File("src/main/resources/staff.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            int[] relevantRowNumbers = getRandomRowNumbers(staffCount, rowCount);
            System.out.println("File input obtained, random rows:" + Arrays.toString(relevantRowNumbers));
            for (int rowNumber :
                    relevantRowNumbers) {
                Row row = sheet.getRow(rowNumber);
                StaffMember newStaff = parseRowIntoStaffMember(row);
                faculty.add(newStaff);
                System.out.println("Forming a new StaffMember from row:" + rowNumber + " " + newStaff.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int[] getRandomRowNumbers(int rowsWanted, int rowsProvided) {
        int[] randomRowNumbers = new int[rowsWanted];
        Random rand = new Random();
        System.out.println("In getRandomRows,rand:" + rand.nextInt(rowsProvided) + " and rows wanted/provided:" + rowsWanted + " " + rowsProvided);
        for(int i = 0; i < rowsWanted; ++i) {
            randomRowNumbers[i] = rand.nextInt(rowsProvided);
        }
        return randomRowNumbers;
    }

    private StaffMember parseRowIntoStaffMember(Row row) {
        System.out.println("parsing row");
        StaffMember newStaff = new StaffMember();

        Cell currentCell = row.getCell(0);
        String cellString = currentCell.getStringCellValue();
        System.out.println(cellString);
        newStaff.setName(cellString);

        currentCell = row.getCell(1);
        cellString = currentCell.getStringCellValue();
        System.out.println(cellString);
        ArrayList<String> activities = tokenizeByComma(cellString);
        newStaff.setResearchActivities(activities);

        currentCell = row.getCell(2);
        System.out.println(cellString);
        cellString = currentCell.getStringCellValue();
        System.out.println(cellString);
        ArrayList<String> areas = tokenizeByComma(cellString);
        newStaff.setResearchAreas(areas);

        currentCell = row.getCell(3);
        if(currentCell == null) cellString = "";
        else cellString = currentCell.getStringCellValue();
        System.out.println(cellString);
        translateFocusForStaffMember(newStaff, cellString);

        createStaffMembersProjects(newStaff);
        return newStaff;
    }

    private boolean projectAlreadyExists(String potentialProjectName) {
        for (StaffMember staff :
                faculty) {
            for (String projectName :
                    staff.getProjects()) {
                if(projectName.equals(potentialProjectName)) {
                    System.out.println("An existing project name was generated");
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<String> tokenizeByComma(String string) {
        ArrayList<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(string,",");
        while(tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        return tokens;
    }

    private void translateFocusForStaffMember(StaffMember staff, String focus) {
        if(focus.equals("Dagon Studies")) {
            staff.setSpecialFocus(Stream.DS);
        }
        else {
            staff.setSpecialFocus(Stream.CS);
        }
    }

    private void createStaffMembersProjects(StaffMember staff) {
        ArrayList<String> projects = new ArrayList<>();
        String specialFocus, project;
        if(staff.getSpecialFocus() == Stream.DS) specialFocus = "in Dagon-worshipping societies";
        else specialFocus = "in Cthulhu-worshipping societies";

        for (String activity :
                staff.getResearchActivities()) {
            project = activity + " " + specialFocus;
            if(!projectAlreadyExists(project)) {
                System.out.println("Project doesn't exist yet, creating it:" + project + " for " + staff.getName());
                projects.add(project);
            }
        }
        staff.setProjects(projects);
    }

    public ArrayList<StaffMember> getFaculty() {
        return faculty;
    }

    public void setFaculty(ArrayList<StaffMember> faculty) {
        this.faculty = faculty;
    }
}
