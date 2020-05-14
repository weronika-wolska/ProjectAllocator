package entities.unit;

import entities.Student;
import entities.StudentReader;
import org.junit.Assert;
import org.junit.Test;
import repositories.ProjectRepository;
import repositories.StudentRepository;

import java.util.ArrayList;

public class StudentReaderTest {
    private ArrayList<Student> students;
    private StudentRepository studentRepository;
    private StudentReader reader;

    private void setUp() {
        students = new ArrayList<>();
        studentRepository = new StudentRepository();
        reader = new StudentReader(new ProjectRepository());
    }

    @Test
    public void testReadXLSXTestingTrue() {
        setUp();
        reader.setTesting(true);
        Assert.assertTrue("StudentReader.testing not set correctly", reader.isTesting());
        reader.readXLSX(5, "src/test/testResources/StudentReaderExam.xlsx");
        Assert.assertEquals("incorrect number of faculty members", 5, reader.getStudents().size());

        reader.readXLSX(200, "src/test/testResources/StudentReaderExam.xlsx");
        students = reader.getStudents();
        Assert.assertTrue("incorrect number of faculty members", reader.getStudents().size() < 200);
        /*
        // there is a random element, thus we repeat the procedure a number of times to see average results
        int sum = 0;
        for(int i = 0; i < 100; ++i) {
            reader.readXLSX(200, "src/test/testResources/StudentReaderExam.xlsx");
            students = reader.getStudents();
            sum += students.size();
        }
        double average = sum / (double) 100;

        System.out.println(students);
        //System.out.println("The average result is:" + average);
        Assert.assertEquals("incorrect number of faculty members", 87, average, 0.1);
        */
    }

    @Test
    public void testReadXLSTestingFalse() {
        setUp();
        Assert.assertFalse("StudentReader.testing not initialized correctly", reader.isTesting());

        reader.setTesting(true);
        Assert.assertTrue("StudentReader.testing not set correctly", reader.isTesting());
        reader.setTesting(false);
        Assert.assertFalse("StudentReader.testing not set correctly", reader.isTesting());

        for( int i = 0; i < 20; ++i) {
            setUp();
            reader.readXLSX("src/test/testResources/StudentReaderExam.xlsx");
            Assert.assertEquals("incorrect number of faculty members", 94, reader.getStudents().size());
        }

        for( int i = 0; i < 20; ++i) {
            setUp();
            reader.readXLSX(50, "src/test/testResources/StudentReaderExam.xlsx");
            students = reader.getStudents();
            int numberRead = reader.getStudents().size();
            boolean isWithinRange = 45 < numberRead && numberRead < 55;
            Assert.assertTrue("number of faculty members read is out of reasonable range", isWithinRange);
        }

        setUp();
        reader.readXLSX("src/test/testResources/StudentReaderExam.xlsx");
        Assert.assertEquals("incorrect number of faculty members", 94, reader.getStudents().size());

        reader.setTesting(true);

        setUp();
        reader.readXLSX("src/test/testResources/StudentReaderExam.xlsx");
        Assert.assertEquals("incorrect number of faculty members", 94, reader.getStudents().size());
    }
}
