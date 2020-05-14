package entities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import exceptions.InvalidArgumentException;
import repositories.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;


public class StudentReader{
    private ArrayList<Student> students;
    private ProjectRepository projectRepository;
    private boolean testing;

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public boolean isTesting() {
        return testing;
    }

    public void setTesting(boolean testing) {
        this.testing = testing;
    }

    public StudentReader(ProjectRepository projectRepository){
        students = new ArrayList<>();
        this.projectRepository=projectRepository;
    }

    public StudentRepository readXLSX( String filePath) {
        return readXLSX(-1, filePath);
    }

    public StudentRepository readXLSX(int studentCount, String filePath) {
        setTesting(studentCount != -1);
        StudentRepository repository = new StudentRepository();
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum() + 1;
            ArrayList<Integer> relevantRowNumbers = getRowNumbers(studentCount, rowCount);
            if(!testing) studentCount = rowCount;
            int failureCount = 0;
            for (int i = 0; i < studentCount && failureCount < studentCount * 2; ++i) {
                if(!relevantRowNumbers.get(i).equals(0)) {
                    Row row = sheet.getRow(relevantRowNumbers.get(i));
                    Student newStudent = parseRowIntoStudent(row);
                    if (newStudent == null || isStudentPresent(newStudent) || isDefaultStudent(newStudent)) {
                        if(testing) {
                            ++failureCount;
                            relevantRowNumbers.remove(i);
                            --i;
                            relevantRowNumbers.add(getRandomNumber(rowCount));
                        }
                    }
                    else {
                        failureCount = 0;
                        students.add(newStudent);
                        repository.addStudent(newStudent);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repository;
    }
    




    private ArrayList<Integer> getRowNumbers(int rowsWanted, int rowsProvided) {
        ArrayList<Integer> rowNumbers = new ArrayList<>();
        if(testing) {
            Random rand = new Random();
            //System.out.println("In getRandomRows,rand:" + rand.nextInt(rowsProvided) + " and rows wanted/provided:" + rowsWanted + " " + rowsProvided);
            for(int i = 0; i < rowsWanted; ++i) {
                rowNumbers.add(rand.nextInt(rowsProvided));
            }
        }
        else {
            for (int i = 0; i < rowsProvided; ++i) {
                rowNumbers.add(i);
            }
        }
        return rowNumbers;
    }

    private int getRandomNumber(int range) {
        Random rand = new Random();
        return rand.nextInt(range);
    }
    

    // TODO

    private Student parseRowIntoStudent(Row row) throws IllegalStateException, NumberFormatException, InvalidArgumentException {
        //System.out.println("parsing row");
        if(row == null) { return null; }
        Student student = new Student(projectRepository);
        Cell currentCell;

        String cellString;
        currentCell = row.getCell(0);
        try {
            cellString = currentCell.getStringCellValue();
        }
        catch (NullPointerException npe) {
            cellString = "";
        }
        //System.out.println(cellString0);
        student.setFirstName(cellString);
        //System.out.println(student.getFirstName());

        
        currentCell = row.getCell(1);
        try {
            cellString = currentCell.getStringCellValue();
        }
        catch (NullPointerException npe) {
            cellString = "";
        }
        //System.out.println(cellString1);
        student.setSurname(cellString);

        
        //long num = Long.parseLong(currentCell.getStringCellValue());
        currentCell = row.getCell(2);
        double id = 0;
        try {
            id = currentCell.getNumericCellValue();
        }
        catch (NullPointerException npe) {
            return null;
        }
        catch (IllegalStateException ise) {
            System.out.println("IllegalStateException caught, this isn't a numeric cell");
            try {
                cellString = currentCell.getStringCellValue();
            }
            catch (Exception e) {
                return null;
            }
            System.out.println("String value of this cell was:" + cellString);
            return null;
        }
        long studentId = (new Double(id)).longValue();
        student.setStudentId(studentId);
        //System.out.println(student.getStudentId());

        currentCell = row.getCell(3);
        try {
            cellString = currentCell.getStringCellValue();
        }
        catch (NullPointerException npe) {
            cellString = "";
        }
        Stream stream;
        if(cellString.equals("CS")){ stream = Stream.CS;}
        else if(cellString.equals("DS")){ stream = Stream.CSDS;}
        else if(cellString.equals("CSDS")){  stream = Stream.CSDS;}
        else { stream = null;}
        student.setStream(stream);
        return student;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    private boolean isStudentPresent(Student newStudent) {
        //System.out.println("Entered isStudentPresent.");
        boolean status = false;
        for (Student student:
                students) {
            if(student.isTheSameStudent(newStudent)) {
                status = true;
                break;
            }
        }
        //System.out.println("Exiting isStaffPresent with result:" + status);
        return status;
    }

    private boolean isDefaultStudent(Student student) {
        try {
            return student.getFirstName().trim().toLowerCase().equals("defaultname") || student.getFirstName().trim().toLowerCase().equals("") || student.getFirstName().trim().toLowerCase().equals("student");
        }
        catch (NullPointerException npe) {
            return true;
        }
    }
}
