package entities.it;

import entities.Project;
import entities.StaffMember;
import entities.Stream;
import entities.Student;
import exceptions.InvalidArgumentException;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import javax.activity.InvalidActivityException;

public class StudentIT {

    @Test
    public void testConstructor8Parameters() throws InvalidArgumentException{
        StaffMember supervisor = new StaffMember();
        supervisor.setName("Jane Doe");
        Project project1 = new Project("The CS Project", Stream.CS, supervisor);
        Project project2 = new Project("The DS Project", Stream.CSDS, supervisor);
        Project project3 = new Project("The CSDS Project", Stream.CSDS, supervisor);
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        Student student = new Student("John", "Smith", (long) 12345678, Stream.CS, preferences);

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
    }

    @Test
    public void testToString() throws InvalidArgumentException{
        StaffMember supervisor = new StaffMember();
        supervisor.setName("Jane Doe");
        Project project1 = new Project("The CS Project", Stream.CS, supervisor);
        Project project2 = new Project("The DS Project", Stream.CSDS, supervisor);
        Project project3 = new Project("The CSDS Project", Stream.CSDS, supervisor);
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        Student student = new Student("John", "Smith", (long) 12345678, Stream.CS, preferences);
        String expected = "Student [firstName=John, stream=CS, studentId=12345678, surname=Sm" +
                "ith, preferences=Project [projectName=" +
                "The CS Project, stream=CS, supervisor=Jane Doe]\n" +
                "Project [projectName=The DS Project, stream=CSDS, supervisor=Jane Doe]\n" +
                "Project [projectName=The CSDS Project, stream=CSDS, supervisor=Jane Doe]\n" +
                "]";
        Assert.assertEquals("toString working incorrectly", expected, student.toString());
    }

    @Test
    public void testCanDoProject() throws InvalidArgumentException {
        StaffMember supervisor = new StaffMember();
        supervisor.setName("Jane Doe");
        Project project1 = new Project("The CS Project", Stream.CS, supervisor);
        Project project2 = new Project("The DS Project", Stream.DS, supervisor);
        Project project3 = new Project("The CSDS Project", Stream.CSDS, supervisor);
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        Student student = new Student("John", "Smith", (long) 12345678, Stream.CS, preferences);

        Assert.assertTrue(student.canDoProject(project1));
        Assert.assertFalse(student.canDoProject(project2));
        Assert.assertFalse(student.canDoProject(project3));

        student.setStream(Stream.DS);

        Assert.assertFalse(student.canDoProject(project1));
        Assert.assertTrue(student.canDoProject(project2));
        Assert.assertTrue(student.canDoProject(project3));

        student.setStream(Stream.CSDS);

        Assert.assertFalse(student.canDoProject(project1));
        Assert.assertTrue(student.canDoProject(project2));
        Assert.assertTrue(student.canDoProject(project3));
    }
}
