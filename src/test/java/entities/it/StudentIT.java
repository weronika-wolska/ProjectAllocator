package entities.it;

import entities.Project;
import entities.StaffMember;
import entities.Stream;
import entities.Student;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class StudentIT {

    @Test
    public void testConstructor8Parameters() {
        StaffMember supervisor = new StaffMember();
        supervisor.setName("Jane Doe");
        Project project1 = new Project("The CS Project", Stream.CS, supervisor);
        Project project2 = new Project("The DS Project", Stream.CSDS, supervisor);
        Project project3 = new Project("The CSDS Project", Stream.CSDS, supervisor);
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        Student student = new Student("John", "Smith", (long) 12345678, Stream.CS, preferences, project1);

        if (student.getFirstName() != null) {
            Assert.assertEquals("firstName initialized incorrectly", "John", student.getFirstName());
        }
        if (student.getSurname() != null) {
            Assert.assertEquals("surname initialized incorrectly", "Smith", student.getSurname());
        }
        if (student.getStudentId() != null) {
            Assert.assertEquals("", 12345678, student.getStudentId().longValue());
        }
        if (student.getStream() != null) {
            Assert.assertEquals("surname initialized incorrectly", Stream.CS, student.getStream());
        }
        if (student.getPreferences() != null) {
            Assert.assertEquals("preferences initialized incorrectly", preferences, student.getPreferences());
        }
        if (student.getProject() != null) {
            Assert.assertEquals("project initialized incorrectly", project1, student.getProject());
        }
    }

    @Test
    public void testToString() {
        StaffMember supervisor = new StaffMember();
        supervisor.setName("Jane Doe");
        Project project1 = new Project("The CS Project", Stream.CS, supervisor);
        Project project2 = new Project("The DS Project", Stream.CSDS, supervisor);
        Project project3 = new Project("The CSDS Project", Stream.CSDS, supervisor);
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        Student student = new Student("John", "Smith", (long) 12345678, Stream.CS, preferences, project1);

        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" +
                    "This is testingToString" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" +
                    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println(student.toString());
        Assert.assertEquals("toString working incorrectly", "Hello", student.toString());
    }
}
