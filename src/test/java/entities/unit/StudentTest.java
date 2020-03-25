package entities.unit;

import com.sun.tools.javac.util.Assert;
import entities.Stream;
import entities.Student;
import org.junit.Test;


public class StudentTest {

    @Test
    public void testDefaultConstructor() {
        Student student = new Student();
        Assert.checkNonNull(student);
    }

    @Test
    public void testConstructor4Parameters() {
        Student student = new Student("John", "Smith", (long) 12345678, Stream.CS);

        Assert.check(student.getFirstName().equals("John"), "student.name not initialized correctly");
        Assert.check(student.getSurname().equals("Smith"), "student.surname not initialized correctly");
        Assert.check(student.getStudentId() == (long) 12345678, "student.id not initialized correctly");
        Assert.check(student.getStream() == Stream.CS, "student.stream not initialized correctly");
        Assert.checkNull(student.getProject(),"student.project not initialized to null");
        Assert.checkNull(student.getPreferences(), "student.preferences not initialized to null");
    }

    @Test
    public void testSetters() {
        Student student = new Student("Jane", "Doe", (long) 87654321, Stream.DS);

        student.setFirstName("John");
        student.setSurname("Smith");
        student.setStudentId((long) 12345678);
        student.setStream(Stream.CS);

        Assert.check(student.getFirstName().equals("John"), "student.name not set correctly");
        Assert.check(student.getSurname().equals("Smith"), "student.surname not set correctly");
        Assert.check(student.getStudentId() == (long) 12345678, "student.id not set correctly");
        Assert.check(student.getStream() == Stream.CS, "student.stream not set correctly");

    }
}
