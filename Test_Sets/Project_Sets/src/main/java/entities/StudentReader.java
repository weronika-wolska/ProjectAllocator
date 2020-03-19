package entities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class StudentReader{
    private ArrayList<Student> students;

    public void readXLSX(int studentCount, String filePath) {
        //System.out.println("In readXLSX");
        students = new ArrayList<>(studentCount);
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            ArrayList<Integer> relevantRowNumbers = getRandomRowNumbers(studentCount, rowCount);
            //System.out.println("File input obtained, random rows:" + Arrays.toString(relevantRowNumbers));
            for (int i = 0; i < studentCount; ++i) {
                Row row = sheet.getRow(relevantRowNumbers.get(i));
                Student newStudent = parseRowIntoStudent(row);
                if (newStudent == null) {
                    relevantRowNumbers.remove(i);
                    --i;
                    relevantRowNumbers.add(getRandomNumber(rowCount));
                }
                else {
                    students.add(newStudent);
                }
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    

    // TODO

    private Student parseRowIntoStudent(Row row) throws IllegalStateException, NumberFormatException {
        //System.out.println("parsing row");
        Student student = new Student();
        Cell currentCell;

        String cellString;
        currentCell = row.getCell(0);
        if(currentCell == null) cellString = "";
        else cellString = currentCell.getStringCellValue();
        //System.out.println(cellString0);
        student.setFirstName(cellString);

        
        currentCell = row.getCell(1);
        cellString = currentCell.getStringCellValue();
        //System.out.println(cellString1);
        student.setSurname(cellString);

        
        //long num = Long.parseLong(currentCell.getStringCellValue());
        currentCell = row.getCell(2);
        double id;
        if(currentCell == null) id=0;
        else id = currentCell.getNumericCellValue();
        long studentId = (new Double(id)).longValue();
        student.setStudentId(studentId);

        currentCell = row.getCell(3);
        if(currentCell == null) cellString = "";
        else cellString = currentCell.getStringCellValue();
        Stream stream;
        if(cellString=="CS"){ stream = Stream.CS;}
        else if(cellString=="DS"){ stream = Stream.DS;}
        else if(cellString=="CSDS"){  stream = Stream.CSDS;}
        else { stream = null;}
        student.setStream(stream);

        return student;
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

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

}