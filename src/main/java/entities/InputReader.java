package entities;

import exceptions.DuplicateStudentIdException;
import exceptions.InvalidArgumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import repositories.ProjectRepository;
import repositories.StudentRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InputReader {
    public final int COLUMN_RANGE = 50;
    public final int PAGE_WIDTH = 200;
    private StudentRepository studentRepository;
    private StudentRepository studentRepositoryAssigned;
    private ProjectRepository projectRepository;
    private ProjectRepository projectRepositoryAssigned;
    private Map<String, Integer> schemaAttributes;
    private boolean ignoreDuplicatesFound;

    public InputReader() {
        this(null, null, null, null, null, false);
    }

    public InputReader(StudentRepository students, StudentRepository studentsAssigned, ProjectRepository projects, ProjectRepository projectsAssigned, Map<String, Integer> schemaAttributes, boolean ignoreDuplicatesFound) {
        setStudentRepository(students);
        setStudentRepositoryAssigned(studentsAssigned);
        setProjectRepository(projects);
        setProjectRepositoryAssigned(projectsAssigned);
        setSchemaAttributes(schemaAttributes);
        setIgnoreDuplicatesFound(ignoreDuplicatesFound);
    }

    public boolean isIgnoreDuplicatesFound() {
        return ignoreDuplicatesFound;
    }

    public void setIgnoreDuplicatesFound(boolean ignoreDuplicatesFound) {
        this.ignoreDuplicatesFound = ignoreDuplicatesFound;
    }

    public Map<String, Integer> getSchemaAttributes() {
        return schemaAttributes;
    }

    public void setSchemaAttributes(Map<String, Integer> schemaAttributes) {
        if(schemaAttributes == null) schemaAttributes = new HashMap<>();
        this.schemaAttributes = schemaAttributes;
    }

    public StudentRepository getStudentRepositoryAssigned() {
        return studentRepositoryAssigned;
    }

    public void setStudentRepositoryAssigned(StudentRepository studentRepositoryAssigned) {
        if( studentRepositoryAssigned == null) studentRepositoryAssigned = new StudentRepository();
        this.studentRepositoryAssigned = studentRepositoryAssigned;
    }

    public ProjectRepository getProjectRepositoryAssigned() {
        return projectRepositoryAssigned;
    }

    public void setProjectRepositoryAssigned(ProjectRepository projectRepositoryAssigned) {
        if( projectRepositoryAssigned == null) projectRepositoryAssigned = new ProjectRepository();
        this.projectRepositoryAssigned = projectRepositoryAssigned;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public void setStudentRepository(StudentRepository studentRepository) {
        if( studentRepository == null) studentRepository = new StudentRepository();
        this.studentRepository = studentRepository;
    }

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public void setProjectRepository(ProjectRepository projectRepository) {
        if( projectRepository == null) projectRepository = new ProjectRepository();
        this.projectRepository = projectRepository;
    }

    public void readXLSX(String filePath) throws InvalidArgumentException, IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum() + 1;
        //System.out.println("File input obtained, row count:" + rowCount);
        parseSchema(sheet);
        for (int i = 1; i < rowCount; ++i) {
            if( !isRowEmpty(sheet.getRow(i))) {
                //System.out.println("About to parse row" + i);
                parseRow(sheet.getRow(i));
            }
        }

        // verify repositories, ensure more projects than students
        if( projectRepository.getSize() < studentRepository.getSize()) throw new InvalidArgumentException("error: not enough projects for the students listed in the input file");
        if( studentRepository.getSize() == 0) throw new InvalidArgumentException("error: only self-proposed students in the file, nothing to assign");
    }

    private void parseRow(Row currentRow) throws InvalidArgumentException {
        // attributes which can be obtained here: student number, first name, surname, gpa, stream, proposer, supervisor, name, 1 (preferences)
        Student currentStudent = new Student();
        Cell currentCell;
        String currentCellString;
        String currentLocationString;

        // obtaining student number
        // moving cell
        currentCell = currentRow.getCell(schemaAttributes.get("student number"));
        currentLocationString = "row " + currentRow.getRowNum() + " column " + currentCell.getColumnIndex();
        currentCellString = getCellsString(currentCell).trim();

        // parsing cell
        //System.out.println("About to parse another student number with " + currentCellString);
        long studentNumber;
        if( !isStringANumber(currentCellString)) /*return; // */throw new InvalidArgumentException("error: cannot read a student number at " + currentLocationString);
        else {
            studentNumber = getNumericValueAsLong(Double.parseDouble(currentCellString));
            //System.out.println("Just parsed student id:" + studentNumber);
        }
        if(studentRepository.hasStudentById(studentNumber) ||
                studentRepositoryAssigned.hasStudentById(studentNumber)) {
            if(ignoreDuplicatesFound) {
                return;
            }
            else throw new InvalidArgumentException("error: newly found student number is a duplicate, at " + currentLocationString); // should the other row be listed?
        }
        else {
            //System.out.println("Setting this as newStudent's id");
            currentStudent.setStudentId(studentNumber);
        }

        // obtaining name/ names
        if( schemaAttributes.containsKey("name")) { // name
            // moving cell
            currentCell = currentRow.getCell(schemaAttributes.get("name"));
            //currentLocationString = "row " + currentRow.getRowNum() + " column " + currentCell.getColumnIndex();
            currentCellString = getCellsString(currentCell).trim();
            // parsing cell
            currentStudent.setByOneName(currentCellString);
            //System.out.println("Getting and setting the student's name:" + currentCellString);

        } else if( schemaAttributes.containsKey("first name") || schemaAttributes.containsKey("surname")) { // names
            if(schemaAttributes.containsKey("first name")) {
                currentCell = currentRow.getCell(schemaAttributes.get("first name"));
                //currentLocationString = "row " + currentRow.getRowNum() + " column " + currentCell.getColumnIndex();
                currentCellString = getCellsString(currentCell).trim();
                currentStudent.setFirstName(currentCellString);
            }
            if(schemaAttributes.containsKey("surname")) {
                currentCell = currentRow.getCell(schemaAttributes.get("surname"));
                //currentLocationString = "row " + currentRow.getRowNum() + " column " + currentCell.getColumnIndex();
                currentCellString = getCellsString(currentCell).trim();
                currentStudent.setSurname(currentCellString);
            }
        }
        // neither => do nothing


        // obtaining gpa
        if( schemaAttributes.containsKey("gpa")) {
            // moving cell
            currentCell = currentRow.getCell(schemaAttributes.get("gpa"));
            currentLocationString = "row " + currentRow.getRowNum() + " column " + currentCell.getColumnIndex();
            currentCellString = getCellsString(currentCell).trim();
            //System.out.println("String read in:" + currentCellString);
            // parsing cell
            try {
                double currentGpa = Double.parseDouble(currentCellString);
                //System.out.println("Double obtained:" + currentGpa);
                currentStudent.setGpa(currentGpa);
            }
            catch (Exception e) {
                throw new InvalidArgumentException("error: cannot parse student gpa at " + currentLocationString);
            }
        } else {
            currentStudent.setGpa(1.0);
        }

        // obtaining stream
        // moving cell
        // parsing cell
        // since we assume matching streams, we just assign common value to all students
        currentStudent.setStream(Stream.commonTesting);

        // obtaining proposer
        String proposer = "supervisor";
        if( schemaAttributes.containsKey("proposer")) {
            currentCell = currentRow.getCell(schemaAttributes.get("proposer"));
            currentLocationString = "row " + currentRow.getRowNum() + " column " + currentCell.getColumnIndex();
            currentCellString = getCellsString(currentCell).trim().toLowerCase();
            if( currentCellString.contains("student") || currentCellString.contains("self")) proposer = "student";
            else if( currentCellString.contains("supervisor") ||
                    currentCellString.contains("faculty") ||
                    currentCellString.contains("staff")) proposer = "supervisor";
            else throw new InvalidArgumentException("error: unable to parse proposer at " + currentLocationString);
        }

        ArrayList<Project> currentPreferences = new ArrayList<>();
        if(proposer.equals("student")) {
            currentCell = currentRow.getCell(schemaAttributes.get("1"));
            currentLocationString = "row " + currentRow.getRowNum() + " column " + currentCell.getColumnIndex();
            currentCellString = getCellsString(currentCell).trim();
            //System.out.println("Found self-assigned project:" + currentCellString);
            if( Project.isValidProjectName(currentCellString)) {
                if(projectRepositoryAssigned.hasProjectByName(currentCellString)) {
                    //System.out.println("This self-assigned project was self-assigned already.");
                    long otherStudentsId = studentRepositoryAssigned.getStudent(projectRepositoryAssigned.getProjects().indexOf(projectRepositoryAssigned.getProjectByName(currentCellString))).getStudentId();
                    throw new InvalidArgumentException("error: duplicate self-proposed project at " + currentLocationString + ", this project was self-proposed by student with id " + otherStudentsId);
                }
                else {
                    //System.out.println("This self-assigned project wasn't self-assigned already.");
                    if( projectRepository.hasProjectByName(currentCellString)) {
                        //System.out.println("This self-assigned project is present in project repository already");
                        projectRepository.removeProject(projectRepository.getProjectByName(currentCellString));
                    }
                    Project currentProject = new Project(currentCellString);
                    currentPreferences.add(currentProject);
                    currentStudent.setPreferences(currentPreferences);
                    try {
                        studentRepositoryAssigned.addStudent(currentStudent);
                    } catch (DuplicateStudentIdException e) {
                        e.printStackTrace();
                        throw new InvalidArgumentException("error: adding duplicate student at " + currentLocationString + " where this student's id was already found to not be present in the student repositories");
                    }
                    //System.out.println("Adding the project " + currentProject.toString() + " to the repository of assigned projects");
                    projectRepositoryAssigned.addProject(currentProject);
                }
            }
            else throw new InvalidArgumentException("error: self-proposed student does not list their proposed project's name at " + currentLocationString);
        }
        else {
            for( int i = schemaAttributes.get("1"); i < schemaAttributes.get("1") + 20; ++i) {
                currentCell = currentRow.getCell(i);
                currentLocationString = "row " + currentRow.getRowNum() + " column " + currentCell.getColumnIndex();
                currentCellString = getCellsString(currentCell).trim();
                if( Project.isValidProjectName(currentCellString)) {
                    if(!projectRepositoryAssigned.hasProjectByName(currentCellString)) {
                        Project currentProject;
                        if( projectRepository.hasProjectByName(currentCellString)) currentProject = projectRepository.getProjectByName(currentCellString);
                        else currentProject = new Project(currentCellString);
                        if( !currentPreferences.contains(currentProject)) currentPreferences.add(currentProject);
                        if( !projectRepository.hasProjectByName(currentCellString)) projectRepository.addProject(currentProject);
                    }
                }
            }
            currentStudent.setPreferences(currentPreferences);
            try {
                studentRepository.addStudent(currentStudent);
            } catch (DuplicateStudentIdException e) {
                e.printStackTrace();
                throw new InvalidArgumentException("error: adding duplicate student at " + currentLocationString + " where this student's id was already found to not be present in the student repositories");
            }
        }
    }

    private void parseSchema(XSSFSheet sheet) throws InvalidArgumentException {
        int firstAttributesIndex = getFirstAttributeIndex(sheet);
        int lastAttributesIndex = getLastAttributesIndex(sheet);
        Row header = sheet.getRow(0);
        String currentCellString;

        for(int i = firstAttributesIndex; i <= lastAttributesIndex; ++i) {
            currentCellString = getCellsString(header.getCell(i));
            //System.out.println("The currentCellString:" + currentCellString);
            if( isStringUseful(currentCellString)) {
                String attribute = getStringsAttribute(currentCellString);
                //System.out.println("Attribute found is:" + attribute);
                if( schemaAttributes.containsKey(attribute)) throw new InvalidArgumentException("error: input file header has duplicate attribute \"" + attribute + "\" at columns " + i + " and " + schemaAttributes.get(attribute));
                else {
                    schemaAttributes.put(attribute, i);
                }
            }
        }

        if( !schemaAttributes.containsKey("student number")) throw new InvalidArgumentException("error: application unable to find student id attribute in header of the input file");
        if( !schemaAttributes.containsKey("1")) throw new InvalidArgumentException("error: application was unable to find preference 1, or any other preference attribute, in the header of the input file");
    }

    public static boolean isRowEmpty(Row row) {
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); ++i) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                return false;
        }
        return true;
    }

    public int getFirstAttributeIndex(XSSFSheet sheet) throws InvalidArgumentException {
        int firstIndex = -1;
        Row header = sheet.getRow(0);

        for( int i = 0; i < PAGE_WIDTH; ++i) {
            if( getCellsString(header.getCell(i)) != null) {
                if( isStringUseful(getCellsString(header.getCell(i)))) {
                    firstIndex = i;
                    break;
                }
            }
        }

        if( firstIndex == -1) throw new InvalidArgumentException("error: no readable values found in the header of the input file");
        return firstIndex;
    }

    public int getLastAttributesIndex(XSSFSheet sheet) throws InvalidArgumentException {
        Row header = sheet.getRow(0);
        int firstIndex = getFirstAttributeIndex(sheet);
        int lastPossibleIndex = (firstIndex + COLUMN_RANGE);
        int lastIndex = lastPossibleIndex;

        for( int i = lastPossibleIndex; i >= firstIndex; --i) {
            if( header.getCell(i) != null) {
                //System.out.println("Current possible last index:" + getCellsString(header.getCell(i)));
                if( isStringUseful(getCellsString(header.getCell(i)))) {
                    lastIndex = i;
                    break;
                }
            }
        }
        return lastIndex;
    }

    public String getCellsString(Cell cell) {
        String cellsString;
        try{
            cellsString = cell.getStringCellValue();
        }
        catch (IllegalStateException ise) {
            double number = cell.getNumericCellValue();
            cellsString = String.valueOf(number);
            //System.out.println("The string is now " + cellsString + " where the number as double was " + number + " and as a long would be " + getNumericValueAsLong(number));
        }
        catch (NullPointerException npe) {
            cellsString = "";
        }
        return cellsString;
    }

    public long getNumericValueAsLong( double numericValue) {
        return (new Double(numericValue)).longValue();
    }

    public String getStringsAttribute(String string) {
        if( string == null) return null;
        else string = string.trim().toLowerCase();
        if( isStringStudentFirstName(string)) return "first name";
        else if( isStringStudentSurname(string)) return "surname";
        else if( isStringStudentNumber(string)) return "student number";
        else if( isStringGpa(string)) return "gpa";
        else if( isStringStream(string)) return "stream";
        else if( isStringProposer(string)) return "proposer";
        else if( isStringSupervisor(string)) return "supervisor";
        else if( isStringStudentName(string)) return "name"; // this searches for the word student, which isn't specific and could be used elsewhere, thus this "search" must be done second last, when the others have been eliminated
        else if( isStringFirstPreference(string)) return "1";
        else return null;
    }

    private boolean isStringUseful(String string) {
        if ( string == null) return false;
        string = string.trim().toLowerCase();
        return isStringAReadableAttribute(string);
    }

    private boolean isStringAReadableAttribute(String string) {
        return isStringStudentFirstName(string) ||
                isStringStudentSurname(string) ||
                isStringStudentName(string) ||
                isStringStudentNumber(string) ||
                isStringGpa(string) ||
                isStringProposer(string) ||
                isStringStream(string) ||
                isStringSupervisor(string) ||
                isStringFirstPreference(string);
    }

    private boolean isStringStudentFirstName(String string) {
        return string.contains("first") && string.contains("name");
    }

    private boolean isStringStudentSurname(String string) {
        return string.contains("surname") ||
                (string.contains("second") && string.contains("name")) ||
                (string.contains("last") && string.contains("name"));
    }

    private boolean isStringStudentName(String string) {
        if(!isStringStudentFirstName(string) && !isStringStudentSurname(string)) {
            return ( string.contains("student")) ||
                    string.contains("name");
        }
        return false;
    }

    private boolean isStringStudentNumber(String string) {
        return string.contains("number") ||
                string.contains("id") ||
                string.contains("num");
    }

    private boolean isStringGpa(String string) {
        return string.contains("gpa") ||
                string.contains("grade point average");
    }

    private boolean isStringProposer(String string) {
        return string.contains("propose");
    }

    private boolean isStringFirstPreference(String string) {
        return string.equals("1") ||
                string.equals("1.0") ||
                string.contains("one") ||
                string.contains("preference1") ||
                string.contains("choice1") ||
                string.contains("project1") ||
                string.contains("preference 1") ||
                string.contains("project 1") ||
                string.contains("choice 1") ||
                string.contains("preference one") ||
                string.contains("choice one") ||
                string.contains("project one") ||
                string.contains("first preference") ||
                string.contains("first choice") ||
                string.contains("first project") ||
                string.contains("1st preference") ||
                string.contains("1st choice") ||
                string.contains("1st project");
    }

    private boolean isStringSupervisor(String string) {
        return string.contains("supervisor") ||
                string.contains("faculty") ||
                string.contains("staff member");
    }

    private boolean isStringStream(String string) {
        return string.contains("stream");
    }

    // currently unused may be will be deleted later
    private boolean isStringANumber(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}

