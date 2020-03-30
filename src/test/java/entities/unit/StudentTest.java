package entities.unit;

import entities.Stream;
import entities.Student;
import org.junit.Test;
import org.junit.Assert;


public class StudentTest {

    @Test
    public void testDefaultConstructor() {
        Student student = new Student();
        Assert.assertNotNull("default constructor not working correctly", student);
    }

    @Test
    public void testConstructor4Parameters() {
        Student student = new Student("John", "Smith", (long) 12345678, Stream.CS);
        Assert.assertEquals("student.name not initialized correctly", "John", student.getFirstName());
        Assert.assertEquals("student.surname not initialized correctly", "Smith", student.getSurname());
        Assert.assertEquals("student.id not initialized correctly", 12345678, student.getStudentId().longValue());
        Assert.assertEquals("student.stream not initialized correctly", Stream.CS, student.getStream());
        Assert.assertNull("student.project not initialized correctly", student.getProject());
        Assert.assertNull("student.preferences not initialized correctly", student.getPreferences());
    }

    @Test
    public void testSetters() {
        Student student = new Student("Jane", "Doe", (long) 87654321, Stream.DS);

        student.setFirstName("John");
        student.setSurname("Smith");
        student.setStudentId((long) 12345678);
        student.setStream(Stream.CS);

        Assert.assertEquals("student.name not initialized correctly", "John", student.getFirstName());
        Assert.assertEquals("student.surname not initialized correctly", "Smith", student.getSurname());
        Assert.assertEquals("student.id not initialized correctly", 12345678, student.getStudentId().longValue());
        Assert.assertEquals("student.stream not initialized correctly", Stream.CS, student.getStream());
    }
}
