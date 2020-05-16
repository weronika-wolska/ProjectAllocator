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
    private StaffRepository staffRepository;
    private boolean testing;

    public void setStaffRepository(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public boolean isTesting() {
        return testing;
    }

    public void setTesting(boolean testing) {
        this.testing = testing;
    }

    public StaffReader(){
        staffRepository = new StaffRepository();
        faculty = new ArrayList<>();
    }

    public StaffRepository readXLSX(String filePath) {

        return readXLSX(-1, filePath);
    }

    public StaffRepository getStaffRepository() {
        return staffRepository;
    }

    public StaffRepository readXLSX(int staffCount, String filePath) {
        setTesting(staffCount != -1);
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum() + 1;
            ArrayList<Integer> relevantRowNumbers = getRowNumbers(staffCount, rowCount);
            if(!testing) staffCount = rowCount;
            int failureCount = 0; // this variable counts the number of times the reader was unsuccessful and arbitrarily, doesn't allow the for loop to execute and fail (consecutively) more than staffCount number of times.
            for (int i = 0; i < staffCount && failureCount < staffCount * 2; ++i) {
                Row row = sheet.getRow(relevantRowNumbers.get(i));
                StaffMember newStaff = parseRowIntoStaffMember(row);
                /*System.out.println("Deciding whether to add new staff to faculty, their number:" + i + " and current fC:" + failureCount + ", their string:");
                try {
                    System.out.println(newStaff.toString());
                }
                catch(NullPointerException npe) {
                    System.out.println("Oops this staff has a null string, skipping the printing.");
                }
                System.out.println("The end of the string, now the decision:");//*/
                if (newStaff == null || isStaffPresent(newStaff) || isDefaultStaffMember(newStaff)) {
                    //System.out.println("Not accepting above staff member.");
                    if(testing) {
                        ++failureCount;
                        relevantRowNumbers.remove(i);
                        --i;
                        relevantRowNumbers.add(getRandomNumber(rowCount));
                    }
                }
                else {
                    //System.out.println("Accepting the above staff member:" + newStaff.getName());
                    failureCount = 0;
                    faculty.add(newStaff);
                    staffRepository.addStaffMember(newStaff);
                }
                //System.out.println("Forming a new StaffMember from row:" + relevantRowNumbers.get(i) + " " + newStaff.toString());
            }
            return staffRepository;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isStaffPresent(StaffMember staffMember) {
        //System.out.println("Entered isStraffPresent.");
        boolean status = false;
        for (StaffMember staff:
                faculty) {
            if(staff.isTheSameStaffMember(staffMember)) {
                status = true;
                break;
            }
        }
        //System.out.println("Exiting isStaffPresent with result:" + status);
        return status;
    }

    private ArrayList<Integer> getRowNumbers(int rowsWanted, int rowsProvided) {
        ArrayList<Integer> randomRowNumbers = new ArrayList<>();
        if(testing) {
            Random rand = new Random();
            //System.out.println("In getRandomRows,rand:" + rand.nextInt(rowsProvided) + " and rows wanted/provided:" + rowsWanted + " " + rowsProvided);
            for(int i = 0; i < rowsWanted; ++i) {
                randomRowNumbers.add(rand.nextInt(rowsProvided));
            }
        }
        else {
            for (int i = 0; i < rowsProvided; ++i) {
                randomRowNumbers.add(i);
            }
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
        try {
            cellString0 = currentCell.getStringCellValue();
        }
        catch (Exception e) {
            cellString0 = "";
        }
        //System.out.println(cellString0);
        newStaff.setName(cellString0);

        //System.out.println("About to parse researchActivities");
        String cellString1;
        currentCell = row.getCell(1);
        try {
            cellString1 = currentCell.getStringCellValue();
        }
        catch (Exception e) {
            cellString1 = "";
        }
        //System.out.println(cellString1);
        ArrayList<String> activities = tokenizeByComma(cellString1);
        //System.out.println("Activities become" + activities);
        newStaff.setResearchActivities(activities);

        //System.out.println("About to parse researchAreas");
        String cellString2;
        currentCell = row.getCell(2);
        try {
            cellString2 = currentCell.getStringCellValue();
        }
        catch (Exception e) {
            cellString2 = "";
        }
        //System.out.println(cellString2);
        ArrayList<String> areas = tokenizeByComma(cellString2);
        //System.out.println("Areas become" + areas);
        newStaff.setResearchAreas(areas);

        String cellString3;
        currentCell = row.getCell(3);
        try {
            cellString3 = currentCell.getStringCellValue();
        }
        catch (Exception e) {
            cellString3 = "";
        }
        //System.out.println(cellString3);
        translateFocusForStaffMember(newStaff, cellString3);

        //System.out.println("Still parsing row with current newMember:" + newStaff);
        if (testing) {
            //System.out.println("UNO");
            if(createsStaffMembersProjects(newStaff) && !staffHasNullAttributes(cellString0, cellString1, cellString2)) {
                //System.out.println("DOS");
                return newStaff;
            }
            //System.out.println("TRES");
        }
        else {
            if(!cellString0.equals("")) {
                return newStaff;
            }
        }
        return null;
    }

    private boolean isDefaultStaffMember(StaffMember staff) {
        try {
            return staff.getName().trim().toLowerCase().equals("defaultname") || staff.getName().trim().toLowerCase().equals("") || staff.getName().trim().toLowerCase().equals("staff member");
        }
        catch (NullPointerException npe) {
            return true;
        }
    }

    private boolean staffHasNullAttributes(String attr0, String attr1, String attr2) {
        //System.out.println("In staffHasNullAttributes, returning:" + (attr0.equals("") || attr1.equals("") || attr2.equals("")));
        return attr0.equals("") || attr1.equals("") || attr2.equals("");
    }

    private boolean projectDoesNotExist(String potentialProjectName) {
        for (StaffMember staff :
                faculty) {
            for (String projectName :
                    staff.getProjects()) {
                if(projectName.equals(potentialProjectName)) {
                    //System.out.println("An existing project name was generated");
                    //System.out.println("In projectDoesNotExist, returning false");
                    return false;
                }
            }
        }
        //System.out.println("In projectDoesNotExist, returning true");
        return true;
    }

    public ArrayList<String> tokenizeByComma(String string) {
        //System.out.println("In tokenizeByComma with:" + string);
        ArrayList<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(string,",");
        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if(!token.equals("") && !token.trim().isEmpty()) {
                tokens.add(token);
            }
        }
        //System.out.println("In tokenizeByComma with tokens:" + tokens);
        return tokens;
    }

    private void translateFocusForStaffMember(StaffMember staff, String focus) {
        if(focus.trim().toLowerCase().equals("dagon studies")) {
            staff.setSpecialFocus(Stream.CSDS);
        }
        else {
            staff.setSpecialFocus(Stream.CS);
        }
    }

    private boolean createsStaffMembersProjects(StaffMember staff) {
        //System.out.println("In createStaffMembersProjects");
        boolean hasAtLeastOneProject = false;
        ArrayList<String> projects = new ArrayList<>();
        String activitySpecifier, areaSpecifier, project;
        if(staff.getSpecialFocus() == Stream.CSDS) {
            activitySpecifier = "in Cthulhu-worshipping societies vs in Dagon-worshipping societies.";
            areaSpecifier = "and the Cthulu-Dagon mythos.";
            //System.out.println("In createStaffMembersProjects with stream:" + Stream.CSDS);
        }
        else if(staff.getSpecialFocus() == Stream.CS) {
            activitySpecifier = "in Cthulhu-worshipping societies.";
            areaSpecifier = "and the Cthulu mythos.";
            //System.out.println("In createStaffMembersProjects with stream:" + Stream.CS);
        }
        else {
            activitySpecifier = "in Dagon-worshipping societies.";
            areaSpecifier = "and the Dagon mythos.";
            //System.out.println("In createStaffMembersProjects with a different stream");
        }

        for (String area :
                staff.getResearchAreas()) {
            project = area + " " + areaSpecifier;
            project = formatStringForProject(project);
            //System.out.println("In createStaffMembersProjects (areas) with new project:" + project);
            if(projectDoesNotExist(project)) {
                //System.out.println("Project doesn't exist yet, creating it:" + project + " for " + staff.getName());
                hasAtLeastOneProject = true;
                projects.add(project);
            }
        }

        for (String activity :
                staff.getResearchActivities()) {
            project = activity + " " + activitySpecifier;
            project = formatStringForProject(project);
            //System.out.println("In createStaffMembersProjects (areas) with new project:" + project);
            if(projectDoesNotExist(project)) {
                //System.out.println("Project doesn't exist yet, creating it:" + project + " for " + staff.getName());
                hasAtLeastOneProject = true;
                projects.add(project);
            }
        }
        staff.setProjects(projects);
        /*if(hasAtLeastOneProject) System.out.println("I have at least one project");
        else System.out.println("Dont have a project");*/
        return hasAtLeastOneProject;
    }

    private String formatStringForProject(String input) {
        input = input.trim();
        input = input.substring(0,1).toUpperCase() + input.substring(1);
        return input;
    }

    public ArrayList<StaffMember> getFaculty() {
        return faculty;
    }

    public void setFaculty(ArrayList<StaffMember> faculty) {
        this.faculty = faculty;
    }
}
