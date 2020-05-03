package entities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import repositories.StaffRepository;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class StaffReader {

    private ArrayList<StaffMember> faculty;
    private StaffRepository staffRepository = new StaffRepository();

    public StaffReader(){};

    public StaffRepository getStaffRepository() {
        return staffRepository;
    }

    public StaffRepository readXLSX(String filePath) {
        return readXLSX(0, filePath);
    }


    public StaffRepository readXLSX(int staffCount, String filePath) {
        //System.out.println("In readXLSX");
        faculty = new ArrayList<>(staffCount);
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            ArrayList<Integer> relevantRowNumbers = getRandomRowNumbers(staffCount, rowCount);
            //System.out.println("File input obtained, random rows:" + Arrays.toString(relevantRowNumbers));
            for (int i = 0; i < staffCount; ++i) {
                Row row = sheet.getRow(relevantRowNumbers.get(i));
                StaffMember newStaff = parseRowIntoStaffMember(row);
                if (newStaff == null) {
                    relevantRowNumbers.remove(i);
                    --i;
                    relevantRowNumbers.add(getRandomNumber(rowCount));
                }
                else {
                    faculty.add(newStaff);
                    staffRepository.addStaffMember(newStaff);
                }
                //System.out.println("Forming a new StaffMember from row:" + rowNumber + " " + newStaff.toString());
            }
            return staffRepository;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Integer> getRandomRowNumbers(int rowsWanted, int rowsProvided) {
        ArrayList<Integer> randomRowNumbers = new ArrayList<>(rowsWanted);
        Random rand = new Random();
        //System.out.println("In getRandomRows,rand:" + rand.nextInt(rowsProvided) + " and rows wanted/provided:" + rowsWanted + " " + rowsProvided);
        for(int i = 0; i < rowsWanted; ++i) {
            randomRowNumbers.add(rand.nextInt(rowsProvided));
        }
        return randomRowNumbers;
    }

    private int getRandomNumber(int range) {
        Random rand = new Random();
        return rand.nextInt(range);
    }

    private StaffMember parseRowIntoStaffMember(Row row) {
        //System.out.println("parsing row");
        StaffMember newStaff = new StaffMember();
        Cell currentCell;

        String cellString0;
        currentCell = row.getCell(0);
        if(currentCell == null) cellString0 = "";
        else cellString0 = currentCell.getStringCellValue();
        //System.out.println(cellString0);
        newStaff.setName(cellString0);

        String cellString1;
        currentCell = row.getCell(1);
        if(currentCell == null) cellString1 = "";
        else cellString1 = currentCell.getStringCellValue();
        //System.out.println(cellString1);
        ArrayList<String> activities = tokenizeByComma(cellString1);
        newStaff.setResearchActivities(activities);

        String cellString2;
        currentCell = row.getCell(2);
        if(currentCell == null) cellString2 = "";
        else cellString2 = currentCell.getStringCellValue();
        //System.out.println(cellString2);
        ArrayList<String> areas = tokenizeByComma(cellString2);
        newStaff.setResearchAreas(areas);

        String cellString3;
        currentCell = row.getCell(3);
        if(currentCell == null) cellString3 = "";
        else cellString3 = currentCell.getStringCellValue();
        //System.out.println(cellString3);
        translateFocusForStaffMember(newStaff, cellString3);

        if(createsStaffMembersProjects(newStaff) && !staffHasNullAttributes(cellString0, cellString1, cellString2)) {
            return newStaff;
        }
        return null;
    }

    private boolean staffHasNullAttributes(String attr0, String attr1, String attr2) {
        return attr0.equals("") || attr1.equals("") || attr2.equals("");
    }

    private boolean projectAlreadyExists(String potentialProjectName) {
        for (StaffMember staff :
                faculty) {
            for (String projectName :
                    staff.getProjects()) {
                if(projectName.equals(potentialProjectName)) {
                    //System.out.println("An existing project name was generated");
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<String> tokenizeByComma(String string) {
        ArrayList<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(string,",");
        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if(!token.equals("") && !token.trim().isEmpty()) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    private void translateFocusForStaffMember(StaffMember staff, String focus) {
            if(focus.equals("Dagon Studies")) {
                staff.setSpecialFocus(Stream.DS);
            }
            else {
                Random rand = new Random();
                if(rand.nextInt(2) % 2 == 0) {
                    staff.setSpecialFocus(Stream.CS);
                }
                else {
                    staff.setSpecialFocus(Stream.CSDS);
                }
            }
    }

    private boolean createsStaffMembersProjects(StaffMember staff) {
        boolean hasAtLeastOneProject = false;
        ArrayList<String> projects = new ArrayList<>();
        String activitySpecifier, areaSpecifier, project;
        if(staff.getSpecialFocus() == Stream.DS) {
            activitySpecifier = "in Dagon-worshipping societies";
            areaSpecifier = "and the Dagon mythos";
        }
        else if(staff.getSpecialFocus() == Stream.CS) {
            activitySpecifier = "in Cthulhu-worshipping societies";
            areaSpecifier = "and the Cthulu mythos";
        }
        else {
            activitySpecifier = "in Cthulhu-worshipping societies vs in Dagon-worshipping societies";
            areaSpecifier = "and the Cthulu-Dagon mythos";
        }

        for (String area :
                staff.getResearchAreas()) {
            project = area + " " + areaSpecifier;
            if(projectAlreadyExists(project)) {
                //System.out.println("Project doesn't exist yet, creating it:" + project + " for " + staff.getName());
                hasAtLeastOneProject = true;
                projects.add(project);
            }
        }

        for (String activity :
                staff.getResearchActivities()) {
            project = activity + " " + activitySpecifier;
            if(projectAlreadyExists(project)) {
                //System.out.println("Project doesn't exist yet, creating it:" + project + " for " + staff.getName());
                hasAtLeastOneProject = true;
                projects.add(project);
            }
        }
        staff.setProjects(projects);
        return hasAtLeastOneProject;
    }

    public ArrayList<StaffMember> getFaculty() {
        return faculty;
    }

    public void setFaculty(ArrayList<StaffMember> faculty) {
        this.faculty = faculty;
    }
}
